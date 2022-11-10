package com.dev.health.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class HadMealDto {
    private int id;
    private boolean isEaten;
    private LocalDate date;
}
