package com.dev.health.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutrientWeekPortionDto {
    private double carbohydratePortion;
    private double proteinPortion;
    private double fatPortion;

    @Builder
    public NutrientWeekPortionDto(double carbohydratePortion, double proteinPortion, double fatPortion){
        this.carbohydratePortion = carbohydratePortion;
        this.proteinPortion = proteinPortion;
        this.fatPortion = fatPortion;
    }

}
