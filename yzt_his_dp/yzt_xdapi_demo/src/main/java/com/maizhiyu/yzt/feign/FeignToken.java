package com.maizhiyu.yzt.feign;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FeignToken {

    private static Object lock = new Object();
    private static String token = "";

    public static String getToken() {
        synchronized (lock) {
            return token;
        }
    }

    public static void setToken(String token) {
        synchronized (lock) {
            log.info("token===:"+token);
            FeignToken.token = token;
        }
    }

}
