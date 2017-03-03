package org.cloud.carassistant.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/1
 */

public class DateUtil {
    private DateUtil() { }

    public static final String FORMAT_DATE      = "yyyy-MM-dd";

    public static final String FORMAT_TIME      = "HH:mm";

    public static String formatDate(String format, Long time) {
        return formatDate(new SimpleDateFormat(format, Locale.CHINA), time);
    }

    public static String formatDate(SimpleDateFormat format, Long time) {
        if (null == time || time <= 0) { return ""; }
        return format.format(new Date(String.valueOf(time).length() == 13 ? time : time * 1000));
    }
}
