package com.maizhiyu.yzt.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(description="响应数据")
public class Result implements Serializable {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), null);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), data);
    }

    public static Result failure(ResultCode resultCode) {
        return new Result(resultCode.code(), resultCode.msg(), null);
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
