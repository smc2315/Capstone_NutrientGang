package com.dev.health.controller;

import com.dev.health.dto.*;
import com.dev.health.service.HealthService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Slf4j
public class HealthController {
    private final HealthService healthService;

    @GetMapping("/diary/calorie")
    public BaseResponse<CalorieInfoResDto> getCalorieInfo(@RequestParam("date") String date){
        try{
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            CalorieInfoResDto calorieInfo = healthService.getCalorieInfo(parseDate);
            return new BaseResponse<>(calorieInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/diary/nutrient")
    public BaseResponse<NutrientStatusInfoResDto> getNutrientInfo(@RequestParam("date") String date){
        try{
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            NutrientStatusInfoResDto nutrientInfo = healthService.getNutrientInfo(parseDate);
            return new BaseResponse<>(nutrientInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/diary/meal")
    public BaseResponse<IntakeMealInfoResDto> getIntakeMealInfo(@RequestParam("date") String date){
        try {
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            IntakeMealInfoResDto intakeMealInfo = healthService.getIntakeMealInfo(parseDate);
            return new BaseResponse<>(intakeMealInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/report/change/calorie")
    public BaseResponse<CalorieReportResDto> getCalorieChangeReport(){
        try{
            CalorieReportResDto calorieReportInfo = healthService.getCalorieReportInfo();
            return new BaseResponse<>(calorieReportInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/report/change/weight")
    public BaseResponse<WeightReportResDto> getWeightChangeReport(){
        try{
            WeightReportResDto weightReportInfo = healthService.getWeightReportInfo();
            return new BaseResponse<>(weightReportInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/report/balance/portion")
    public BaseResponse<NutrientWeekPortionDto> getHaveNutrientPortion(@RequestParam("begin") String begin,@RequestParam("end") String end){
        try{
            LocalDate parseBegin = LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate parseEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            NutrientWeekPortionDto nutrientPortionInfo = healthService.getNutrientPortionInfo(parseBegin,parseEnd);
            return new BaseResponse<>(nutrientPortionInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/report/balance/detail")
    public BaseResponse<NutrientWeekInfoResDto> getWeekNutrientInfo(@RequestParam("begin") String begin,@RequestParam("end") String end){
        try{
            LocalDate parseBegin = LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate parseEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            NutrientWeekInfoResDto nutrientWeekInfo = healthService.getNutrientWeekInfo(parseBegin,parseEnd);
            return new BaseResponse<>(nutrientWeekInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/report/balance/graph")
    public BaseResponse<NutrientWeekPortionInfoResDto> getWeekNutrientPortion(@RequestParam("begin") String begin,@RequestParam("end") String end){
        try{
            LocalDate parseBegin = LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate parseEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            NutrientWeekPortionInfoResDto weekNutrientPortion = healthService.getWeekNutrientPortion(parseBegin,parseEnd);
            return new BaseResponse<>(weekNutrientPortion);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/report/meal/graph")
    public BaseResponse<MealPortionResDto> getWeekMealPortion(@RequestParam("begin") String begin,@RequestParam("end") String end){
        try {
            LocalDate parseBegin = LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate parseEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            MealPortionResDto mealPortion = healthService.getMealPortion(parseBegin, parseEnd);
            return new BaseResponse<>(mealPortion);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @GetMapping("/report/meal/list")
    public BaseResponse<HadMealInfoResDto> getWeekMealList(@RequestParam("begin") String begin,@RequestParam("end") String end){
        try {
            LocalDate parseBegin = LocalDate.parse(begin, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate parseEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            HadMealInfoResDto hadMealInfo = healthService.getHadMealInfo(parseBegin, parseEnd);
            return new BaseResponse<>(hadMealInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
