package com.demand.management.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {
    public static String buildWithIsOn(String isOn) {
        String isNull = null;
        if (isOn != null && isOn.equals("1")) isNull = "isNull";
        if (isOn != null && isOn.equals("2")) isNull = "isNotNull";
        return isNull;
    }

    public static Date buildDateParams(String time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        if (time != null) return format.parse(time);
        else return null;
    }

    public static Date buildDateParams(String time, String timeFormat) throws ParseException {
        String timeForma2 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        if (timeFormat != null && !timeFormat.isEmpty()) timeForma2 = timeFormat;
        SimpleDateFormat format = new SimpleDateFormat(timeForma2);
        if (time != null) return format.parse(time);
        else return null;
    }
}
