package com.maizhiyu.yzt.utils;

import java.text.ParseException;
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

    public static String getNextDay(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long time = sdf.parse(date).getTime();
        long nextDay = time + 24 * 60 * 60 * 1000;
        return sdf.format(new Date(nextDay));
    }

}
