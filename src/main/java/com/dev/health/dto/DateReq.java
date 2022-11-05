package com.dev.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DateReq {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
}
