package com.maizhiyu.yzt.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@ApiModel(description="响应数据")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "接口成功后的业务状态码")
    private Integer businessCode;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg, T data,Integer businessCode) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.businessCode = businessCode;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), null);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), data);
    }

    public static Result success(Object data,SuccessBusinessCode successBusinessCode) {
        return new Result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), data,successBusinessCode.businessCode());
    }

    public static Result success(Object data,String msg) {
        return new Result(ResultCode.SUCCESS.code(), msg, data);
    }

    public static Result failure(ResultCode resultCode) {
        return new Result(resultCode.code(), resultCode.msg(), null);
    }

    public static Result failure() {
        return new Result(ResultCode.FAILURE.code(), ResultCode.FAILURE.msg(), null);
    }

    public static Result failure(String msg) {
        return new Result(ResultCode.FAILURE.code(), msg, null);
    }


    public static Result failure(ResultCode resultCode, Object data) {
        return new Result(resultCode.code(), resultCode.msg(), data);
    }

    public static Result failure(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result failure(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }
}
