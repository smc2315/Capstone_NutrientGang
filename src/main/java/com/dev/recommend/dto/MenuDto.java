package com.dev.recommend.dto;

import com.dev.health.dto.NutrientCalorieInfoDto;
import com.dev.health.dto.NutrientDto;
import com.dev.register.dto.FoodDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private String menuName;
    private String menuInfo;
    private double menuKcal;
    private double menuCarbo;
    private double menuProtein;
    private double menuFat;

}
