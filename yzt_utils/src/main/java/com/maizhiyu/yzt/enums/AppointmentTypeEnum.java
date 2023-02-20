package com.maizhiyu.yzt.enums;

/**
 * API 统一返回状态码
 */
public enum AppointmentTypeEnum {

    /* 预约状态枚举 */
    NO_APPOINTMENT(1, "未预约"),
    IN_APPOINTMENT(2,"预约中"),
    COMPLETE_APPOINTMENT(3,"完成预约"),
    HAS_CANCEL(4, "已作废");


    private Integer code;
    private String msg;

    AppointmentTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static AppointmentTypeEnum getByCode(Integer code) {
        for (AppointmentTypeEnum enumConstant : AppointmentTypeEnum.class.getEnumConstants()) {
            if (code.intValue() == enumConstant.code.intValue()) {
                return enumConstant;
            }
        }
        return null;
    }

    public static AppointmentTypeEnum getByMsg(String msg) {
        for (AppointmentTypeEnum enumConstant : AppointmentTypeEnum.class.getEnumConstants()) {
            if (msg.equals(enumConstant.msg)) {
                return enumConstant;
            }
        }
        return null;
    }
}
