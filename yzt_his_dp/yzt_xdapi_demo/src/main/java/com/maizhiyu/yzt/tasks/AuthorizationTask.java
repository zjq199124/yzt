package com.maizhiyu.yzt.tasks;


import com.maizhiyu.yzt.feign.FeignToken;
import com.maizhiyu.yzt.feign.FeignYptClient;
import feign.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Log4j2
@Component
@EnableScheduling
@ConditionalOnProperty(prefix = "api.ypt", name = "cronenable", havingValue = "true")
public class AuthorizationTask {

    @Value("${api.ypt.customer}")
    String customer;

    @Value("${api.ypt.secretkey}")
    String secretkey;

    @Autowired
    private FeignYptClient yptClient;

    @PostConstruct
    public void init() {
        authorizationTask();
    }

    @Scheduled(cron = "${api.ypt.cron}")
    public void authorizationTask() {
        log.info("更新token"+customer+secretkey);
        // 登录
        Response response = yptClient.login(customer, secretkey);
        log.info("更新token-response:"+response);
        // 获取头
        Collection<String> collection = response.headers().get("Authorization");
        log.info("Authorization:"+collection);
        // 判断头
        if (collection.size() > 0) {
            // 获取token
            String token = (String) collection.toArray()[0];
            // 更新token
            FeignToken.setToken(token);
        }
    }

}
