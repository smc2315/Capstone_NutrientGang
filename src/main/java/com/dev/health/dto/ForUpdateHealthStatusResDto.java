package com.dev.health.dto;

import com.dev.health.entity.Activity;
import com.dev.health.entity.Gender;
import com.dev.health.entity.Target;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ForUpdateHealthStatusResDto {
    private int height;
    private int weight;
    private Gender gender;
    private Activity activity;
    private Target target;
    private LocalDate date;
}
