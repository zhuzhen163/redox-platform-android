package com.redoxyt.platform.uitl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by zz.
 * description: 白名单
 */

public class LgnoreBatteryOptimizations {

    public static void systemJudgment(Activity context){
        if (isHuawei()){
            goHuaweiSetting(context);
        }else if (isXiaomi()){
            goXiaomiSetting(context);
        }else if (isOPPO()){
            goOPPOSetting(context);
        }else if (isVIVO()){
            goVIVOSetting(context);
        }else if (isMeizu()){
            goMeizuSetting(context);
        }else if (isSamsung()){
            goSamsungSetting(context);
        }else if (isSmartisan()){
            goSmartisanSetting(context);
        }else if (isLeTV()){
            goLetvSetting(context);
        }
    }

    /**
     * 跳转到指定应用的首页
     */
    public static void showActivity(Context context,@NonNull String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    public static void showActivity(Context context,@NonNull String packageName, @NonNull String activityDir) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //华为
    public static boolean isHuawei() {
        if (Build.BRAND == null) {
            return false;
        } else {
            return Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor");
        }
    }
    public static void goHuaweiSetting(Activity context) {
        try {
            showActivity(context,"com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } catch (Exception e) {
            showActivity(context,"com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        }
    }

    //小米
    public static boolean isXiaomi() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("xiaomi");
    }
    public static void goXiaomiSetting(Activity context) {
        showActivity(context,"com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
    }

    //oppo
    public static boolean isOPPO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("oppo");
    }
    public static void goOPPOSetting(Activity context) {
        try {
            showActivity(context,"com.coloros.phonemanager");
        } catch (Exception e1) {
            try {
                showActivity(context,"com.oppo.safe");
            } catch (Exception e2) {
                try {
                    showActivity(context,"com.coloros.oppoguardelf");
                } catch (Exception e3) {
                    showActivity(context,"com.coloros.safecenter");
                }
            }
        }
    }

    //vivo
    public static boolean isVIVO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("vivo");
    }
    public static void goVIVOSetting(Activity context) {
        showActivity(context,"com.iqoo.secure");
    }

    //魅族
    public static boolean isMeizu() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("meizu");
    }
    public static void goMeizuSetting(Activity context) {
        showActivity(context,"com.meizu.safe");
    }

    //三星
    public static boolean isSamsung() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }
    public static void goSamsungSetting(Activity context) {
        try {
            showActivity(context,"com.samsung.android.sm_cn");
        } catch (Exception e) {
            showActivity(context,"com.samsung.android.sm");
        }
    }

    //乐视
    public static boolean isLeTV() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("letv");
    }

    public static void goLetvSetting(Activity context) {
        showActivity(context,"com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity");
    }

    //锤子

    public static boolean isSmartisan() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("smartisan");
    }
    public static void goSmartisanSetting(Activity context) {
        showActivity(context,"com.smartisanos.security");
    }
}
