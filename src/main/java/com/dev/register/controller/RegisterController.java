package com.dev.register.controller;

import com.dev.register.dto.EachMealInfoResDto;
import com.dev.register.dto.MealRegisterReqDto;
import com.dev.register.dto.MealRegisterResDto;
import com.dev.register.service.RegisterService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/meal")
    public BaseResponse<MealRegisterResDto> registerMeal(@RequestBody MealRegisterReqDto mealRegisterReqDto){
        try{
            MealRegisterResDto mealRegisterResDto = registerService.registerMeal(mealRegisterReqDto);
            return new BaseResponse<>(mealRegisterResDto);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/info/meal")
    public BaseResponse<EachMealInfoResDto> getEachMealInfo(@RequestParam("mealId") Long mealId,@RequestParam("meal") String mealType){
        try{
            EachMealInfoResDto forUpdateMealInfo = registerService.getForUpdateMealInfo(mealId, mealType);
            return new BaseResponse<>(forUpdateMealInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PutMapping("/update/meal")
    public BaseResponse<MealRegisterResDto> updateMealInfo(@RequestParam("mealId") Long mealId,@RequestParam("meal") String mealType, @RequestBody MealRegisterReqDto mealRegisterReqDto){
        try{
            MealRegisterResDto mealUpdateResDto = registerService.updateMealInfo(mealId, mealType, mealRegisterReqDto);
            return new BaseResponse<>(mealUpdateResDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @DeleteMapping("/delete/meal")
    public BaseResponse<MealRegisterResDto> deleteMealInfo(@RequestParam("mealId") Long mealId,@RequestParam("meal") String mealType){
        try{
            MealRegisterResDto deleteMealInfo = registerService.deleteMeal(mealId, mealType);
            return new BaseResponse<>(deleteMealInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
