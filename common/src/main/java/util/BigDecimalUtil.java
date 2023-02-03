package util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 */

public class BigDecimalUtil {
    public static double getBigDecimalValue(double value) {
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double getBigDecimalValue(String value) {
        BigDecimal bg = new BigDecimal(Double.parseDouble(value));
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String getNumber_2(String num) {
        float money = Float.parseFloat(num);
        StringBuilder strBuilder = new StringBuilder();
        if (money >= 10000) {
            strBuilder.append(money / 10000);
            if (!strBuilder.toString().contains(".")) {
                return strBuilder.append(".00万").toString();
            } else {
                int length = strBuilder.substring(strBuilder.lastIndexOf(".") + 1, strBuilder.length()).length();
                if (length == 2) {
                    return strBuilder.append("万").toString();
                } else if (length > 2) {
                    return strBuilder.substring(0, strBuilder.lastIndexOf(".") + 3) + "万";
                } else {
                    return strBuilder.append("0万").toString();
                }
            }
        } else {
            strBuilder.append(num);
            if (!strBuilder.toString().contains(".")) {
                return strBuilder.append(".00").toString();
            } else {
                int length = strBuilder.substring(strBuilder.lastIndexOf(".") + 1, strBuilder.length()).length();
                if (length == 2) {
                    return strBuilder.toString();
                } else if (length > 2) {
                    return strBuilder.substring(0, strBuilder.lastIndexOf(".") + 3);
                } else {
                    return strBuilder.append("0").toString();
                }
            }
        }

    }

    public static String getNumber_3(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0.00";
        }
        float money = Float.parseFloat(num);

        return String.format(Locale.CHINA, "%.2f", money);
    }

    /**
     * 1、配件商城所有商品带有价钱的都要标有人民币符号“¥”，不需要“元”字
     * 2、售车商城，车辆价格过万的加“万” ，例如：¥30.68万
     * 售车商城，厂商指导价过万的加“万” ，例如：¥30.68万
     * 适用于后台返回以万元为单位的数字
     *
     * @param num
     * @return
     */
    public static String getNumber(String num) {
        float money = 0;
        try {
            if (TextUtils.isEmpty(num)) {
                return "0.00";
            }
            money = Float.parseFloat(num);
        } catch (Exception o) {
        }
        return "¥" + String.format(Locale.CHINA, "%.2f", money) + "万";
    }

    /**
     * 适用于保留两位小数不转化为万元
     *
     * @param num
     * @return
     */
    public static String getNumberFormat(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0.00";
        }
        float money = Float.parseFloat(num);
        return "¥" + String.format(Locale.CHINA, "%.2f", money);
    }

    /**
     * 使用于金额大于一万
     * 单位以万元显示
     * * @param num
     *
     * @return
     */
    public static String getNumber￥(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0.00";
        }
        float money = Float.parseFloat(num);
        if (money > 10000) {
            String numStr = money % 10000 == 0 ?
                    String.valueOf(money / 10000) : String.format(Locale.CHINA, "%.2f", money / 10000 - 0.005);
            return "¥" + numStr + "万";
        }
        return "¥" + String.format(Locale.CHINA, "%.2f", money);
    }

    public static String getDoubleNumValue(String value) {
        try {
            return String.format(Locale.CHINA, "%.2f", Double.valueOf(value));
        } catch (Exception o) {

        }
        return "0.00";
    }


    public static String getDouble(double dou) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(dou);

    }

    public static String getInt(double dou) {
        DecimalFormat df = new DecimalFormat("0");
        return df.format(dou);
    }
}
