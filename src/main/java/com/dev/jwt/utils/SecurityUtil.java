package com.dev.jwt.utils;

import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
    private SecurityUtil() {}

    public static Long getCurrentMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_AUTHENTICATION); // Security Context 에 인증 정보가 없습니다.
        }

        return Long.parseLong(authentication.getName());
    }
}
