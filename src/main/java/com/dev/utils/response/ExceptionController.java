package com.dev.utils.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({Exception.class})
    public BaseResponse<Object> serverErrorException(final Exception exception){
        log.info(exception.getClass().getName());
        log.info("errot => {}",exception);
        return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
    }

}
