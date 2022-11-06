package com.dev.health.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class NutrientWeekPortionInfoDto {
    private LocalDate date;
    private double carbohydratePortion;
    private double proteinPortion;
    private double fatPortion;

    @Builder
    public NutrientWeekPortionInfoDto(LocalDate date, double carbohydratePortion, double proteinPortion, double fatPortion){
        this.date = date;
        this.carbohydratePortion = carbohydratePortion;
        this.proteinPortion = proteinPortion;
        this.fatPortion = fatPortion;
    }
}
