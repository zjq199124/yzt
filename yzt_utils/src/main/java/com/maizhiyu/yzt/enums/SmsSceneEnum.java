package com.maizhiyu.yzt.enums;

/**
 * 患者检查类别枚举
 */
public enum SmsSceneEnum {
    LOGIN_PREFIX("LOGIN", "短信验证码Redis中key前缀");

    private String code;
    private String name;

    SmsSceneEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (SmsSceneEnum value : SmsSceneEnum.values()) {
            if (value.getCode() == code) {
                return value.getName();
            }
        }
        return null;
    }
}
