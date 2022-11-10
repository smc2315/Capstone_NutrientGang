package com.dev.register.service;

import com.dev.health.dto.NutrientDto;
import com.dev.register.dto.FoodDto;
import com.dev.register.entity.Food;
import com.dev.register.repository.FoodRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodDto findFood(String menu){
        Optional<Food> findFood = foodRepository.findByName(menu);
        if (findFood.isEmpty()){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_FOOD);
        }

        NutrientDto nutrient = new NutrientDto((int)findFood.get().getCarbohydrate(),
                (int)findFood.get().getProtein(),
                (int)findFood.get().getFat());

        return FoodDto.builder()
                .name(findFood.get().getName())
                .calorie((int)findFood.get().getCalorie())
                .nutrient(nutrient)
                .build();

    }
}
