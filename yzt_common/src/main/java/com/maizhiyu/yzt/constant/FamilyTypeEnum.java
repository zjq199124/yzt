package com.maizhiyu.yzt.constant;

/**
 * API 统一返回状态码
 */
public enum FamilyTypeEnum {

    /* 家人关系枚举 */
    SELF(0, "本人"),
    FATHER(1, "父亲"),
    MOTHER(2, "母亲"),
    SON(3, "儿子"),
    DAUGHTER(4, "女儿"),
    SPOUSE(5, "配偶"),
    OTHER(6, "其他");


    private Integer code;
    private String msg;

    FamilyTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static FamilyTypeEnum getByCode(Integer code) {
        for (FamilyTypeEnum enumConstant : FamilyTypeEnum.class.getEnumConstants()) {
            if (code.intValue() == enumConstant.code.intValue()) {
                return enumConstant;
            }
        }
        return null;
    }

    public static FamilyTypeEnum getByMsg(String msg) {
        for (FamilyTypeEnum enumConstant : FamilyTypeEnum.class.getEnumConstants()) {
            if (msg.equals(enumConstant.msg)) {
                return enumConstant;
            }
        }
        return null;
    }
}
