package com.redoxyt.platform.uitl;

import android.content.Context;

import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.widget.YTDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * Created by ${Crown} on 25/04/2018.
 * QQ:16573042
 * phone:13051059995
 */

public class CreditPermissionUtil {

    private static Context mContext;
    private static BaseActivity mActivity;
    private static String mPermissionDes;
    private static YTDialog ytDialog;

    public static void applyPermission(final BaseActivity activity, final Context context, String[] permissions, String permissionDes, final PermissionCallback callback) {
        ytDialog = new YTDialog(activity);
        mActivity = activity;
        mContext = context;
        mPermissionDes = permissionDes;
        AndPermission.with(activity)
                .permission(permissions)
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (callback != null) {
                            callback.onGrant();
                        }
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            goToSettingDialog();
                        }
                    }
                })
                .start();
    }


    /**
     * 用户禁止权限且不再提醒弹窗
     */
    private static void goToSettingDialog() {

        ytDialog.setCancel("友情提示", "没有" + mPermissionDes + "权限无法继续哦,请把权限赐给我吧!", "去设置",true);
        ytDialog.setCallBack(new YTDialog.SubmitCallBack() {
            @Override
            public void submit() {
                // 如果用户继续：
                AppUtils.goSetting(mActivity, mContext);
            }
        });

    }

    /**
     * 用户禁止权限后再次弹窗（样式自定义）
     */

    private static Rationale mRationale = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions,
                                  final RequestExecutor executor) {

            ytDialog.setContent("友情提示", "此操作需要您授予我们" + mPermissionDes + "权限,没有相关权限我们无法继续哦,请把权限赐给我吧!", "好,给你", "不,拒绝");
            ytDialog.setCallBack(new YTDialog.SubmitCallBack() {
                @Override
                public void submit() {
                    executor.execute();

                }
            }, new YTDialog.CancelCallBack() {
                @Override
                public void cancel() {
                    executor.cancel();

                }
            });

        }
    };

    public interface PermissionCallback {
        void onGrant();
    }
}
