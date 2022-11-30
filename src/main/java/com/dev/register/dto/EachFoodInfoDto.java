package com.dev.register.dto;

import com.dev.health.dto.EachNutrientDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EachFoodInfoDto {
    private Double xmain;
    private Double ymain;
    private String name;
    private Integer kcal;
    private EachNutrientDto nutrient;
}
