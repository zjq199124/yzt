package com.maizhiyu.yzt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class YztXdapiDemo implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(YztXdapiDemo.class, args);
    }

    @Override
    public void run(ApplicationArguments args){
        log.info("application start...");
    }
}
