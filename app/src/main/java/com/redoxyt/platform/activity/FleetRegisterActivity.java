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
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.BindMessageBean;
import com.redoxyt.platform.bean.FleetInfoBean;
import com.redoxyt.platform.bean.LoginBean;
import com.redoxyt.platform.bean.UserBean;
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
 * description:车队注册
 */

public class FleetRegisterActivity extends BaseActivity {

    @BindView(R.id.et_fleetName)
    EditText et_fleetName;
    @BindView(R.id.et_groupContacter)
    EditText et_groupContacter;
    @BindView(R.id.iv_idCard)
    InfoView iv_idCard;
    @BindView(R.id.iv_businessLicense)
    InfoView iv_businessLicense;
    @BindView(R.id.tv_fleet_register)
    TextView tv_fleet_register;
    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;

    private String ImagePath; //拍照 照片本地地址
    private int pubType;//图片上传  类型 （企业声明传10）（行驶证传4）（身份证传1）（车主声明传9）（车辆照片传8）（银行卡传2）（驾驶证传3）
    private Handler handler = new Handler();
    private UserBean userBean = new UserBean();
    private String ivDriverIdcardPath = "",businessLicensePath = "";
    private String userType = "";//1表示创建,2注册，3修改

    private boolean isIdcard = false,isLicense = false;

    @Override
    public int setView() {
        return R.layout.acitivty_fleet_register;
    }

