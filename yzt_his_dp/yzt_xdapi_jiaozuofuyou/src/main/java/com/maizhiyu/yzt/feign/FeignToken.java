package com.maizhiyu.yzt.feign;

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
            FeignToken.token = token;
        }
    }

}
