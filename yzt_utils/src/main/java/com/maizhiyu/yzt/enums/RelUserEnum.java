package com.maizhiyu.yzt.enums;

/**
 * 患者检查类别枚举
 */
public enum RelUserEnum {
    PARENTS(1, "父母"),

    LOVERS(2,"爱人"),

    CHILDREN(3, "子女"),

    OTHERS(4,"其他");


    private int code;
    private String name;

    RelUserEnum(int code, String name) {
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
        for (RelUserEnum value : RelUserEnum.values()) {
            if (value.getCode() == code) {
                return value.getName();
            }
        }
        return null;
    }
}
