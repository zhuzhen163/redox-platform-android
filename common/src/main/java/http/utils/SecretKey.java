package http.utils;

import android.text.TextUtils;

import com.redoxyt.app.common.BuildConfig;

import util.PreManagerCustom;


public class SecretKey {
    //注意: 这里的password(秘钥必须是16位的)
    //私钥   AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。
    private static final String KEY = BuildConfig.KEY;

    public static String getKey() {
        PreManagerCustom sp = PreManagerCustom.instance(Constant.mContext);
        if (!TextUtils.isEmpty(sp.getNextSecret())) {
            return sp.getNextSecret();
        } else {
            return KEY.substring(0, 16);
        }

    }

    public static void main(String[] args) {
        System.out.println(getKey());
    }


}

