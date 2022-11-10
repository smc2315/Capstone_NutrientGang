package com.dev.register.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MealRegisterReqDto {
    private LocalDate date;
    private String meal;
    private String imgUrl;
    private List<FoodInfoDto> foods;
}
