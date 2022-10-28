package com.dev.auth.service;

import com.dev.auth.dto.LoginReqDto;
import com.dev.auth.dto.TokenReqDto;
import com.dev.auth.entity.RefreshToken;
import com.dev.auth.repository.RefreshTokenRepository;
import com.dev.jwt.TokenProvider;
import com.dev.jwt.dto.TokenDto;
import com.dev.member.dto.MemberDto;
import com.dev.member.dto.MemberReqDto;
import com.dev.member.dto.MemberResDto;
import com.dev.member.entity.Member;
import com.dev.member.repository.MemberRepository;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResDto signup(MemberReqDto memberInfoReqDto){
        if(memberRepository.existsByEmail(memberInfoReqDto.getEmail())){
            throw new BaseException(BaseResponseStatus.DUPLICATE_USER);
        }

        MemberDto memberDto = new MemberDto(memberInfoReqDto.getEmail(), memberInfoReqDto.getPassword(), memberInfoReqDto.getUsername());
        Member member = memberDto.toMember(passwordEncoder);
        log.debug("email -> {}",member.getEmail());
        log.debug("username -> {}",member.getUsername());

        return MemberResDto.of(memberRepository.save(member));
        //todo: HealthStatus 에 info 저장 로직 만들기
    }

    @Transactional
    public TokenDto login(LoginReqDto loginReqDto){
        UsernamePasswordAuthenticationToken authenticationToken = loginReqDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenReqDto tokenReqDto){
        // refreshToken 검증
        if(!tokenProvider.validateToken(tokenReqDto.getRefreshToken())){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);// "Refresh Token이 유효하지 않습니다."
        }

        // accessToken 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenReqDto.getAccessToken());
        // 저장소에서 id 기반으로 refreshToken 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.LOGOUT_MEMBER));

        // refreshToken이 일치하는지 검사
        if(!refreshToken.getValue().equals(tokenReqDto.getRefreshToken())){
            throw new BaseException(BaseResponseStatus.NOT_EQUAL_TOKEN);
        }

        //새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;

    }
}
