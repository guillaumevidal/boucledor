package com.realite.boucledor.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private DateUtil() {
        //private hidden constructor
    }

    private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat(
            "dd/MM/yy");

    public static String formatDateTimeForDisplay(Context context, String timeToFormat) {
        return  formatWithFormat(context, timeToFormat, DISPLAY_FORMAT);
    }

    public static String formatDateTimeForDb(Context context, String timeToFormat) {
        return formatWithFormat(context, timeToFormat, DB_FORMAT);
    }

    private static String formatWithFormat(Context context, String timeToFormat, SimpleDateFormat dateFormat) {
        String finalDateTime = "";
        Date date;
        if (timeToFormat != null) {
            try {
                date = dateFormat.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;

    }




}
