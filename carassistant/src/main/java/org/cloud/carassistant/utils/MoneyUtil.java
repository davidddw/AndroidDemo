package org.cloud.carassistant.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/1
 */

public class MoneyUtil {

    private BigDecimal mBigDecimal;

    private MoneyUtil() {}

    public static BigDecimal objectToBigDecimal(@NonNull Object number) {
        BigDecimal value;
        if (number instanceof Integer) {
            value = new BigDecimal(Integer.toString((Integer)number));
        } else if (number instanceof Float) {
            value = new BigDecimal(Float.toString((Float)number));
        } else if (number instanceof Double) {
            value = new BigDecimal(Double.toString((Double)number));
        } else if (number instanceof Short) {
            value = new BigDecimal(Short.toString((Short)number));
        } else if (number instanceof Long) {
            value = new BigDecimal(Long.toString((Long)number));
        } else if (number instanceof String) {
            value = new BigDecimal(number.toString());
        } else { //未知的类型
            throw new IllegalArgumentException("unknown type!");
        }
        return value;
    }

    public static String replace(Number number) {
        return replace(String.valueOf(number));
    }

    public static String replace(String number) {
        if(TextUtils.isEmpty(number)) {
            return "0";
        }
        if(number.indexOf(".")>0) {
            number = number.replaceAll("0+?$", "");
            number = number.replaceAll("[.]$", "");
        }
        return number;
    }

    public static String replace(String number, int scale) {
        if (TextUtils.isEmpty(number)) { return "0"; }
        number = newInstance(number).round(scale).create().toString();
        if (number.indexOf(".") > 0) {
            number = number.replaceAll("0+?$", ""); //去掉后面无用的零
            number = number.replaceAll("[.]$", ""); //如小数点后面全是零则去掉小数点
        }
        return number;
    }

    public MoneyUtil round(int scale, int roundingMode) {
        if (scale >= 0) {
            mBigDecimal = mBigDecimal.divide(new BigDecimal("1"), scale, roundingMode);
        }
        return this;
    }

    public MoneyUtil round(int scale) {
        return round(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static String replace(Number number, int scale) {
        return replace(String.valueOf(number), scale);
    }

    public static MoneyUtil newInstance(@NonNull Object number) {
        MoneyUtil moneyUtil = new MoneyUtil();
        moneyUtil.mBigDecimal = objectToBigDecimal(number);
        return moneyUtil;
    }

    public BigDecimal create() {
        return mBigDecimal;
    }
}
