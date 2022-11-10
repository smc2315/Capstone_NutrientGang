package com.dev.health.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class HadMealInfoResDto {
    private List<HadMealDto> breakfastList;
    private List<HadMealDto> lunchList;
    private List<HadMealDto> dinnerList;
}
