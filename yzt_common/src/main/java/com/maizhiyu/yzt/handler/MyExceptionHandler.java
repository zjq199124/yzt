package com.maizhiyu.yzt.handler;


import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ResponseBody
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public Result businessExceptionHandler(BusinessException e) {
        System.out.println("### 发生异常 BusinessException：" + e.getMessage());
        return Result.failure(10000, e.getMessage());
    }

//    @ExceptionHandler(value = Exception.class)
//    public Result commonExceptionHandler(Exception e) {
//        System.out.println("### 发生异常 Exception：" + e.getMessage());
//        e.printStackTrace();
//        return Result.failure(10000, e.getMessage());
//    }
}
