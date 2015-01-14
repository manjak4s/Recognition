package com.mayer.recognition.util;

import android.database.Cursor;
import android.text.TextUtils;

import com.mayer.recognition.model.dao.order.DiscountType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dot on 14.01.2015.
 */
public class ContentValueUtil {

    public static final int DECIMAL_SCALE = 2;
    public static final int QUANTITY_SCALE = 3;

    private static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);

    public static DiscountType discountType(Cursor c, int index) {
        return c.isNull(index) ? null : DiscountType.values()[ c.getInt(index) ];
    }

    public static String decimal(BigDecimal decimal) {
        if (decimal == null) {
            return "";
        }
        return decimalFormat.get().format(decimal);
    }

    public static String decimal(BigDecimal decimal, int scale) {
        if (decimal == null) {
            return "";
        }

        return scale <= DECIMAL_SCALE ? decimalFormat.get().format(decimal) : quantityFormat.get().format(decimal);
    }

    public static BigDecimal decimal(String decimalValue, int scale) {
        if (TextUtils.isEmpty(decimalValue))
            return BigDecimal.ZERO;
        try {
            return (BigDecimal) (scale <= DECIMAL_SCALE ? decimalFormat.get().parse(decimalValue) : quantityFormat.get().parse(decimalValue));
        } catch (ParseException e) {
            Logger.e("Parse number error", e);
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal decimal(Cursor c, int columnIndex) {
        return decimal(c.getString(columnIndex));
    }

    public static BigDecimal decimal(String decimalValue) {
        if (TextUtils.isEmpty(decimalValue)) {
            return BigDecimal.ZERO;
        }
        try {
            return (BigDecimal) decimalFormat.get().parse(decimalValue);
        } catch (ParseException e) {
            Logger.e("Parse number error", e);
            return BigDecimal.ZERO;
        }
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

    public static Date nullableDate(Cursor c, int index) {
        return c.isNull(index) ? null : new Date(c.getLong(index));
    }

    public static Date date(String value) {
        long time = _long(value, 0);
        if (time == 0)
            return null;
        return new Date(time);
    }

    public static String decimalQty(BigDecimal decimal) {
        return decimal(decimal, QUANTITY_SCALE);
    }

    public static BigDecimal decimalQty(Cursor c, int columnIndex) {
        return decimal(c.getString(columnIndex), QUANTITY_SCALE);
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
