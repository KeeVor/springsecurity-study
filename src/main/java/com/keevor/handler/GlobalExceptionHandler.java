package com.keevor.handler;


import com.keevor.common.CustomException;
import com.keevor.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 统一处理业务运行异常信息,只拦截抛出的自定义异常信息
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public R<String> handlerCustomException(CustomException e){
        log.info("拦截到异常信息：{}",e.getMessage());
        return R.error(e.getMessage());
    }




}
