package com.maizhiyu.yzt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YztXdapiJiaozuofuyou {

    public static void main(String[] args) {
        SpringApplication.run(YztXdapiJiaozuofuyou.class, args);
    }

}
