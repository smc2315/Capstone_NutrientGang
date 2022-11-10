package com.dev.health.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class IntakeMealInfoResDto {
    private int eachNeedCalorie;
    private int breakfastCalorie;
    private int lunchCalorie;
    private int dinnerCalorie;
    private List<MealInfoDto> breakfast = new ArrayList<>();
    private List<MealInfoDto> lunch = new ArrayList<>();
    private List<MealInfoDto> dinner = new ArrayList<>();

    public void setBreakfast(MealInfoDto mealInfoDto){
        this.breakfast.add(mealInfoDto);
    }

    public void setLunch(MealInfoDto mealInfoDto){
        this.lunch.add(mealInfoDto);
    }

    public void setDinner(MealInfoDto mealInfoDto){
        this.dinner.add(mealInfoDto);
    }
}
