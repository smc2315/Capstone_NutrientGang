package com.dev.health.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CalorieReportResDto {
    private Integer maxCalorie;
    private Integer minCalorie;
    private int needCalorie;
    private List<CalorieInfoDto> calorieInfoList;

    @Builder
    public CalorieReportResDto(Integer maxCalorie,Integer minCalorie, int needCalorie, List<CalorieInfoDto> calorieInfoList){
        this.maxCalorie = maxCalorie;
        this.minCalorie = minCalorie;
        this.needCalorie = needCalorie;
        this.calorieInfoList = calorieInfoList;
    }
}
