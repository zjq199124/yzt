package com.maizhiyu.yzt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YztXdapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YztXdapiApplication.class, args);
    }

}
