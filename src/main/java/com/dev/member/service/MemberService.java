package com.dev.member.service;

import com.dev.jwt.utils.SecurityUtil;
import com.dev.member.dto.MemberResDto;
import com.dev.member.repository.MemberRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResDto getMemberInfo(String email){
        MemberResDto memberResDto = memberRepository.findByEmail(email)
                .map(MemberResDto::of)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));// 유저 정보가 없습니다.
        return memberResDto;
    }

    @Transactional(readOnly = true)
    public MemberResDto getMyInfo() {
        MemberResDto memberResDto = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResDto::of)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LOGOUT_MEMBER)); // 로그인 유저 정보가 없습니다
        return memberResDto;
    }
}
