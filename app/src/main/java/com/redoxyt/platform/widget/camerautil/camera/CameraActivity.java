package com.redoxyt.platform.widget.camerautil.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.redoxyt.platform.R;
import com.redoxyt.platform.widget.camerautil.cropper.CropImageView;
import com.redoxyt.platform.widget.camerautil.utils.CommonUtils;
import com.redoxyt.platform.widget.camerautil.utils.FileUtils;
import com.redoxyt.platform.widget.camerautil.utils.ImageUtils;
import com.redoxyt.platform.widget.camerautil.utils.PermissionUtils;
import com.redoxyt.platform.widget.camerautil.utils.ScreenUtils;

import http.utils.Constant;
import util.StatusBarUtil;


/**
 *  zz
    拍照界面
 */
public class CameraActivity extends Activity implements View.OnClickListener {

    private CropImageView mCropImageView;
    private Bitmap        mCropBitmap;
    private CameraPreview mCameraPreview;
    private View          mLlCameraCropContainer;
    private ImageView     mIvCameraCrop;
    private ImageView     mIvCameraFlash;
    private View          mLlCameraOption;
    private View          mLlCameraResult;
    private TextView      mViewCameraCropBottom;
    private TextView      view_camera_crop_top;
    private FrameLayout   mFlCameraOption;
    private View          mViewCameraCropLeft;

    private int     mType;//拍摄类型
    private boolean isToast = true;//是否弹吐司，为了保证for循环只弹一次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*动态请求需要的权限*/
        boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(this, IDCardCamera.PERMISSION_CODE_FIRST,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
        if (checkPermissionFirst) {
            init();
        }
    }

