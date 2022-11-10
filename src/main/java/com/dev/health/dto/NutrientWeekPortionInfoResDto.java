package com.dev.health.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NutrientWeekPortionInfoResDto {
    private List<NutrientWeekPortionInfoDto> weekPortionList;
}
