package com.gehad.todotask.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static String dateTimeToshow(Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - date.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - date.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - date.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - date.getTime());

            if (seconds < 60) {
                return (seconds + " seconds ago");
            } else if (minutes < 60) {
                return (minutes + " minutes ago");
            } else if (hours < 24) {
                return (hours + " hours ago");
            } else {
                return (days + " days ago");
            }
        } catch (Exception j) {
            j.printStackTrace();
        }
        return "";
    }

}