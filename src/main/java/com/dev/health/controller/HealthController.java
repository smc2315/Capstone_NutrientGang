package com.dev.health.controller;

import com.dev.health.dto.*;
import com.dev.health.service.HealthService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Slf4j
public class HealthController {
    private final HealthService healthService;

    @GetMapping("/diary/calorie")
    public BaseResponse<CalorieInfoResDto> getCalorieInfo(@RequestBody DateReq dateReq){
        try{
            log.info("date => {}",dateReq.getDate());
            CalorieInfoResDto calorieInfo = healthService.getCalorieInfo(dateReq.getDate());
            log.info("필요 칼로리 = > {}",calorieInfo.getNeedCalorie());
            log.info("섭취 칼로리 = > {}",calorieInfo.getHaveCalorie());
            return new BaseResponse<>(calorieInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("diary/nutrient")
    public BaseResponse<NutrientStatusInfoResDto> getNutrientInfo(@RequestBody DateReq dateReq){
        try{
            NutrientStatusInfoResDto nutrientInfo = healthService.getNutrientInfo(dateReq.getDate());
            return new BaseResponse<>(nutrientInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("report/change/calorie")
    public BaseResponse<CalorieReportResDto> getCalorieChangeReport(){
        try{
            CalorieReportResDto calorieReportInfo = healthService.getCalorieReportInfo();
            return new BaseResponse<>(calorieReportInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("report/change/weight")
    public BaseResponse<WeightReportResDto> getWeightChangeReport(){
        try{
            WeightReportResDto weightReportInfo = healthService.getWeightReportInfo();
            return new BaseResponse<>(weightReportInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("report/balance/portion")
    public BaseResponse<NutrientWeekPortionDto> getHaveNutrientPortion(@RequestBody PeriodReq periodReq){
        try{
            NutrientWeekPortionDto nutrientPortionInfo = healthService.getNutrientPortionInfo(periodReq.getBegin(),periodReq.getEnd());
            return new BaseResponse<>(nutrientPortionInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("report/balance/detail")
    public BaseResponse<NutrientWeekInfoResDto> getWeekNutrientInfo(@RequestBody PeriodReq periodReq){
        try{
            NutrientWeekInfoResDto nutrientWeekInfo = healthService.getNutrientWeekInfo(periodReq.getBegin(), periodReq.getEnd());
            return new BaseResponse<>(nutrientWeekInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("report/balance/graph")
    public BaseResponse<List<NutrientWeekPortionInfoDto>> getWeekNutrientPortion(@RequestBody PeriodReq periodReq){
        try{
            List<NutrientWeekPortionInfoDto> weekNutrientPortion = healthService.getWeekNutrientPortion(periodReq.getBegin(), periodReq.getEnd());
            return new BaseResponse<>(weekNutrientPortion);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
