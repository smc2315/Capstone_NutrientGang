package com.dev.register.controller;

import com.dev.register.dto.FoodDto;
import com.dev.register.service.FoodService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("")
    public BaseResponse<FoodDto> getFoodInfo(@RequestParam("name") String name){
        try {
            FoodDto foodInfo = foodService.findFood(name);
            return new BaseResponse<>(foodInfo);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
