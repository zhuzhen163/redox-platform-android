package util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * description:权限工具类
 * author: niufeifei
 * date:2017/8/8 16:03
 */
public class PermissionsUtils {

    //危险权限分组名称 动态申请权限必须在manifest配置文件里声明
    //日程表
    public static String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    //相机
    public static String CAMERA = Manifest.permission.CAMERA;
    //联系人
    public static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    //位置
    public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    //麦克风
    public static String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    //电话
    public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static String USE_SIP = Manifest.permission.USE_SIP;
    public static String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    //传感器
    public static String SENSORS = Manifest.permission.BODY_SENSORS;
    //短信
    public static String SEND_SMS = Manifest.permission.SEND_SMS;
    public static String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static String READ_SMS = Manifest.permission.READ_SMS;
    public static String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    //储存
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static String SYSTEM_ALERT_WINDOW = Manifest.permission.SYSTEM_ALERT_WINDOW;


    public static String WAKE_LOCK = Manifest.permission.WAKE_LOCK;

    /**
     * @param activity       需要权限的界面
     * @param permissionList 需要的所有权限
     * @return 是否有所有权限 true拥有所有权限不需要授权 false需要授权
     */
    public static boolean requestPermission(Activity activity, List<String> permissionList) {


        List<String> applyList = havaPermissionList(activity, permissionList);
        if (!applyList.isEmpty()) {  //申请的集合不为空时，表示有需要申请的权限
            ActivityCompat.requestPermissions(activity, applyList.toArray(new String[applyList.size()]), 1);
            PermissionPageUtils per = new PermissionPageUtils(activity);
            return false;
        } else { //所有的权限都已经授权过了
            return true;
        }

    }


    /**
     * @param context            上下文对象检查是否已经有权限
     * @param NeedPermissionList 需要的所有权限
     * @return 需要申请的权限
     */
    private static List<String> havaPermissionList(Context context, List<String> NeedPermissionList) {
        List<String> applyList = new ArrayList<>();
        if (NeedPermissionList == null) {
            return applyList;
        }
        for (String Permission : NeedPermissionList) {
            if (ContextCompat.checkSelfPermission(context, Permission) != PackageManager.PERMISSION_GRANTED) {
                applyList.add(Permission);
            }
        }
        return applyList;
    }


    /**
     * 权限动态申请回调方法处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param premissionsCall
     */
    public static void onPermissionsResult(int requestCode, String[] permissions, int[] grantResults, PremissionsCall premissionsCall) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) { //安全写法，如果小于0，肯定会出错了
                    boolean isHavaAllPermissions = true;
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED) { //这个是权限拒绝
                            String s = permissions[i];
                            isHavaAllPermissions = false;
                            premissionsCall.onErrorPremissions(s);
                        } else { //授权成功了
                            String s = permissions[i];
                            premissionsCall.onSuccessPremissions(s);
                        }
                    }
                    premissionsCall.onAllPremissionHava(isHavaAllPermissions);

                }
                break;
            default:
                break;
        }

    }

    public static interface PremissionsCall {
        //权限被拒绝的单个权限回调
        void onErrorPremissions(String premissions);

        //权限允许的单个权限回调
        void onSuccessPremissions(String premissions);

        //所有权限总回调
        void onAllPremissionHava(boolean isHavaAll);


    }

}
