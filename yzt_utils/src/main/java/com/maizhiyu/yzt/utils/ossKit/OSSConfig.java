package com.maizhiyu.yzt.utils.ossKit;

import lombok.Data;

@Data
public class OSSConfig {
    public static final String PREFIX = "oss";
    //存储空间
    private String endpoint;
    //accessKeyId
    private String accessKeyId;
    //accessKeySecret
    private String accessKeySecret;
    //公共存储空间名称
    private String publicBucket;
    //公共存储空间url
    private String publicBucketUrl;
    //私有存储空间名称
    private String privateBucket;
    //私有存储空间url
    private String privateBucketUrl;
    //访问过期时间
    private Integer expiration;
    //水印图片
    private String styleWatermark;
    //图片缩略
    private String styleMicro;

}
