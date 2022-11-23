package com.maizhiyu.yzt.utils.ossKit;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = OSSConfig.PREFIX)
public class AliyunOssConfig extends OSSConfig {

    @Bean
    public OSSConfig ossConfig() {
        return this;
    }

    @Bean
    public OSS ossClient() {
        DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider(getAccessKeyId(), getAccessKeySecret());
        ClientBuilderConfiguration clientConfiguration = new ClientBuilderConfiguration();
        return new OSSClientBuilder().build(getEndpoint(), defaultCredentialProvider, clientConfiguration);
    }

}
