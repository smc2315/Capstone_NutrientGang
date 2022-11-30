package com.dev.health.controller;

import com.dev.health.dto.ForUpdateHealthStatusResDto;
import com.dev.health.dto.UpdateMemberHealthReq;
import com.dev.health.dto.UpdateMemberHealthRes;
import com.dev.health.service.MemberHealthService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/memberHealth")
@RequiredArgsConstructor
public class MemberHealthController {

    private final MemberHealthService memberHealthService;

    @PutMapping("/update")
    public BaseResponse<UpdateMemberHealthRes> updateMemberHealthInfo(@RequestBody UpdateMemberHealthReq updateMemberHealthReq){
        try{
            UpdateMemberHealthRes updateMemberHealthRes = memberHealthService.UpdateMemberHealthInfo(updateMemberHealthReq);
            return new BaseResponse<>(updateMemberHealthRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping()
    public BaseResponse<ForUpdateHealthStatusResDto> getForUpdateHealthStatus(@RequestParam("date") String date){
        try {
            LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ForUpdateHealthStatusResDto forUpdateHealthStatus = memberHealthService.getForUpdateHealthStatus(parseDate);
            return new BaseResponse<>(forUpdateHealthStatus);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
