package com.dev.utils.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 런타임 예외 처리시 Response status 처리
 * */

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private final BaseResponseStatus status;
}
