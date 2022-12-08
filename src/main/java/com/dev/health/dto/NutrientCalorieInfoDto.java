package com.dev.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class NutrientCalorieInfoDto {
    private int calorie;
    private int carbohydrate;
    private int protein;
    private int fat;
}
