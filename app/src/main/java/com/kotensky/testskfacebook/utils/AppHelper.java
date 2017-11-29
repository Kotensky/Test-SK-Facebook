package com.kotensky.testskfacebook.utils;


import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppHelper {

    public static String convertTime(String createdTime) {
        long now = System.currentTimeMillis();
        return String.valueOf(
                DateUtils.getRelativeTimeSpanString(
                        getDateInMillis(createdTime), now, DateUtils.MINUTE_IN_MILLIS));
    }

    private static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
