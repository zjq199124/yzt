package com.maizhiyu.yzt.utils.ossKit;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@Slf4j
@ConfigurationProperties(prefix = OSSConfig.PREFIX)
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

    @Bean
    public OSS ossClient() {
        if (StrUtil.isEmpty(getAccessKeyId()) || StrUtil.isEmpty(getAccessKeySecret())) {
            log.debug("阿里云OSS 配置参数未配置......, ossClient 初始化失败！！！");
            return null;
        }
        DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider(getAccessKeyId(), getAccessKeySecret());
        ClientBuilderConfiguration clientConfiguration = new ClientBuilderConfiguration();
        return new OSSClientBuilder().build(getEndpoint(), defaultCredentialProvider, clientConfiguration);
    }

}
