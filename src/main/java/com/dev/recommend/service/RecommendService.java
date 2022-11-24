package com.dev.recommend.service;


import com.dev.health.dto.*;
import com.dev.health.entity.NutrientStatus;
import com.dev.health.repository.HealthStatusRepository;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final HealthStatusRepository healthStatusRepository;
    private final NutrientStatusRepository nutrientStatusRepository;

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

    private void calculateBreakfast(LocalDate date){
        CalorieInfoResDto calorieInfo = getCalorieInfo(date);

        int minBreakfastNeedCalorie = calorieInfo.getNeedCalorie()/3-400;
        int maxBreakfastNeedCalorie = calorieInfo.getNeedCalorie()/3-400;


    }
}
