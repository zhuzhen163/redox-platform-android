package com.redoxyt.platform.widget.camerautil.camera;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Author       zz
 * Desc	        ocr识别
 */
public class IDCardCamera {

    public final static int    TYPE_IDCARD_FRONT     = 1;//身份证正面
    public final static int    TYPE_BANK_FRONT      = 2;//银行卡正面
    public final static int    TYPE_JIALICENSE_FRONT      = 3;//驾驶证正面

    public final static int    TYPE_XINGLICENSE_FRONT      = 4;//行驶证正面
    public final static int    TYPE_XINGLICENSE_BACK      = 5;//行驶证附页

    public final static int    TYPE_TRAILERJIALICENSE_FRONT      = 6;//挂车行驶证正面
    public final static int    TYPE_TRAILERJIALICENSE_BACK      = 7;//挂车行驶证附页

    public final static int    TYPE_BUSINESSLICENSE     = 8;//营业执照
    public final static int    TYPE_ROADPERMIT      = 9;//道路运输经营许可证
    public final static int    TYPE_ROAD_TRANSPORT      = 10;//道路运输从业资格证
    public final static int    TYPE_ROAD      = 11;//道路运输证

    public final static int    TYPE_DRIVER_STATEMENT      = 12;//司机声明
    public final static int    TYPE_ENTERPRISE_STATEMENT      = 13;//企业申明
    public final static int    TYPE_CAR      = 14;//车辆


    public final static int    RESULT_CODE           = 0X11;//结果码
    public final static int    PERMISSION_CODE_FIRST = 0x12;//权限请求码
    public final static String TAKE_TYPE             = "take_type";//拍摄类型标记
    public final static String IMAGE_PATH            = "image_path";//图片路径标记

    private final WeakReference<Activity> mActivity;

    public static IDCardCamera create(Activity activity) {
        return new IDCardCamera(activity);
    }

    private IDCardCamera(Activity activity) {
        this.mActivity = new WeakReference(activity);
    }

    /**
     * 打开相机
     *
     * @param IDCardDirection 身份证方向（TYPE_IDCARD_FRONT / TYPE_IDCARD_BACK）
     */
    public void openCamera(int IDCardDirection) {
        Activity activity = this.mActivity.get();
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(TAKE_TYPE, IDCardDirection);
        activity.startActivityForResult(intent, IDCardDirection);
    }

    /**
     * 获取图片路径
     *
     * @param data Intent
     * @return 图片路径
     */
    public static String getImagePath(Intent data) {
        if (data != null) {
            return data.getStringExtra(IMAGE_PATH);
        }
        return "";
    }
}

