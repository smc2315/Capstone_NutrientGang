package com.dev.auth.controller;

import com.dev.auth.dto.LoginReqDto;
import com.dev.auth.dto.LoginResDto;
import com.dev.auth.dto.ReissueResDto;
import com.dev.auth.dto.TokenReqDto;
import com.dev.auth.service.AuthService;
import com.dev.jwt.dto.TokenDto;
import com.dev.member.dto.MemberReqDto;
import com.dev.member.dto.MemberResDto;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public BaseResponse<MemberResDto> signup(@RequestBody MemberReqDto memberReqDto){
        try{
            MemberResDto memberResDto = authService.signup(memberReqDto);
            return new BaseResponse<>(memberResDto);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/login")
    public BaseResponse<LoginResDto> login(@RequestBody LoginReqDto loginReqDto){
        try{
            LoginResDto loginResDto = authService.login(loginReqDto);
            return new BaseResponse<>(loginResDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/reissue")
    public BaseResponse<ReissueResDto> reissue(@RequestBody TokenReqDto tokenReqDto){
        try{
            ReissueResDto reissue = authService.reissue(tokenReqDto);
            return new BaseResponse<>(reissue);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
