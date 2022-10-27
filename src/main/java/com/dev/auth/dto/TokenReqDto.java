package com.dev.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReqDto {
    private String accessToken;
    private String refreshToken;
}
