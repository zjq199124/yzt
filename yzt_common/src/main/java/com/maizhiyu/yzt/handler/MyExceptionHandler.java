package com.maizhiyu.yzt.handler;


import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.exception.HisException;
import com.maizhiyu.yzt.result.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@ResponseBody
@ControllerAdvice
public class MyExceptionHandler {

    // 参数验证异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result validationExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField() + ":" + error.getDefaultMessage() + ";");
            }
            return Result.failure(10000, builder.toString());
        }
        return Result.failure(10000, e.getMessage());
    }

    // 业务处理异常
    @ExceptionHandler(value = BusinessException.class)
    public Result businessExceptionHandler(BusinessException e) {
        System.out.println("### 发生异常 BusinessException：" + e.getMessage());
        return Result.failure(10000, e.getMessage());
    }

    // HIS处理异常
    @ExceptionHandler(value = HisException.class)
    public Result hisExceptionHandler(HisException e) {
        return Result.failure(10000, e.getMessage());
    }

    // 其他处理异常
    @ExceptionHandler(value = Exception.class)
    public Result commonExceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.failure(10000, e.getMessage());
    }
}
