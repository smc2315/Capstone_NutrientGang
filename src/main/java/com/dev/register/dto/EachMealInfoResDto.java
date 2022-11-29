package com.dev.register.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EachMealInfoResDto {
    private LocalDate date;
    private Long mealId;
    private String meal;
    private String imgUrl;
    private List<String> foods = new ArrayList<>();

    public void setFoods(){

    }
}
