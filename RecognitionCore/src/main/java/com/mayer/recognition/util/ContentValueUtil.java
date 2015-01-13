package com.mayer.recognition.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dot on 14.01.2015.
 */
public class ContentValueUtil {

    private static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);

    public static String decimal(BigDecimal decimal) {
        if (decimal == null) {
            return "";
        }
        return decimalFormat.get().format(decimal);
    }

    public static long _long(String value, long def) {
        if (value == null) {
            return def;
        }
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return def;
        }
    }

    public static Long _long(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static String date(Date value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getTime());
    }

    public static Date date(String value) {
        long time = _long(value, 0);
        if (time == 0)
            return null;
        return new Date(time);
    }

    private static ThreadLocal<DecimalFormat> decimalFormat = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            DecimalFormat format = new DecimalFormat("0.00");
            format.setDecimalFormatSymbols(otherSymbols);
            format.setParseBigDecimal(true);
            return format;
        }
    };

    private static ThreadLocal<DecimalFormat> quantityFormat = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            DecimalFormat format = new DecimalFormat("0.000");
            format.setDecimalFormatSymbols(otherSymbols);
            format.setParseBigDecimal(true);
            return format;
        }
    };
}
