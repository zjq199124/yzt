package com.maizhiyu.yzt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class YztWebWxApplication {

    public static void main(String[] args) {
        SpringApplication.run(YztWebWxApplication.class, args);
    }

}
