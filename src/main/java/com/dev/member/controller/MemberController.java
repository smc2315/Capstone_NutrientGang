package com.dev.member.controller;

import com.dev.member.dto.MemberResDto;
import com.dev.member.service.MemberService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public BaseResponse<MemberResDto> getMyMemberInfo(){
        try{
            MemberResDto myInfo = memberService.getMyInfo();
            return new BaseResponse<MemberResDto>(myInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/{email}")
    public BaseResponse<MemberResDto> getMemberInfo(@PathVariable String email){
        try{
            MemberResDto memberInfo = memberService.getMemberInfo(email);
            return new BaseResponse<MemberResDto>(memberInfo);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
