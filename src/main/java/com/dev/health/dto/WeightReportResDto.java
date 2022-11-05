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
    private List<WeightInfoDto> weightInfoList;

    @Builder
    public WeightReportResDto(Integer maxWeight, Integer minWeight, List<WeightInfoDto> weightInfoList){
        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        this.weightInfoList = weightInfoList;
    }
}
