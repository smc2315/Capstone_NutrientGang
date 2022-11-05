package com.dev.health.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutrientStatusInfoResDto {
    private NutrientDto needNutrient;
    private NutrientDto haveNutrient;

    @Builder
    public NutrientStatusInfoResDto(NutrientDto needNutrient,NutrientDto haveNutrient){
        this.needNutrient = needNutrient;
        this.haveNutrient = haveNutrient;
    }
}
