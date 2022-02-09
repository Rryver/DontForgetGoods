package com.example.dontforgetgoods.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static final String DATE_FORMAT = "dd-MM-yyy HH:mm";

    public static String toStringDate(Timestamp timestamp, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(timestamp);
    }

    public static Timestamp toTimestampFromUnix(Long unixTimestamp) {
        return new Timestamp(unixTimestamp * 1000);
    }

}