    @Override
    public void initData() {
        UserBean userBean = (UserBean) getIntent().getSerializableExtra("userBean");
        if (userBean != null){
            this.userBean = userBean;
        }
        userType = getIntent().getStringExtra("userType");

        if (userType.equals("3")){
            getUpdateMyInfoFleet();
        }

        iv_idCard.getRlImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pubType = 1;
                showPictureDialog();
            }
        });
        iv_businessLicense.getRlImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pubType = 13;
                showPictureDialog();
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

    @OnClick({R.id.tv_fleet_register})
    public void onViewClicked(View view){
        if (TextUtil.isEtNull(et_fleetName,"请输入车队名称")) return;
        if (TextUtil.isEtNull(et_groupContacter,"请输入联系人")) return;
        if (StringUtils.isNotBlank(businessLicensePath) || StringUtils.isNotBlank(ivDriverIdcardPath)){
            if (userType.equals("3")){//修改
                updateMyInfoFleet();
            }else {
                userRegister();
            }
        }else {
            showToast("请上传营业执照或法人身份证");
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
            if (PermissionsUtils.requestPermission(FleetRegisterActivity.this, list1)) {
                ImagePath = Constant.CCB_PATH + "/" + "Image" + System.currentTimeMillis() + ".png";
                dialog.dismiss();
                if (ConfigUtils.getUsePhoto()){
                    if (pubType == 1){
                        IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_IDCARD_FRONT);
                    }else if (pubType == 13){
                        IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_BUSINESSLICENSE);
                    }
                }else {
                    PickImage.pickImageFromCamera(FleetRegisterActivity.this, ImagePath, 1000);
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
            if (PermissionsUtils.requestPermission(FleetRegisterActivity.this, list1)) {
                dialog.dismiss();
                PickImage.pickImageFromPhoto(FleetRegisterActivity.this, 100);
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
                            handler.postDelayed(() -> saveInfomation(FileUtil.getFilePathByUri(FleetRegisterActivity.this, uri), pubType),10);
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
                PickImage.pickImageFromCamera(FleetRegisterActivity.this, ImagePath, 1000);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (userType.equals("2")){
            startActivity(MainDriverActivity.class);
        }else {
            finish();
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
        OkGo.<QueryVoLzyResponse<BindMessageBean>>post(BaseUrl.YT_Base + BaseUrl.upLoadImgNoToken)
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
                            if (type == 1) {//身份证
                                isIdcard = true;
                                ivDriverIdcardPath = data.getIdcardFront();
                                userBean.setIdcardRealname(data.getIdcardRealname());
                                userBean.setIdcardCode(data.getIdcardCode());
                                userBean.setIdcardAddress(data.getIdcardAddress());
                                GlideUtils.loadImageView(FleetRegisterActivity.this, ivDriverIdcardPath, iv_idCard.getIvImg());
                                iv_idCard.setText("重新上传");
                            }else if (pubType == 13){
                                isLicense = true;
                                businessLicensePath = data.getBusinessLicenseImg();
                                userBean.setGroupCompany(data.getGroupCompany());
                                userBean.setGroupTaxCode(data.getGroupTaxCode());
                                userBean.setGroupAddress(data.getGroupAddress());
                                userBean.setGroupLegal(data.getGroupLegal());
                                userBean.setGroupCapital(data.getGroupCapital());
                                userBean.setGroupCreateDate(data.getGroupCreateDate());
                                userBean.setGroupBusinessScope(data.getGroupBusinessScope());
                                GlideUtils.loadImageView(FleetRegisterActivity.this, businessLicensePath, iv_businessLicense.getIvImg());
                                iv_businessLicense.setText("重新上传");
                            }
                        }
                    }
                });
    }

    /**
     * 回显
     */
    public void getUpdateMyInfoFleet() {
        OkGo.<QueryVoLzyResponse<FleetInfoBean>>get(BaseUrl.YT_Base + BaseUrl.getUpdateMyInfoFleet)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<FleetInfoBean>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<FleetInfoBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<FleetInfoBean>> response, String desc) {
                        FleetInfoBean infoBean = response.body().getData();
                        if (infoBean != null) {
                            FleetInfoBean.TmsGroup tmsGroup = infoBean.getTmsGroup();
                            if (tmsGroup != null){
                                et_fleetName.setText(tmsGroup.getGroupAbbr());
                                et_groupContacter.setText(tmsGroup.getGroupContacter());
                                businessLicensePath = tmsGroup.getGroupBusinessLicense();
                                if (StringUtils.isNotBlank(businessLicensePath)){
                                    isLicense = true;
                                    GlideUtils.loadImageView(FleetRegisterActivity.this, businessLicensePath, iv_businessLicense.getIvImg());
                                }

                                userBean.setGroupCompany(tmsGroup.getGroupCompany());
                                userBean.setGroupTaxCode(tmsGroup.getGroupTaxCode());
                                userBean.setGroupAddress(tmsGroup.getGroupAddress());
                                userBean.setGroupLegal(tmsGroup.getGroupLegal());
                                userBean.setGroupCapital(tmsGroup.getGroupCapital());
                                userBean.setGroupCreateDate(tmsGroup.getGroupCreateDate());
                                userBean.setGroupBusinessScope(tmsGroup.getGroupBusinessScope());
                            }

                            FleetInfoBean.TmsUserIdcard tmsUserIdcard = infoBean.getTmsUserIdcard();
                            if (tmsUserIdcard != null){
                                ivDriverIdcardPath = tmsUserIdcard.getIdcardFront();
                                if (StringUtils.isNotBlank(ivDriverIdcardPath)){
                                    isIdcard = true;
                                    GlideUtils.loadImageView(FleetRegisterActivity.this, ivDriverIdcardPath, iv_idCard.getIvImg());
                                }

                                userBean.setIdcardAddress(tmsUserIdcard.getIdcardAddress());
                                userBean.setIdcardCode(tmsUserIdcard.getIdcardCode());
                                userBean.setIdcardRealname(tmsUserIdcard.getIdcardRealname());
                            }
                        }
                    }
                });
    }

    /**
     * 修改
     */
    public void updateMyInfoFleet() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("userId", ConfigUtils.getUserId());
        httpParams.put("groupId", ConfigUtils.getGroupId());
        httpParams.put("userMobile", ConfigUtils.getUserMoble());
        httpParams.put("groupAbbr", et_fleetName.getText().toString().trim());
        httpParams.put("groupContacter", et_groupContacter.getText().toString().trim());
        if (isLicense){
            httpParams.put("groupCompany", userBean.getGroupCompany());
            httpParams.put("groupTaxCode", userBean.getGroupTaxCode());
            httpParams.put("groupAddress", userBean.getGroupAddress());
            httpParams.put("groupLegal", userBean.getGroupLegal());
            httpParams.put("groupCapital", userBean.getGroupCapital());
            httpParams.put("groupCreateDate", userBean.getGroupCreateDate());
            httpParams.put("groupBusinessScope", userBean.getGroupBusinessScope());
            httpParams.put("groupBusinessLicense", businessLicensePath);
        }

        if (isIdcard){
            httpParams.put("idcardRealname", userBean.getIdcardRealname());
            httpParams.put("idcardCode", userBean.getIdcardCode());
            httpParams.put("idcardAddress", userBean.getIdcardAddress());
            httpParams.put("idcardFront", ivDriverIdcardPath);
        }
        OkGo.<QueryVoLzyResponse<String>>post(BaseUrl.YT_Base + BaseUrl.updateMyInfoFleet)
                .params(httpParams)
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
                        startActivity(MainFleetActivity.class);
                        finish();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        startActivity(MainFleetActivity.class);
                        finish();
                    }
                });
    }

    private void userRegister(){
        HttpParams httpParams = new HttpParams();
        if (userType.equals("2")){//注册
            httpParams.put("userId", userBean.getUserId());
            httpParams.put("userMobile", userBean.getUserMobile());
        }else {//创建
            httpParams.put("userId", ConfigUtils.getUserId());
            httpParams.put("userMobile", ConfigUtils.getUserMoble());
        }

        httpParams.put("groupAbbr", et_fleetName.getText().toString().trim());
        httpParams.put("groupContacter", et_groupContacter.getText().toString().trim());
        if (isLicense){
            httpParams.put("groupCompany", userBean.getGroupCompany());
            httpParams.put("groupTaxCode", userBean.getGroupTaxCode());
            httpParams.put("groupAddress", userBean.getGroupAddress());
            httpParams.put("groupLegal", userBean.getGroupLegal());
            httpParams.put("groupCapital", userBean.getGroupCapital());
            httpParams.put("groupCreateDate", userBean.getGroupCreateDate());
            httpParams.put("groupBusinessScope", userBean.getGroupBusinessScope());
            httpParams.put("groupBusinessLicense", businessLicensePath);
        }

        if (isIdcard){
            httpParams.put("idcardRealname", userBean.getIdcardRealname());
            httpParams.put("idcardCode", userBean.getIdcardCode());
            httpParams.put("idcardAddress", userBean.getIdcardAddress());
            httpParams.put("idcardFront", ivDriverIdcardPath);
        }

        OkGo.<QueryVoLzyResponse<LoginBean>>post(BaseUrl.YT_Base+BaseUrl.fleetRegister)
                .params(httpParams)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<LoginBean>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<LoginBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<LoginBean>> response, String desc) {
                        showToast(desc);
                        LoginBean data = response.body().getData();
                        ConfigUtils.saveToken(data.getAccess_token());
                        ConfigUtils.saveRefreshToken(data.getRefresh_token());
                        ConfigUtils.saveUserId(data.getUserId()+"");
                        ConfigUtils.saveGroupId(data.getGroupId());
                        ConfigUtils.saveUserName(data.getRealName());
                        ConfigUtils.saveUserMoble(data.getUserMobile());
                        int userFlag = data.getUserFlag();
                        ConfigUtils.saveUserFlag(userFlag);
                        startActivity(MainFleetActivity.class);
                        finish();
                    }
                });
    }
}
