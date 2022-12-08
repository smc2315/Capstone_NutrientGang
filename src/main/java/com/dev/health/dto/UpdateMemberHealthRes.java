package com.dev.health.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdateMemberHealthRes {
    private LocalDate date;
}
