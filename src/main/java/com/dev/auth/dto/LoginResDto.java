package com.dev.auth.dto;

import com.dev.jwt.dto.TokenDto;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResDto {
    private String username;
    private TokenDto tokenDto;

}
