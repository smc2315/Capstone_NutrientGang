package com.dev.health.dto;

import com.dev.health.entity.Activity;
import com.dev.health.entity.Gender;
import com.dev.health.entity.Target;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberHealthReq {
    private Integer height;
    private Integer weight;
    private Gender gender;
    private Activity activity;
    private Target target;
    private LocalDate date;

}
