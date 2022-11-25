package com.maizhiyu.yzt.enums;

/**
 * 患者检查类别枚举
 */
public enum CheckTypeEnum {
    INFRARED(2, "红外成像检查"),

    MERIDIAN(1, "经络检查");
    private int code;
    private String name;

    CheckTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByCode(int code) {
        for (CheckTypeEnum value : CheckTypeEnum.values()) {
            if (value.getCode() == code) {
                return value.getName();
            }
        }
        return null;
    }
}
