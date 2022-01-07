package com.maizhiyu.yzt.utis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = OSSConfig.PREFIX)
public class AliyunOssConfig extends OSSConfig {

    @Bean
    public OSSConfig ossConfig(){
        return this;
    }
}
