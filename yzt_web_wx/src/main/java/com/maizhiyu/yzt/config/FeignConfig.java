package com.maizhiyu.yzt.config;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * feign的http客户端配置
 */
@Configuration
public class FeignConfig {
    /**
     *No qualifying bean of type ‘org.springframework.boot.autoconfigure.http.HttpMessage
     */

//    @Bean
//    @ConditionalOnMissingBean
//    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
//        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
//    }

}
