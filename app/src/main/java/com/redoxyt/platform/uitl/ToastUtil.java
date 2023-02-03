package com.redoxyt.platform.uitl;

import android.view.Gravity;
import android.widget.Toast;

import com.redoxyt.platform.FactoryApplication;


/**
 *描述：书城
 *作者：Created by zhuzhen
 */
public class ToastUtil {
    public static Toast toast;

    public static void showToast(String str) {
        if (StringUtils.isNotBlank(str)){
            if (toast == null) {
                toast = Toast.makeText(FactoryApplication.getInstances(), str, Toast.LENGTH_LONG);
            } else {
                toast.cancel();
                toast = Toast.makeText(FactoryApplication.getInstances(), str, Toast.LENGTH_LONG);
                toast.setText(str);
            }
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public static void showToastShort(String str) {
        if (StringUtils.isNotBlank(str)){
            if (toast == null) {
                toast = Toast.makeText(FactoryApplication.getInstances(), str, Toast.LENGTH_SHORT);
            } else {
                toast.setText(str);
            }
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    public static void showToast(int contentId) {
        if (toast == null) {
            toast = Toast.makeText(FactoryApplication.getInstances(), contentId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(contentId);
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
