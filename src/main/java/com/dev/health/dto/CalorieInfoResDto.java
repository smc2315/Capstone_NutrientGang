package com.dev.health.dto;


import lombok.Builder;

public class CalorieInfoResDto {
    private int needCalorie;
    private int haveCalorie;

    @Builder
    public CalorieInfoResDto(int needCalorie,int haveCalorie){
        this.needCalorie = needCalorie;
        this.haveCalorie = haveCalorie;
    }
}
