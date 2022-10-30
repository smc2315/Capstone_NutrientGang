package com.dev.health.dto;


import lombok.Builder;

public class NutrientStatusInfoResDto {
    private NutrientDto needNutrient;
    private NutrientDto haveNutrient;

    @Builder
    public NutrientStatusInfoResDto(NutrientDto needNutrient,NutrientDto haveNutrient){
        this.needNutrient = needNutrient;
        this.haveNutrient = haveNutrient;
    }
}
