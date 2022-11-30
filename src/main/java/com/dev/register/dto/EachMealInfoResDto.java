package com.dev.register.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EachMealInfoResDto {
    private LocalDate date;
    private Long mealId;
    private String meal;
    private String imgUrl;
    private List<EachFoodInfoDto> foods = new ArrayList<>();

    public void setFoods(EachFoodInfoDto eachFoodInfoDto){
        this.foods.add(eachFoodInfoDto);
    }
}
