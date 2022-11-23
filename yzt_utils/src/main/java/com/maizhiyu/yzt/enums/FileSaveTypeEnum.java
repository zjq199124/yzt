package com.maizhiyu.yzt.enums;

/**
 * 文件存储服务类型
 */
public enum FileSaveTypeEnum {
    /**
     * 阿里云公开
     */
    ALI_PUBLIC(0),
    /**
     * 阿里云私密
     */
    ALI_PRIVACY(1);

    private int code;

    FileSaveTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
