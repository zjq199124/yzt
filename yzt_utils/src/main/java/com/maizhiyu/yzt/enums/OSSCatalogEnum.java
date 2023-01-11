package com.maizhiyu.yzt.enums;

/**
 * oss 文件存储目录
 */
public enum OSSCatalogEnum {
    INFRARED("equipment/hw/", "红外成像设备检查文件"),
    MERIDIAN("equipment/jl/", "经络检测设备检测文件"),
    INFRARED_IMG("equipment/hw/imgs/", "红外成像设备检查文件中的图片")

    ;

    OSSCatalogEnum(String path, String remark) {
        this.path = path;
        this.remark = remark;
    }

    /**
     * 目录
     */
    private String path;
    /**
     * 备注
     */
    private String remark;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
