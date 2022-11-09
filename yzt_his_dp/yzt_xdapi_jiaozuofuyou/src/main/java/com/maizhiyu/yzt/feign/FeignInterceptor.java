package com.maizhiyu.yzt.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = FeignToken.getToken();
        requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
    }
}
