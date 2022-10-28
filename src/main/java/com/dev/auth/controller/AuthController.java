package com.dev.auth.controller;

import com.dev.auth.dto.LoginReqDto;
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
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public BaseResponse<MemberResDto> signup(@RequestBody MemberReqDto memberReqDto){
        try{
            log.debug("email -> {}",memberReqDto.getEmail());
            log.debug("password -> {}",memberReqDto.getPassword());
            log.debug("username -> {}",memberReqDto.getUsername());
            log.debug("gender -> {}",memberReqDto.getGender());
            log.debug("activity -> {}",memberReqDto.getActivity());
            MemberResDto memberResDto = authService.signup(memberReqDto);
            return new BaseResponse<>(memberResDto);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/login")
    public BaseResponse<TokenDto> login(@RequestBody LoginReqDto loginReqDto){
        try{
            TokenDto tokenDto = authService.login(loginReqDto);
            return new BaseResponse<>(tokenDto);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/reissue")
    public BaseResponse<TokenDto> reissue(@RequestBody TokenReqDto tokenReqDto){
        try{
            TokenDto tokenDto = authService.reissue(tokenReqDto);
            return new BaseResponse<>(tokenDto);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
