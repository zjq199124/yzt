package com.maizhiyu.yzt.enums;

/**
 * 患者检查类别枚举
 */
public enum SmsTemplateEnum {
    VERIFICATION_CODE("SMS_266960575", "短信验证码"),
    REMIND_LATEST_APPOINTMENT("SMS_271400565", "最近一次预约时间提醒"),
    REMIND_TREATMENT("SMS_271450451", "治疗提醒");

    private String code;
    private String name;

    SmsTemplateEnum(String code, String name) {
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
        for (SmsTemplateEnum value : SmsTemplateEnum.values()) {
            if (value.getCode() == code) {
                return value.getName();
            }
        }
        return null;
    }
}
