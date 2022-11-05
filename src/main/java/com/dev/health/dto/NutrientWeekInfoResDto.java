package com.dev.health.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutrientWeekInfoResDto {
    private NutrientDto haveNutrient;
    private NutrientDto needNutrient;
    private NutrientWeekPortionDto nutrientPortion;

    @Builder
    public NutrientWeekInfoResDto(NutrientDto haveNutrient,NutrientDto needNutrient,NutrientWeekPortionDto nutrientPortion){
        this.haveNutrient = haveNutrient;
        this.needNutrient = needNutrient;
        this.nutrientPortion = nutrientPortion;
    }

}
