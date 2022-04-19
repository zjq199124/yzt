package com.maizhiyu.yzt.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;


@Component
public class YztApiInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        // 通过template获取到请求体（已经被转成json）
//        String jsonBody = template.requestBody().asString();
//        // 构造通用的请求体
//        BaseReq baseReq = translateToBaseReq(jsonBody);
//        // 替换请求体
//        String baseReqStr = JSON.toJSONString(baseReq);
//        template.body(baseReqStr);
    }

}