    /**
     * 处理请求权限的响应
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 请求权限结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPermissions = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isPermissions = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) { //用户选择了"不再询问"
                    if (isToast) {
                        Toast.makeText(this, "请手动打开该应用需要的权限", Toast.LENGTH_SHORT).show();
                        isToast = false;
                    }
                }
            }
        }
        isToast = true;
        if (isPermissions) {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "允许所有权限");
            init();
        } else {
            Log.d("onRequestPermission", "onRequestPermissionsResult: " + "有权限不允许");
            finish();
        }
    }

    private void init() {
        try {
            setContentView(R.layout.activity_camera);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mType = getIntent().getIntExtra(IDCardCamera.TAKE_TYPE, 0);
            setStatusBar();
            initView();
            initListener();
        }catch (Exception e){
            confirm();
        }
    }

    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 100, null);
        int color = getResources().getColor(R.color.black_000);
        StatusBarUtil.setColor(this, color, 1);
        StatusBarUtil.setLightMode(this);
    }

    private void initView() {
        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        mLlCameraCropContainer = findViewById(R.id.ll_camera_crop_container);
        mIvCameraCrop = (ImageView) findViewById(R.id.iv_camera_crop);
        mIvCameraFlash = (ImageView) findViewById(R.id.iv_camera_flash);
        mLlCameraOption = findViewById(R.id.ll_camera_option);
        mLlCameraResult = findViewById(R.id.ll_camera_result);
        mCropImageView = findViewById(R.id.crop_image_view);
        mViewCameraCropBottom = (TextView) findViewById(R.id.view_camera_crop_bottom);
        view_camera_crop_top = (TextView) findViewById(R.id.view_camera_crop_top);
        mFlCameraOption = (FrameLayout) findViewById(R.id.fl_camera_option);
        mViewCameraCropLeft = findViewById(R.id.view_camera_crop_left);

        float screenMinSize = Math.min(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
        float screenMaxSize = Math.max(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
        float height = (int) (screenMinSize * 0.75);
        float width = (int) (height * 95.0f / 57.0f);
        //获取底部"操作区域"的宽度
        float flCameraOptionWidth = (screenMaxSize - width) / 2;
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        LinearLayout.LayoutParams cameraOptionParams = new LinearLayout.LayoutParams((int) flCameraOptionWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlCameraCropContainer.setLayoutParams(containerParams);
        mIvCameraCrop.setLayoutParams(cropParams);
        //获取"相机裁剪区域"的宽度来动态设置底部"操作区域"的宽度，使"相机裁剪区域"居中
        mFlCameraOption.setLayoutParams(cameraOptionParams);

        switch (mType) {
            case IDCardCamera.TYPE_IDCARD_FRONT:
                mIvCameraCrop.setImageResource(R.mipmap.bg_idcard);
                break;
            case IDCardCamera.TYPE_JIALICENSE_FRONT:
                mIvCameraCrop.setImageResource(R.mipmap.bg_jiashizheng);
                break;
            case IDCardCamera.TYPE_ROAD_TRANSPORT:
                mIvCameraCrop.setImageResource(R.mipmap.bg_cyzgz);
                break;
            case IDCardCamera.TYPE_CAR:
                mIvCameraCrop.setImageResource(R.mipmap.bg_car);
                break;
            case IDCardCamera.TYPE_ROAD:
            case IDCardCamera.TYPE_BUSINESSLICENSE:
            case IDCardCamera.TYPE_ROADPERMIT:
                mIvCameraCrop.setImageResource(R.mipmap.bg_dlys);
                break;
            case IDCardCamera.TYPE_XINGLICENSE_FRONT:
                mIvCameraCrop.setImageResource(R.mipmap.bg_xxzzy);
                break;
            case IDCardCamera.TYPE_XINGLICENSE_BACK:
                mIvCameraCrop.setImageResource(R.mipmap.bg_xszfy);
                break;
            case IDCardCamera.TYPE_TRAILERJIALICENSE_FRONT:
                mIvCameraCrop.setImageResource(R.mipmap.bg_gcxszzy);
                break;
            case IDCardCamera.TYPE_TRAILERJIALICENSE_BACK:
                mIvCameraCrop.setImageResource(R.mipmap.bg_gcxszfy);
                break;
        }

        /*增加0.5秒过渡界面，解决个别手机首次申请权限导致预览界面启动慢的问题*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCameraPreview.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 500);
    }

    private void initListener() {
        mCameraPreview.setOnClickListener(this);
        mIvCameraFlash.setOnClickListener(this);
        findViewById(R.id.iv_camera_close).setOnClickListener(this);
        findViewById(R.id.iv_camera_take).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_ok).setOnClickListener(this);
        findViewById(R.id.iv_camera_result_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.camera_preview) {
            mCameraPreview.focus();
        } else if (id == R.id.iv_camera_close) {
            finish();
        } else if (id == R.id.iv_camera_take) {
            if (!CommonUtils.isFastClick()) {
                try {
                    takePhoto();
                }catch (Exception e){
                    confirm();
                }
            }
        } else if (id == R.id.iv_camera_flash) {
            if (CameraUtils.hasFlash(this)) {
                boolean isFlashOn = mCameraPreview.switchFlashLight();
                mIvCameraFlash.setImageResource(isFlashOn ? R.mipmap.camera_flash_on : R.mipmap.camera_flash_off);
            } else {
                Toast.makeText(this, R.string.no_flash, Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.iv_camera_result_ok) {
            confirm();
        } else if (id == R.id.iv_camera_result_cancel) {
            mCameraPreview.setEnabled(true);
            mCameraPreview.addCallback();
            mCameraPreview.startPreview();
            mIvCameraFlash.setImageResource(R.mipmap.camera_flash_off);
            setTakePhotoLayout();
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        CameraUtils.getCamera().setOneShotPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(final byte[] bytes, Camera camera) {
                final Camera.Size size = camera.getParameters().getPreviewSize(); //获取预览大小
                camera.stopPreview();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final int w = size.width;
                            final int h = size.height;
                            Bitmap bitmap = ImageUtils.getBitmapFromByte(bytes, w, h);
                            cropImage(bitmap);
                        }catch (Exception e){
                            confirm();
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 裁剪图片
     */
    private void cropImage(Bitmap bitmap) {
        /*计算扫描框的坐标点*/
        float left = mViewCameraCropLeft.getWidth();
        float top = mIvCameraCrop.getTop();
        float right = mIvCameraCrop.getRight() + left;
        float bottom = mIvCameraCrop.getBottom();

        /*计算扫描框坐标点占原图坐标点的比例*/
        float leftProportion = left / mCameraPreview.getWidth();
        float topProportion = top / mCameraPreview.getHeight();
        float rightProportion = right / mCameraPreview.getWidth();
        float bottomProportion = bottom / mCameraPreview.getBottom();

        /*自动裁剪*/
        mCropBitmap = Bitmap.createBitmap(bitmap,
                (int) (leftProportion * (float) bitmap.getWidth()),
                (int) (topProportion * (float) bitmap.getHeight()),
                (int) ((rightProportion - leftProportion) * (float) bitmap.getWidth()),
                (int) ((bottomProportion - topProportion) * (float) bitmap.getHeight()));

        /*设置成手动裁剪模式*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //将手动裁剪区域设置成与扫描框一样大
                mCropImageView.setLayoutParams(new LinearLayout.LayoutParams(mIvCameraCrop.getWidth(), mIvCameraCrop.getHeight()));
                setCropLayout();
                mCropImageView.setImageBitmap(mCropBitmap);

                /*保存图片到sdcard并返回图片路径*/
                if (FileUtils.createOrExistsDir(Constant.CCB_PATH)) {
                    imagePath = Constant.CCB_PATH + "/" + "Image" + "";
                    ImageUtils.save(mCropBitmap, imagePath, Bitmap.CompressFormat.JPEG);
                }
            }
        });
    }
    String imagePath = "";

    /**
     * 设置裁剪布局
     */
    private void setCropLayout() {
        mIvCameraCrop.setVisibility(View.GONE);
        mCameraPreview.setVisibility(View.GONE);
        mLlCameraOption.setVisibility(View.GONE);
        mCropImageView.setVisibility(View.VISIBLE);
        mLlCameraResult.setVisibility(View.VISIBLE);
        mViewCameraCropBottom.setText("");
        view_camera_crop_top.setText("");
    }

    /**
     * 设置拍照布局
     */
    private void setTakePhotoLayout() {
        mIvCameraCrop.setVisibility(View.VISIBLE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mLlCameraOption.setVisibility(View.VISIBLE);
        mCropImageView.setVisibility(View.GONE);
        mLlCameraResult.setVisibility(View.GONE);
        mViewCameraCropBottom.setText(getString(R.string.touch_to_focus));
        view_camera_crop_top.setText(getString(R.string.touch_to_focus_top));

        mCameraPreview.focus();
    }

    /**
     * 点击确认，返回图片路径
     */
    private void confirm() {
        Intent intent = new Intent();
        intent.putExtra(IDCardCamera.IMAGE_PATH, imagePath);
        setResult(IDCardCamera.RESULT_CODE, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }
}