package com.redoxyt.platform.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.BindMessageBean;
import com.redoxyt.platform.bean.InfoBean;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.uitl.CreditPermissionUtil;
import com.redoxyt.platform.uitl.FileUtil;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.camerautil.camera.IDCardCamera;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import http.utils.Constant;
import util.CropImageUtils;
import util.GlideUtils;
import util.PermissionsUtils;
import util.PickImage;
import utils.ConfigUtils;
import utils.TextUtil;
import view.InfoView;
import widget.CommonToolbar;

/**
 * Created by zz.
 * description:身份认证
 */

public class InfoAuthActivity extends BaseActivity {

    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;
    @BindView(R.id.iv_driverLicense)
    InfoView iv_driverLicense;
    @BindView(R.id.iv_license)
    InfoView iv_license;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_carCode)
    EditText et_carCode;
    @BindView(R.id.tv_changeInfo)
    TextView tv_changeInfo;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.ll_driverLicense)
    LinearLayout ll_driverLicense;
    @BindView(R.id.ll_userName)
    LinearLayout ll_userName;
    private int pubType;//图片上传  类型 （行驶证传18）（驾驶证传3）
    private String userType = "";//0表示添加，1表示修改,2注册
    private String ivDriverLicensePath,ivLicensePath;
    private String ImagePath; //拍照 照片本地地址
    private Handler handler = new Handler();
    private String driverLicenceNum,carId = "";

    @Override
    public int setView() {
        return R.layout.acitivty_info_auth;
    }

    @Override
    public void initData() {
        userType = getIntent().getStringExtra("userType");
        if (StringUtils.isNotBlank(userType)){
            if (userType.equals("1")){
                tv_submit.setText("修改认证");
                findUserAuth();
            }else if (userType.equals("0")){
                ll_driverLicense.setVisibility(View.GONE);
                ll_userName.setVisibility(View.GONE);
            }
        }

        iv_driverLicense.getRlImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyPermission(R.id.iv_driverLicense);
            }
        });

        iv_license.getRlImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyPermission(R.id.iv_license);
            }
        });

        ctb_title.getGoBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userType.equals("2")){
                    startActivity(MainDriverActivity.class);
                }else {
                    finish();
                }
            }
        });
    }

    /**
     * 申请相机权限开始ocr扫描
     * @param viewId
     */
    private void applyPermission(int viewId) {
        String[] permissions = new String[]{PermissionsUtils.CAMERA,PermissionsUtils.WRITE_EXTERNAL_STORAGE,PermissionsUtils.READ_EXTERNAL_STORAGE};

        String permissionDes = "手机相册";
        CreditPermissionUtil.applyPermission(InfoAuthActivity.this, FactoryApplication.getInstances(), permissions, permissionDes,
                new CreditPermissionUtil.PermissionCallback() {
                    @Override
                    public void onGrant() {
                        if (R.id.iv_driverLicense == viewId){
                            pubType = 3;
                            showPictureDialog();
                        }else if (R.id.iv_license == viewId){
                            pubType = 18;
                            showPictureDialog();
                        }
                    }
                }
        );
    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_submit:
                if (!userType.equals("0")){
                    if (TextUtils.isEmpty(ivDriverLicensePath)) {
                        showToast("请拍摄/上传驾驶证");
                        return;
                    }
                    if (TextUtil.isEtNull(et_name,"请填写姓名")) return;
                }

                if (TextUtils.isEmpty(ivLicensePath)) {
                    showToast("请拍摄/上传行驶证");
                    return;
                }

                if (TextUtil.isEtNull(et_carCode,"请填写车牌号")) return;
                userAuth();
                break;
        }
    }

    /**
     * 选择图片弹窗
     */
    private void showPictureDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.Dialog);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_custom_layout, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.dialogAnim);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.findViewById(R.id.tv_take_photo).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.tv_take_pic).setVisibility(View.VISIBLE);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(view1 -> {
            //拍照
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
            list1.add("android.permission.CAMERA");
            if (PermissionsUtils.requestPermission(InfoAuthActivity.this, list1)) {
                ImagePath = Constant.CCB_PATH + "/" + "Image" + System.currentTimeMillis() + ".png";
                dialog.dismiss();
                if (ConfigUtils.getUsePhoto()){
                    if (pubType == 3){
                        IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_JIALICENSE_FRONT);
                    }else if (pubType == 18){//行驶证
                        IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_XINGLICENSE_FRONT);
                    }
                }else {
                    PickImage.pickImageFromCamera(InfoAuthActivity.this, ImagePath, 1000);
                }
            } else {
                showToast("需要照相机权限");
            }
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(view12 -> {
            //相册
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
            list1.add("android.permission.CAMERA");
            if (PermissionsUtils.requestPermission(InfoAuthActivity.this, list1)) {
                dialog.dismiss();
                PickImage.pickImageFromPhoto(InfoAuthActivity.this, 100);
            } else {
                showToast("需要照相机权限");
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view14 -> {
            //取消
            dialog.dismiss();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100://相册
                    if (data != null){
                        Uri uri = data.getData();
                        if (uri != null){
                            handler.postDelayed(() -> saveInfomation(FileUtil.getFilePathByUri(InfoAuthActivity.this, uri), pubType),10);
                        }
                    }
                    break;
                case 1000://拍照
                    handler.postDelayed(() -> {
                        saveInfomation(ImagePath, pubType);
                    },10);
                    break;
            }
        }else if (resultCode == IDCardCamera.RESULT_CODE) {
            //获取图片路径，显示图片
            final String path = IDCardCamera.getImagePath(data);
            if (!TextUtils.isEmpty(path)) {
                saveInfomation(path, pubType);
            }else {
                ConfigUtils.saveUsePhoto(false);
                PickImage.pickImageFromCamera(InfoAuthActivity.this, ImagePath, 1000);
            }
        }
    }

    /**
     * @param path 图片路径
     * @param type （企业声明传10）（行驶证传4）（身份证传1）（车主声明传9）（车辆照片传8）（银行卡传2）（驾驶证传3）
     */
    public void saveInfomation(String path, int type) {
        File file = null;
        try{
            file = CropImageUtils.compress(path);
        }catch (Exception e){
            e.printStackTrace();
            showToast("上传图片存在异常，请重新上传");
            return;
        }
        OkGo.<QueryVoLzyResponse<BindMessageBean>>post(BaseUrl.YT_Base + BaseUrl.pub)
                .params("type", type)
                .params("file", file)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<BindMessageBean>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);

                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<BindMessageBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<BindMessageBean>> response, String desc) {
                        BindMessageBean data = response.body().getData();
                        if (data != null) {
                            setViewData(data, type);
                        }
                    }
                });
    }

    private void setViewData(BindMessageBean map, int type) {
        if (type == 3) {//驾驶证
            ivDriverLicensePath = map.getDriverLicenceImage();
            driverLicenceNum = map.getDriverLicence();
            GlideUtils.loadImageView(InfoAuthActivity.this, ivDriverLicensePath, iv_driverLicense.getIvImg());
            iv_driverLicense.setText("重新上传");
            et_name.setText(map.getDriverName());//驾驶员姓名
        }else if (pubType == 18){//行驶证
            et_carCode.setText(map.getCarCode());//承运车牌号码
            ivLicensePath = map.getDrivingLicenseFront();
            GlideUtils.loadImageView(InfoAuthActivity.this, ivLicensePath, iv_license.getIvImg());
            iv_license.setText("重新上传");
        }
    }

    public void findUserAuth() {
        OkGo.<QueryVoLzyResponse<InfoBean>>get(BaseUrl.YT_Base + BaseUrl.findUserAuth)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<InfoBean>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);

                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<InfoBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<InfoBean>> response, String desc) {
                        InfoBean data = response.body().getData();
                        if (data.getTmsUserDriver() != null) {
                            setViewDataFind(data);
                        }else {
                            if (desc.contains("成功")) return;
                            showToast(desc);
                        }
                    }
                });
    }

    /**
     * 已认证信息回显
     * @param infoBean
     */
    public void setViewDataFind(InfoBean infoBean) {
        //驾驶证
        InfoBean.TmsUserDriverBean tmsUserDriver = infoBean.getTmsUserDriver();

        ivDriverLicensePath = tmsUserDriver.getDriverLicenceImage();
        GlideUtils.loadImageView(InfoAuthActivity.this, ivDriverLicensePath, iv_driverLicense.getIvImg());
        iv_driverLicense.setText("重新上传");

        et_name.setText(tmsUserDriver.getDriverName());//驾驶员姓名

        InfoBean.TmsUserCarBean tmsUserCar = infoBean.getTmsUserCar();
        carId = tmsUserCar.getCarId()+"";
        et_carCode.setText(tmsUserCar.getCarCode());//承运车牌号码
        ivLicensePath = tmsUserCar.getCarDrivingImage();
        GlideUtils.loadImageView(InfoAuthActivity.this, ivLicensePath, iv_license.getIvImg());
        iv_license.setText("重新上传");

    }

    public void userAuth() {
        HttpParams params = new HttpParams();
        if (StringUtils.isNotBlank(carId)){
            params.put("carId",carId);
        }
        params.put("carDrivingImage",ivLicensePath);
        params.put("carCode",et_carCode.getText().toString().trim());
        params.put("driverLicenceImage",ivDriverLicensePath);
        params.put("driverName",et_name.getText().toString().trim());
        params.put("driverLicence",driverLicenceNum);
        OkGo.<QueryVoLzyResponse<String>>post(BaseUrl.YT_Base + BaseUrl.userAuth)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);

                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<String>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        ConfigUtils.saveCarCode(et_carCode.getText().toString().trim());
                        ConfigUtils.saveUserStatus("2");//已认证
                        finish();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        String data = response.body().getData();
                        ConfigUtils.saveCarCode(et_carCode.getText().toString().trim());
                        ConfigUtils.saveUserStatus("2");//已认证
                        showToast(desc);
                        myInfo();
                    }
                });
    }

    public void myInfo(){
        OkGo.<QueryVoLzyResponse<UserBean>>get(BaseUrl.YT_Base+BaseUrl.myInfo)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<UserBean>>(InfoAuthActivity.this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<UserBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<UserBean>> response, String desc) {
                        UserBean data = response.body().getData();
                        if (data != null){
                            ConfigUtils.saveUserMoble(data.getUserMobile());
                            ConfigUtils.saveUserName(data.getUserName());
                            ConfigUtils.saveUserStatus(data.getUserStatus());
                            ConfigUtils.saveUserMoble(data.getUserMobile());
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (userType.equals("2")){
            startActivity(MainDriverActivity.class);
        }else {
            finish();
        }
    }

}
