package com.dev.health.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MealPortionResDto {
    private double breakfast;
    private double lunch;
    private double dinner;
}
