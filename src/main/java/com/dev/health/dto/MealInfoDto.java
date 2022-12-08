package com.dev.health.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MealInfoDto {
    private long mealId;
    private List<String> names;
    private String imgUrl;
    private int calorie;
}
