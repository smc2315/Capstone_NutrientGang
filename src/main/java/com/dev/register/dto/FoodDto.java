package com.dev.register.dto;

import com.dev.health.dto.NutrientDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FoodDto {
    private String name;
    private int calorie;
    private NutrientDto nutrient;

    @Builder
    FoodDto(String name,int calorie,NutrientDto nutrient){
        this.name = name;
        this.calorie = calorie;
        this.nutrient = nutrient;
    }
}
