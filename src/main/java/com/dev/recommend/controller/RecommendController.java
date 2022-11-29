package com.dev.recommend.controller;

import com.dev.recommend.service.RecommendFoodService;
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
@RequestMapping("/rcn")
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final RecommendFoodService recommendService;

    @GetMapping("/menu")
    public BaseResponse<FoodListDto> getRecommend(@RequestParam("meal") String meal, @RequestParam("date") String date){
        try{
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FoodListDto recommend;
            if(meal.equals("breakfast")) {
                recommend = recommendService.getRecommendBreakfast(parseDate);
            }
            else if(meal.equals("lunch")) {
                recommend = recommendService.getRecommendLunch(parseDate);
            }
            else {
                recommend = recommendService.getRecommendDinner(parseDate);
            }
            return new BaseResponse<>(recommend);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/lunch")
    public BaseResponse<FoodListDto> getRecommendLunch(@RequestParam("date") String date){
        try{
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FoodListDto recommendLunch = recommendService.getRecommendLunch(parseDate);
            return new BaseResponse<>(recommendLunch);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/dinner")
    public BaseResponse<FoodListDto> getRecommendDinner(@RequestParam("date") String date){
        try{
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            FoodListDto recommendDinner = recommendService.getRecommendDinner(parseDate);
            return new BaseResponse<>(recommendDinner);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
