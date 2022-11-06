package com.dev.jwt;

import com.dev.auth.service.CustomUserDetailsService;
import com.dev.utils.response.BaseException;
import com.dev.utils.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication){
        if(authentication.getCredentials() == null){
            throw new BaseException(BaseResponseStatus.NOT_FOUND_PASSWORD); // 비밀번호를 찾지 못함
        }
        String presentedPassword = authentication.getCredentials().toString();
        if(!this.passwordEncoder.matches(presentedPassword,userDetails.getPassword())){
            throw new BaseException(BaseResponseStatus.WRONG_PASSWORD); //비밀 번호 틀림
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication){
        UserDetails user = null;
        try{
            user = retrieveUser(authentication.getName());
        }catch(BaseException exception){
            throw exception;
        }

        Object principalToReturn = user;
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principalToReturn
                ,authentication.getCredentials()
                ,this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        additionalAuthenticationChecks(user,result);
        result.setDetails(authentication.getDetails());
        return result;
    }

    protected final UserDetails retrieveUser(String username) {
        try{
            UserDetails loadedUser = customUserDetailsService.loadUserByUsername(username);
            if(loadedUser ==null){
                throw new InternalAuthenticationServiceException(
                        "UserDetailService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }catch (BaseException exception){
            log.debug("error in retrieveUser = {}",exception.getMessage());
            throw exception;
        }
        catch(Exception exception){
            throw new InternalAuthenticationServiceException("내부 인증 로직 중 알수 없는 오류가 발생했습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }



}
