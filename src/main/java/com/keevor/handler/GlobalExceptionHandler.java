package com.keevor.handler;


import com.keevor.domain.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理业务运行异常信息
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public R<String> handlerRuntimeException(RuntimeException e){
        return R.error(e.getMessage());
    }
}
