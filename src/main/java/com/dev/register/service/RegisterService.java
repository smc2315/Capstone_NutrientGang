package com.dev.register.service;

import com.dev.health.entity.NutrientStatus;
import com.dev.health.repository.NutrientStatusRepository;
import com.dev.jwt.utils.SecurityUtil;
import com.dev.member.entity.Member;
import com.dev.member.repository.MemberRepository;
import com.dev.register.dto.FoodInfoDto;
import com.dev.register.dto.MealRegisterReqDto;
import com.dev.register.dto.MealRegisterResDto;
import com.dev.register.entity.Breakfast;
import com.dev.register.entity.Dinner;
import com.dev.register.entity.Lunch;
import com.dev.register.repository.BreakfastRepository;
import com.dev.register.repository.DinnerRepository;
import com.dev.register.repository.LunchRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegisterService {
    private final BreakfastRepository breakfastRepository;
    private final LunchRepository lunchRepository;
    private final DinnerRepository dinnerRepository;
    private final NutrientStatusRepository nutrientStatusRepository;
    private final MemberRepository memberRepository;

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
}
