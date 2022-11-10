package com.dev.register.dto;

import com.dev.health.dto.NutrientDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodInfoDto {
    private Double xmain;
    private Double ymain;
    private String name;
    private Integer calorie;
    private NutrientDto nutrient;
}
