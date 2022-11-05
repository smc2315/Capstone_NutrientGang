package com.dev.health.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class NutrientInfoDto {
    private LocalDate date;
    private int carbohydrate;
    private int protein;
    private int fat;
}
