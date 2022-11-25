package com.dev.recommend.controller;

import com.dev.health.dto.CalorieInfoResDto;
import com.dev.recommend.service.RecommendService;
import com.dev.register.dto.FoodListDto;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final RecommendService recommendService;

    @GetMapping("/")
    public BaseResponse<FoodListDto> getRecommendBreakfast(@RequestParam("date") String date){
        try{
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FoodListDto recommendBreakfast = recommendService.getRecommendBreakfast(parseDate);
            return new BaseResponse<>(recommendBreakfast);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
