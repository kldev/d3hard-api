package com.kldev.d3.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    public static DateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static String timeToDate(long date)
    {
        return FORMAT.format(new Date(date));
    }
}
