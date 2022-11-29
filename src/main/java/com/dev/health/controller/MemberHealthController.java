package com.dev.health.controller;

import com.dev.health.dto.UpdateMemberHealthReq;
import com.dev.health.dto.UpdateMemberHealthRes;
import com.dev.health.service.MemberHealthService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
