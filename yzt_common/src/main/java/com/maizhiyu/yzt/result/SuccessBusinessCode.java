package com.maizhiyu.yzt.result;

/**
 * API 统一返回状态码
 */
public enum SuccessBusinessCode {

    /* 业务状态码 */
    PS_COMPLETE_MESSAGE(1, "完善信息个人信息");


    private Integer businessCode;
    private String msg;

    SuccessBusinessCode(Integer businessCode, String msg) {
        this.businessCode = businessCode;
        this.msg = msg;
    }

    public Integer businessCode() {
        return this.businessCode;
    }

    public String msg() {
        return this.msg;
    }
}
