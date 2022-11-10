package com.dev.register.controller;

import com.dev.register.dto.MealRegisterReqDto;
import com.dev.register.dto.MealRegisterResDto;
import com.dev.register.service.RegisterService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
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
}
