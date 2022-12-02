//package com.maizhiyu.yzt.utils.ossKit;
//
//import cn.hutool.core.lang.Assert;
//import cn.hutool.core.util.StrUtil;
//import com.aliyun.oss.ClientBuilderConfiguration;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.aliyun.oss.common.auth.DefaultCredentialProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConfigurationProperties(prefix = OSSConfig.PREFIX)
//@Slf4j
//public class AliyunOssConfig extends OSSConfig {
//
//    @Bean
//    public OSSConfig ossConfig() {
//        return this;
//    }
//
//    @Bean
//    public OSS ossClient() {
//        if(StrUtil.isEmpty(getAccessKeyId())||StrUtil.isEmpty(getAccessKeySecret())){
//            log.debug("阿里云OSS 配置参数未配置......");
//            return null;
//        }
//        DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider(getAccessKeyId(), getAccessKeySecret());
//        ClientBuilderConfiguration clientConfiguration = new ClientBuilderConfiguration();
//        return new OSSClientBuilder().build(getEndpoint(), defaultCredentialProvider, clientConfiguration);
//    }
//
//}
