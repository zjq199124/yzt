package com.maizhiyu.yzt.enums;

/**
 * API 统一返回状态码
 */
public enum FamilyTypeEnum {

    /* 家人关系枚举 */
    SELF(0, "本人"),
    PARENT(1,"父母"),
    FATHER(2, "父亲"),
    MOTHER(3, "母亲"),
    CHILD(4,"子女"),
    SON(5, "儿子"),
    DAUGHTER(6, "女儿"),
    SPOUSE(7, "爱人"),
    WIFE(8,"妻子"),
    HUSBAND(9,"丈夫"),
    OTHER(10, "其他");


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
