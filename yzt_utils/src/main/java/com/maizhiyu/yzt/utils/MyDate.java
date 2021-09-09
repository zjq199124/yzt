package com.maizhiyu.yzt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {

    public static String getTodayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getTomorrowStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        long tomorrow = today.getTime() + 24 * 60 * 60 * 1000;
        return sdf.format(new Date(tomorrow));
    }


}
