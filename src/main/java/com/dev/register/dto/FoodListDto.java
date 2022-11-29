package com.dev.register.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class FoodListDto {
    private List<FoodDto> foodDtoList;

    @Builder
    FoodListDto(List<FoodDto> foodDtoList){
        this.foodDtoList = foodDtoList;
    }
}
