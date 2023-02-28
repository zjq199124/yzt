package com.maizhiyu.yzt.enums;

import lombok.Getter;
@Getter
public enum UpLoadType {


    YES("1","已上传"),
    NO("0","未上传");


    private String code;

    private String desc;

    private UpLoadType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}