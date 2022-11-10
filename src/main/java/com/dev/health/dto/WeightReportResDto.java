package com.dev.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeightReportResDto {
    private Integer maxWeight;
    private Integer minWeight;
    private int todayWeight;
    private List<WeightInfoDto> weightInfoList;

    @Builder
    public WeightReportResDto(Integer maxWeight, Integer minWeight,int todayWeight, List<WeightInfoDto> weightInfoList){
        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        this.todayWeight = todayWeight;
        this.weightInfoList = weightInfoList;
    }
}
