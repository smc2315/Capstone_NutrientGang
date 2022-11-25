package com.dev.recommend.service;


import com.dev.health.dto.*;
import com.dev.health.entity.NutrientStatus;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.register.dto.FoodDto;
import com.dev.register.dto.FoodListDto;
import com.dev.register.entity.Food;
import com.dev.register.repository.FoodRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendService {
    private final HealthStatusRepository healthStatusRepository;
    private final NutrientStatusRepository nutrientStatusRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public CalorieInfoResDto getCalorieInfo(LocalDate date){
        Long userId = SecurityUtil.getCurrentMemberId();
        Integer needCalorie = healthStatusRepository.findNeedCalorieByMemberAndDate(userId, date);
        if (needCalorie == null) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }

        Integer haveCalorie = nutrientStatusRepository.findHaveCalorieByMemberAndDate(userId, date);
        if (haveCalorie == null) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }

        return CalorieInfoResDto.builder()
                .needCalorie(needCalorie)
                .haveCalorie(haveCalorie)
                .build();
    }

    @Transactional
    public NutrientStatusInfoResDto getNutrientInfo(LocalDate date){
        Long userId = SecurityUtil.getCurrentMemberId();

        // 섭취해야할 영양소 계산
        Optional<NutrientDto> needNutrientInfo = healthStatusRepository.findNeedNutrientByMemberAndDate(userId, date);
        if(needNutrientInfo.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_HEALTH_STATUS);
        }
        NutrientDto needNutrient = needNutrientInfo.get();


        // 섭취한 영양소 값
        Optional<NutrientDto> haveNutrientInfo = nutrientStatusRepository.findHaveNutrientInfoByMemberAndDate(userId, date);
        if(haveNutrientInfo.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }
        NutrientDto haveNutrient = haveNutrientInfo.get();

        return NutrientStatusInfoResDto.builder()
                .needNutrient(needNutrient)
                .haveNutrient(haveNutrient)
                .build();

    }

    @Transactional
    public FoodListDto getRecommendBreakfast(LocalDate date){
        CalorieInfoResDto calorieInfo = getCalorieInfo(date);

        Double minBreakfastNeedCalorie = (double)calorieInfo.getNeedCalorie()/3-1000;
        Double maxBreakfastNeedCalorie = (double)calorieInfo.getNeedCalorie()/3+1000;

        Optional<List<Food>> foods = foodRepository.findByCalorieBetween(minBreakfastNeedCalorie, maxBreakfastNeedCalorie);
        log.info("{}",foods.get().get(0).getName());
        if(foods.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
        }
        List<Food> fiveFoods = getRandomFiveFoods(foods);
        log.info("{}",fiveFoods.size());
        List<FoodDto> fiveFoodDtos = new ArrayList<>();
        for(Food f: fiveFoods){
            NutrientDto nutrient = new NutrientDto((int)f.getCarbohydrate(),
                    (int)f.getProtein(),
                    (int)f.getFat());
            FoodDto foodDto = FoodDto.builder()
                    .name(f.getName())
                    .calorie((int)f.getCalorie())
                    .nutrient(nutrient)
                    .build();
            fiveFoodDtos.add(foodDto);

        }
        log.info("{}",fiveFoodDtos.size());
        return FoodListDto.builder()
                .foodDtoList(fiveFoodDtos)
                .build();

    }

    private List<Food> getRandomFiveFoods(Optional<List<Food>> foods){
        if(foods.get().size()<=5){
            return foods.get();
        }

        List<Food> fiveFoods = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        while (set.size() < 5) {
            Double d = Math.random() * foods.get().size();
            set.add(d.intValue());
        }
        for(int i : set){
            fiveFoods.add(foods.get().get(i));
        }
        return fiveFoods;
    }

}
