package com.dev.recommend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class RestaurantListDto {
    private List<RestaurantDto> restaurantDtoList;

    @Builder
    RestaurantListDto(List<RestaurantDto> restaurantDtoList) {
        this.restaurantDtoList = restaurantDtoList;
    }
}
