package com.maizhiyu.yzt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class YztWebApiApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(YztWebApiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("application start...");
    }

}
