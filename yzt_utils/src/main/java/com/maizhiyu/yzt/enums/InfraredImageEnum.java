package com.maizhiyu.yzt.enums;

import java.util.HashMap;

/**
 * 红外检测图片保存枚举
 */
public enum InfraredImageEnum {
    DANJIN("胆经", "6-1"),
    DUMAI("督脉", "7-2"),
    FEIJIN("肺经", "4-3"),
    GANJIN("肝经", "6-0"),
    PIJIN("脾经", "4-1"),
    RENMAI("任脉", "7-3"),
    SHENJIN("肾经", "5-2"),
    WEIJIN("胃经", "4-2"),
    XINJIN("心经", "5-5"),
    PANGGUANJIN("膀胱经", "5-4"),
    DACHANGJIN("大肠经", "4-4"),
    XIAOCHANGJIN("小肠经", "5-3"),
    XINBAOJIN("心包经", "6-3"),
    JINWAIQIXU("经外奇穴", "7-0"),
    ;

    /**
     * 部位名称
     */
    private String name;
    /**
     * 位置编码
     */
    private String code;

    InfraredImageEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 以code 匹配name
     *
     * @param code
     * @return
     */
    public static String getName(String code) {
        for (InfraredImageEnum value : InfraredImageEnum.values()) {
            //包含匹配
            if (code.equals(value.getCode())) {
                return value.getName();
            }
        }
        return null;
    }
}
