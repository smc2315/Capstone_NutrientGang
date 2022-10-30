package com.dev.health.controller;

import com.dev.health.dto.CalorieInfoResDto;
import com.dev.health.service.HealthService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {
    private HealthService healthService;

    @GetMapping("/calorie/{date}")
    public BaseResponse<CalorieInfoResDto> getCalorieInfo(@PathVariable Date date){
        try{
            CalorieInfoResDto calorieInfo = healthService.getCalorieInfo(date);
            return new BaseResponse<>(calorieInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
