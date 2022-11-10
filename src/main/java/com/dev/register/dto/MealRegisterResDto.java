package com.dev.register.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MealRegisterResDto {
    private String username;
    private String meal;
    private LocalDate date;
}
