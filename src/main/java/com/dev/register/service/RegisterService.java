package com.dev.register.service;

import com.dev.health.dto.EachNutrientDto;
import com.dev.health.entity.NutrientStatus;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.member.entity.Member;
import com.dev.member.repository.MemberRepository;
import com.dev.register.dto.*;
import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Dinner;
import com.dev.register.entity.Food;
import com.dev.register.entity.Lunch;
import com.dev.register.repository.BreakfastRepository;
import com.dev.register.repository.DinnerRepository;
import com.dev.register.repository.FoodRepository;
import com.dev.register.repository.LunchRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RegisterService {
    private final BreakfastRepository breakfastRepository;
    private final LunchRepository lunchRepository;
    private final DinnerRepository dinnerRepository;
    private final NutrientStatusRepository nutrientStatusRepository;
    private final MemberRepository memberRepository;

    private final FoodRepository foodRepository;

    @Transactional
    public MealRegisterResDto registerMeal(MealRegisterReqDto mealRegisterReqDto){
        Long userId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(userId).get();

        LocalDate date = mealRegisterReqDto.getDate();
        String meal = mealRegisterReqDto.getMeal();
        String imgUrl = mealRegisterReqDto.getImgUrl();

        Optional<NutrientStatus> findNutrientStatus = nutrientStatusRepository.findByMemberAndDate(userId, date);
        if (findNutrientStatus.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }
        NutrientStatus nutrientStatus = findNutrientStatus.get();

        if (meal.equals("아침")){
            Breakfast breakfast = new Breakfast();
            breakfast.setDate(date);
            breakfast.setImgUrl(imgUrl);
            breakfast.setMember(member);
            for (FoodInfoDto food : mealRegisterReqDto.getFoods()) {
                   breakfast.setXmains(food.getXmain());
                   breakfast.setYmains(food.getYmain());
                   breakfast.setMenu(food.getName());
                   nutrientStatus.addCalorie(food.getCalorie());
                   nutrientStatus.addCarbohydrate(food.getNutrient().getCarbohydrate());
                   nutrientStatus.addProtein(food.getNutrient().getProtein());
                   nutrientStatus.addFat(food.getNutrient().getFat());
            }
            breakfastRepository.save(breakfast);
        }

        if (meal.equals("점심")){
            Lunch lunch = new Lunch();
            lunch.setDate(date);
            lunch.setImgUrl(imgUrl);
            lunch.setMember(member);
            for (FoodInfoDto food : mealRegisterReqDto.getFoods()) {
                lunch.setXmains(food.getXmain());
                lunch.setYmains(food.getYmain());
                lunch.setMenu(food.getName());
                nutrientStatus.addCalorie(food.getCalorie());
                nutrientStatus.addCarbohydrate(food.getNutrient().getCarbohydrate());
                nutrientStatus.addProtein(food.getNutrient().getProtein());
                nutrientStatus.addFat(food.getNutrient().getFat());
            }
            lunchRepository.save(lunch);
        }

        if(meal.equals("저녁")){
            Dinner dinner = new Dinner();
            dinner.setDate(date);
            dinner.setImgUrl(imgUrl);
            dinner.setMember(member);
            for (FoodInfoDto food : mealRegisterReqDto.getFoods()) {
                dinner.setXmains(food.getXmain());
                dinner.setYmains(food.getYmain());
                dinner.setMenu(food.getName());
                nutrientStatus.addCalorie(food.getCalorie());
                nutrientStatus.addCarbohydrate(food.getNutrient().getCarbohydrate());
                nutrientStatus.addProtein(food.getNutrient().getProtein());
                nutrientStatus.addFat(food.getNutrient().getFat());
            }
            dinnerRepository.save(dinner);
        }

        nutrientStatusRepository.save(nutrientStatus);
        return MealRegisterResDto.builder()
                .username(member.getUsername())
                .meal(meal)
                .date(date)
                .build();
    }

    @Transactional
    public EachMealInfoResDto getForUpdateMealInfo(Long mealId,String mealType){
        EachMealInfoResDto eachMealInfoResDto = new EachMealInfoResDto();

        if (mealType.equals("아침")){
            Optional<Breakfast> findMeal = breakfastRepository.findById(mealId);
            if(findMeal.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }
            Breakfast mealInfo = findMeal.get();

            eachMealInfoResDto.setDate(mealInfo.getDate());
            eachMealInfoResDto.setMealId(mealInfo.getId());
            eachMealInfoResDto.setMeal(mealType);
            eachMealInfoResDto.setImgUrl(mealInfo.getImgUrl());

            List<String> menus = mealInfo.getMenu();
            List<String> xmains = mealInfo.getXmains();
            List<String> ymains = mealInfo.getYmains();

            for (int i = 0; i<menus.size(); i++){
                Optional<Food> findFood = foodRepository.findByName(menus.get(i));
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                EachFoodInfoDto eachFoodInfoDto = new EachFoodInfoDto();
                if (xmains.get(i).equals(" ")){
                    eachFoodInfoDto.setXmain(null);
                }else{
                    eachFoodInfoDto.setXmain(Double.parseDouble(xmains.get(i)));
                }
                if (ymains.get(i).equals(" ")){
                    eachFoodInfoDto.setYmain(null);
                }else{
                    eachFoodInfoDto.setYmain(Double.parseDouble(ymains.get(i)));
                }
                eachFoodInfoDto.setName(food.getName());
                eachFoodInfoDto.setKcal((int)food.getCalorie());
                eachFoodInfoDto.setNutrient(new EachNutrientDto((int)food.getCarbohydrate(),
                        (int)food.getProtein(),
                        (int)food.getFat()));
                eachMealInfoResDto.setFoods(eachFoodInfoDto);
            }


        }

        if (mealType.equals("점심")){
            Optional<Lunch> findMeal = lunchRepository.findById(mealId);
            if(findMeal.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }
            Lunch mealInfo = findMeal.get();
            eachMealInfoResDto.setDate(mealInfo.getDate());
            eachMealInfoResDto.setMealId(mealInfo.getId());
            eachMealInfoResDto.setMeal(mealType);
            eachMealInfoResDto.setImgUrl(mealInfo.getImgUrl());

            List<String> menus = mealInfo.getMenu();
            List<String> xmains = mealInfo.getXmains();
            List<String> ymains = mealInfo.getYmains();

            for (int i = 0; i<menus.size(); i++){
                Optional<Food> findFood = foodRepository.findByName(menus.get(i));
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                EachFoodInfoDto eachFoodInfoDto = new EachFoodInfoDto();
                if (xmains.get(i).equals(" ")){
                    eachFoodInfoDto.setXmain(null);
                }else{
                    eachFoodInfoDto.setXmain(Double.parseDouble(xmains.get(i)));
                }
                if (ymains.get(i).equals(" ")){
                    eachFoodInfoDto.setYmain(null);
                }else{
                    eachFoodInfoDto.setYmain(Double.parseDouble(ymains.get(i)));
                }
                eachFoodInfoDto.setName(food.getName());
                eachFoodInfoDto.setKcal((int)food.getCalorie());
                eachFoodInfoDto.setNutrient(new EachNutrientDto((int)food.getCarbohydrate(),
                        (int)food.getProtein(),
                        (int)food.getFat()));
                eachMealInfoResDto.setFoods(eachFoodInfoDto);
            }
        }

        if (mealType.equals("저녁")){
            Optional<Dinner> findMeal = dinnerRepository.findById(mealId);
            if (findMeal.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }
            Dinner mealInfo = findMeal.get();
            eachMealInfoResDto.setDate(mealInfo.getDate());
            eachMealInfoResDto.setMealId(mealInfo.getId());
            eachMealInfoResDto.setMeal(mealType);
            eachMealInfoResDto.setImgUrl(mealInfo.getImgUrl());

            List<String> menus = mealInfo.getMenu();
            List<String> xmains = mealInfo.getXmains();
            List<String> ymains = mealInfo.getYmains();

            for (int i = 0; i<menus.size(); i++){
                Optional<Food> findFood = foodRepository.findByName(menus.get(i));
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                EachFoodInfoDto eachFoodInfoDto = new EachFoodInfoDto();
                if (xmains.get(i).equals(" ")){
                    eachFoodInfoDto.setXmain(null);
                }else{
                    eachFoodInfoDto.setXmain(Double.parseDouble(xmains.get(i)));
                }
                if (ymains.get(i).equals(" ")){
                    eachFoodInfoDto.setYmain(null);
                }else{
                    eachFoodInfoDto.setYmain(Double.parseDouble(ymains.get(i)));
                }
                eachFoodInfoDto.setName(food.getName());
                eachFoodInfoDto.setKcal((int)food.getCalorie());
                eachFoodInfoDto.setNutrient(new EachNutrientDto((int)food.getCarbohydrate(),
                        (int)food.getProtein(),
                        (int)food.getFat()));
                eachMealInfoResDto.setFoods(eachFoodInfoDto);
            }
        }

        return eachMealInfoResDto;
    }

    @Transactional
    public MealRegisterResDto updateMealInfo(Long mealId,String mealType,MealRegisterReqDto updateMealInfoReqDto){
        Long userId = SecurityUtil.getCurrentMemberId();
        LocalDate date = updateMealInfoReqDto.getDate();
        Optional<NutrientStatus> findNutrientStatus = nutrientStatusRepository.findByMemberAndDate(userId, date);
        if (findNutrientStatus.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
        }
        NutrientStatus nutrientStatus = findNutrientStatus.get();

        if (mealType.equals("아침")){
            Optional<Breakfast> findMealInfo = breakfastRepository.findById(mealId);
            if (findMealInfo.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }
            Breakfast mealInfo = findMealInfo.get();
            List<String> menus = mealInfo.getMenu();

            for (String menu: menus){
                Optional<Food> findFood = foodRepository.findByName(menu);
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                nutrientStatus.subCalorie((int)food.getCalorie());
                nutrientStatus.subCarbohydrate((int)food.getCarbohydrate());
                nutrientStatus.subProtein((int)food.getProtein());
                nutrientStatus.subFat((int)food.getFat());
            }
            nutrientStatusRepository.save(nutrientStatus);
            breakfastRepository.delete(mealInfo);
        }

        if (mealType.equals("점심")){
            Optional<Lunch> findMealInfo = lunchRepository.findById(mealId);
            if (findMealInfo.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }

            Lunch mealInfo = findMealInfo.get();
            List<String> menus = mealInfo.getMenu();

            for (String menu: menus){
                Optional<Food> findFood = foodRepository.findByName(menu);
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                nutrientStatus.subCalorie((int)food.getCalorie());
                nutrientStatus.subCarbohydrate((int)food.getCarbohydrate());
                nutrientStatus.subProtein((int)food.getProtein());
                nutrientStatus.subFat((int)food.getFat());
            }
            nutrientStatusRepository.save(nutrientStatus);
            lunchRepository.delete(mealInfo);
        }

        if (mealType.equals("저녁")){
            Optional<Dinner> findMealInfo = dinnerRepository.findById(mealId);
            if (findMealInfo.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }

            Dinner mealInfo = findMealInfo.get();
            List<String> menus = mealInfo.getMenu();

            for (String menu: menus){
                Optional<Food> findFood = foodRepository.findByName(menu);
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                nutrientStatus.subCalorie((int)food.getCalorie());
                nutrientStatus.subCarbohydrate((int)food.getCarbohydrate());
                nutrientStatus.subProtein((int)food.getProtein());
                nutrientStatus.subFat((int)food.getFat());
            }
            nutrientStatusRepository.save(nutrientStatus);
            dinnerRepository.delete(mealInfo);
        }

        return registerMeal(updateMealInfoReqDto);

    }

    public MealRegisterResDto deleteMeal(Long mealId,String mealType){
        Long userId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(userId).get();
        LocalDate date = LocalDate.now();

        if (mealType.equals("아침")){
            Optional<Breakfast> findMealInfo = breakfastRepository.findById(mealId);
            if (findMealInfo.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }
            Breakfast mealInfo = findMealInfo.get();
            date = mealInfo.getDate();
            Optional<NutrientStatus> findNutrientStatus = nutrientStatusRepository.findByMemberAndDate(userId, date);
            if (findNutrientStatus.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
            }
            NutrientStatus nutrientStatus = findNutrientStatus.get();
            List<String> menus = mealInfo.getMenu();

            for (String menu : menus) {
                Optional<Food> findFood = foodRepository.findByName(menu);
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                nutrientStatus.subCalorie((int)food.getCalorie());
                nutrientStatus.subCarbohydrate((int)food.getCarbohydrate());
                nutrientStatus.subProtein((int)food.getProtein());
                nutrientStatus.subFat((int)food.getFat());
            }
            nutrientStatusRepository.save(nutrientStatus);
            breakfastRepository.delete(mealInfo);
        }

        if (mealType.equals("점심")){
            Optional<Lunch> findMealInfo = lunchRepository.findById(mealId);
            if (findMealInfo.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }
            Lunch mealInfo = findMealInfo.get();
            date = mealInfo.getDate();
            Optional<NutrientStatus> findNutrientStatus = nutrientStatusRepository.findByMemberAndDate(userId, date);
            if (findNutrientStatus.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
            }
            NutrientStatus nutrientStatus = findNutrientStatus.get();
            List<String> menus = mealInfo.getMenu();

            for (String menu : menus) {
                Optional<Food> findFood = foodRepository.findByName(menu);
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                nutrientStatus.subCalorie((int)food.getCalorie());
                nutrientStatus.subCarbohydrate((int)food.getCarbohydrate());
                nutrientStatus.subProtein((int)food.getProtein());
                nutrientStatus.subFat((int)food.getFat());
            }
            nutrientStatusRepository.save(nutrientStatus);
            lunchRepository.delete(mealInfo);
        }

        if (mealType.equals("저녁")){
            Optional<Dinner> findMealInfo = dinnerRepository.findById(mealId);
            if (findMealInfo.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_INTAKE);
            }
            Dinner mealInfo = findMealInfo.get();
            date = mealInfo.getDate();
            Optional<NutrientStatus> findNutrientStatus = nutrientStatusRepository.findByMemberAndDate(userId, date);
            if (findNutrientStatus.isEmpty()){
                throw new BaseException(BaseResponseStatus.NOT_FOUND_NUTRIENT_STATUS);
            }
            NutrientStatus nutrientStatus = findNutrientStatus.get();
            List<String> menus = mealInfo.getMenu();

            for (String menu : menus) {
                Optional<Food> findFood = foodRepository.findByName(menu);
                if (findFood.isEmpty()){
                    throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
                }
                Food food = findFood.get();
                nutrientStatus.subCalorie((int)food.getCalorie());
                nutrientStatus.subCarbohydrate((int)food.getCarbohydrate());
                nutrientStatus.subProtein((int)food.getProtein());
                nutrientStatus.subFat((int)food.getFat());
            }
            nutrientStatusRepository.save(nutrientStatus);
            dinnerRepository.delete(mealInfo);
        }

        return MealRegisterResDto.builder()
                .username(member.getUsername())
                .meal(mealType)
                .date(date)
                .build();
    }

}
