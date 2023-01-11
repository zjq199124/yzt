package com.maizhiyu.yzt.enums;

public enum FileTypeEnum {

    IMAGE(1, "图片" , "image/default/"),

    VEDIO(2, "视频","vedio/default/"),

    AUDIO(3, "音频","audio/default/"),

    FILE(4, "文件","file/default/");
    private int code;
    private String name;

    private String path;

    FileTypeEnum(int code, String name, String path) {
        this.code = code;
        this.name = name;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        for (FileTypeEnum value : FileTypeEnum.values()) {
            if (value.getCode() == code) {
                return value.getName();
            }
        }
        return null;
    }

    public static String getPathByCode(int code) {
        for (FileTypeEnum value : FileTypeEnum.values()) {
            if (value.getCode() == code) {
                return value.getPath();
            }
        }
        return null;
    }
}
