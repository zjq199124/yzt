package com.maizhiyu.yzt;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.maizhiyu.yzt"})
@EnableFeignClients
public class YztXdapiJiaozuofuyou {

    public static void main(String[] args) {
        SpringApplication.run(YztXdapiJiaozuofuyou.class, args);
    }
}
