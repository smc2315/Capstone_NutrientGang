package com.dev.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class CalorieInfoDto {
    private Date date;
    private int calorie;
}
