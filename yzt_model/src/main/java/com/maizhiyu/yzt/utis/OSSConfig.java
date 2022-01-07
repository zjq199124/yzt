package com.maizhiyu.yzt.utis;
import org.springframework.stereotype.Component;

@Component
public class OSSConfig {

    public static final String PREFIX = "oss";

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String publicBucket;
    private String publicBucketUrl;
    private String privateBucket;
    private String privateBucketUrl;
    private Integer expiration;
    private String styleWatermark;
    private String styleMicro;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getPublicBucket() {
        return publicBucket;
    }

    public void setPublicBucket(String publicBucket) {
        this.publicBucket = publicBucket;
    }

    public String getPublicBucketUrl() {
        return publicBucketUrl;
    }

    public void setPublicBucketUrl(String publicBucketUrl) {
        this.publicBucketUrl = publicBucketUrl;
    }

    public String getPrivateBucket() {
        return privateBucket;
    }

    public void setPrivateBucket(String privateBucket) {
        this.privateBucket = privateBucket;
    }

    public String getPrivateBucketUrl() {
        return privateBucketUrl;
    }

    public void setPrivateBucketUrl(String privateBucketUrl) {
        this.privateBucketUrl = privateBucketUrl;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public String getStyleWatermark() {
        return styleWatermark;
    }

    public void setStyleWatermark(String styleWatermark) {
        this.styleWatermark = styleWatermark;
    }

    public String getStyleMicro() {
        return styleMicro;
    }

    public void setStyleMicro(String styleMicro) {
        this.styleMicro = styleMicro;
    }
}
