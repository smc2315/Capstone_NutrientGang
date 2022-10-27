package com.dev.member.service;

import com.dev.member.dto.MemberInfoResDto;
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
    public MemberInfoResDto getMemberInfo(String email){
        MemberInfoResDto memberInfoResDto = memberRepository.findByEmail(email)
                .map(MemberInfoResDto::of)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_USER));// 유저 정보가 없습니다.
        return memberInfoResDto;
    }

    @Transactional(readOnly = true)
    public MemberInfoResDto getMyInfo() {
        MemberInfoResDto memberInfoResDto = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberInfoResDto::of)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LOGOUT_MEMBER)); // 로그인 유저 정보가 없습니다
        return memberInfoResDto;
    }
}
