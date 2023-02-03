package utils;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import http.utils.Constant;

/**
 * @author Sxw
 * @date 2019/6/18.
 * description：判断HashMap字符串是否为空
 */

public class TextUtil {
    private static String value;

    public static String valueString(HashMap<String, Object> map, String key) {
        if (map.containsKey(key)) {
            String keyValue = map.get(key).toString();
            if (keyValue != null && keyValue.length() != 0 && !keyValue.equals("null")) {
                value = keyValue;
            } else {
                value = "";
            }
        } else {
            value = "";
        }

        return value;
    }

    /**
     * 手机号校验
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((1[0-9][0-9]))\\d{8}$";
        if (phone.length() != 11) {
            ToastUtil.ToastMessage(Constant.mContext, "", "手机号应为11位数", -1);
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                ToastUtil.ToastMessage(Constant.mContext, "", "请填入正确的手机号", -1);
            }
            return isMatch;
        }
    }

    public static String getNumber(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0.00" + "元";
        }
        float money = Float.parseFloat(num);
//        if (money > 10000) {
//            String numStr = money % 10000 == 0 ?
//                    String.valueOf(money / 10000) : String.format(Locale.CHINA, "%.2f", money / 10000 - 0.005);
//            return numStr + "万元";
//        }
        return getDouble(String.format(Locale.CHINA, "%.2f", money)) + "元";
    }

    public static String getDouble(String d) {
        double dou = Double.parseDouble(d);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(dou);
    }

    public static String getDouble￥(String d) {
        if (TextUtils.isEmpty(d)) {
            return "0.00元";
        } else {
            double dou = Double.parseDouble(d);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(dou) + "元";
        }
    }

    public static boolean isEtNull(EditText ed) {
        if (TextUtils.isEmpty(ed.getText().toString())) {
            ToastUtil.ToastMessage(Constant.mContext, "", ed.getHint().toString(), -1);
//            Toast.makeText(Constant.mContext, ed.getHint().toString(), Toast.LENGTH_LONG).show();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEtNull(EditText ed, String toast) {
        if (TextUtils.isEmpty(ed.getText().toString().trim())) {
            ToastUtil.ToastMessage(Constant.mContext, "", toast, -1);
//            Toast.makeText(Constant.mContext, ed.getHint().toString(), Toast.LENGTH_LONG).show();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStringNull(String s, String msg) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.ToastMessage(Constant.mContext, "", msg, -1);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPwdNull(String s, String msg) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.ToastMessage(Constant.mContext, "", msg, -1);
            return true;
        } else if (s.length() < 6) {
            ToastUtil.ToastMessage(Constant.mContext, "", "支付密码为6位数字", -1);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStrEq(String s1, String s2, String msg) {

        if (!s1.equals(s2)) {
            ToastUtil.ToastMessage(Constant.mContext, "", msg, -1);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断时间格式 格式必须为“YYYY-MM-dd” 2004-2-30 是无效的 2003-2-29 是无效的
     * @return
     */

    public static boolean isValidDate(String str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = (Date) formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNotBlank(String var0) {
        return !isBlank(var0);
    }

    public static boolean isBlank(String var0) {
        int var1;
        if(var0 != null && (var1 = var0.length()) != 0) {
            for(int var2 = 0; var2 < var1; ++var2) {
                if(!Character.isWhitespace(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
