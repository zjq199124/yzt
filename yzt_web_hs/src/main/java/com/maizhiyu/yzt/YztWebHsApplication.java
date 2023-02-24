package com.maizhiyu.yzt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class YztWebHsApplication implements ApplicationRunner {
    public static void main(String[] args) {
        System.out.printf("", "哈哈哈哈");
        SpringApplication.run(YztWebHsApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("application start...");
    }



}
