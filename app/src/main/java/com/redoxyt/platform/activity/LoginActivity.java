package com.redoxyt.platform.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.BuildConfig;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ImageCodeBean;
import com.redoxyt.platform.bean.LoginBean;
import com.redoxyt.platform.bean.OneLoginBean;
import com.redoxyt.platform.bean.UpdateBean;
import com.redoxyt.platform.uitl.AppUtils;
import com.redoxyt.platform.uitl.Base64Utils;
import com.redoxyt.platform.uitl.CreditPermissionUtil;
import com.redoxyt.platform.uitl.DownloadTask;
import com.redoxyt.platform.uitl.LgnoreBatteryOptimizations;
import com.redoxyt.platform.uitl.OnMultiClickUtils;
import com.redoxyt.platform.uitl.ShanYanConfigUtil;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.UpdateDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.CropImageUtils;
import util.PermissionsUtils;
import utils.ConfigUtils;
import utils.PutinDialog;
import utils.SpUtils;
import utils.TextUtil;
import utils.ToastUtil;

/**
 * Created by zz.
 * description:
 */

public class LoginActivity extends BaseActivity implements UpdateDialog.UpdateCallBack{

    @BindView(R.id.ll_login_phone)
    LinearLayout ll_login_phone;
    @BindView(R.id.ll_login_account)
    LinearLayout ll_login_account;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_get_phone_img)
    ImageView iv_get_phone_img;
    @BindView(R.id.iv_get_account_img)
    ImageView iv_get_account_img;
    @BindView(R.id.tv_sendCode)
    TextView tv_sendCode;
    @BindView(R.id.et_captcha_img)
    EditText et_captcha_img;
    @BindView(R.id.tv_check_login)
    TextView tv_check_login;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.et_captcha)
    EditText et_captcha;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_account_img)
    EditText et_account_img;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_oneLogin)
    TextView tv_oneLogin;
    @BindView(R.id.cbx_account_login_gopwd_principal)
    CheckBox cbx_account_login_gopwd_principal;


    private int loginType = 1;//登录类型 (0 密码登录)( 1验证码登)
    private ImageCodeBean imageCodeBean;
    private boolean isVerify;

    private UpdateDialog updateDialog;
    private ProgressDialog progressDialog;
    private String updateUrl = "";

    @Override
    public int setView() {
        return R.layout.acitivty_login;
    }


    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getCodeImg();
        getLoginConfig();

        if (SpUtils.get(BaseUrl.IS_SHOW_PRIVATE, false) == null || !(boolean) SpUtils.get(BaseUrl.IS_SHOW_PRIVATE, false)) {
            showPrivateWindow();
        }
        String userMoble = ConfigUtils.getUserMoble();
        et_phone.setText(userMoble);
        et_phone.setSelection(userMoble.length());

        updateDialog = new UpdateDialog(LoginActivity.this);
        updateDialog.setUpdateCallBack(this);
        //相关属性
        progressDialog =new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在下载中...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        updateApk();
    }

    @OnClick({R.id.tv_check_login,R.id.tv_login,R.id.tv_register,R.id.iv_get_phone_img,R.id.iv_get_account_img,
    R.id.tv_sendCode,R.id.tv_confirm,R.id.tv_privacy,R.id.tv_oneLogin})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_check_login:
                String checkLogin = tv_check_login.getText().toString();
                if (checkLogin.contains("手机号")){
                    loginType = 1;
                    ll_login_phone.setVisibility(View.VISIBLE);
                    ll_login_account.setVisibility(View.GONE);
                    tv_check_login.setText("账号密码登录");
                }else {
                    loginType = 0;
                    ll_login_phone.setVisibility(View.GONE);
                    ll_login_account.setVisibility(View.VISIBLE);
                    tv_check_login.setText("手机号登录");
                }
                break;
            case R.id.tv_login:
                if (loginType == 0) {
                    //密码登录
                    if (TextUtil.isEtNull(et_account,"请输入您的账号")) return;
                    if (TextUtil.isEtNull(et_account_img,"请输入图形验证码")) return;
                    if (TextUtil.isEtNull(et_password, "请输入您的密码")) return;
                    login(loginType, et_account.getText().toString().trim(), et_password.getText().toString().trim(), "");
                } else if (loginType == 1) {
                    //验证码登录
                    if (TextUtil.isEtNull(et_phone,"请输入手机号")) return;
                    if (TextUtil.isEtNull(et_captcha_img, "请输入图形验证码")) return;
                    if (TextUtil.isEtNull(et_captcha,"请输入验证码")) return;
                    login(loginType, et_phone.getText().toString().trim(), "", et_captcha.getText().toString().trim());
                }
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.iv_get_phone_img:
            case R.id.iv_get_account_img:
                getCodeImg();
                break;
            case R.id.tv_sendCode:
                if (TextUtil.isEtNull(et_phone,"请输入手机号")) return;
                String phone = et_phone.getText().toString();
                String captchaImg = et_captcha_img.getText().toString().trim();
                if (StringUtils.isBlank(captchaImg)){
                    showToast("请输入图形验证码");
                    return;
                }
                if (isVerify){
                    showToast("验证码已发送");
                    return;
                }
                if (TextUtil.isPhone(phone)){
                    getCode(phone,captchaImg);
                }
                break;
            case R.id.tv_confirm:
                Bundle bundle = new Bundle();
                bundle.putString("mUrl",BaseUrl.YT_BaseH5+"apptraderules");
                bundle.putString("mTitle","交易规则");
                startActivity(WebViewActivity.class,bundle);
                break;
            case R.id.tv_privacy:
                Bundle bun = new Bundle();
                bun.putString("mUrl",BaseUrl.PRIVATE_URL);
                bun.putString("mTitle","隐私政策");
                startActivity(WebViewActivity.class,bun);
                break;
            case R.id.tv_oneLogin:
                if (OnMultiClickUtils.isMultiClickClick(getApplicationContext())) {
                    OneKeyLoginManager.getInstance().setAuthThemeConfig(ShanYanConfigUtil.getCJSConfig(getApplicationContext()), null);
                    openLoginActivity(true);
                }
                break;
        }
    }

    private void openLoginActivity(boolean type) {
        //拉取授权页方法
        OneKeyLoginManager.getInstance().openLoginAuth(true, new OpenLoginAuthListener() {
            @Override
            public void getOpenLoginAuthStatus(int code, String result) {
                if (1000 == code) {
                    //拉起授权页成功
                    Log.e("VVV", "拉起授权页成功： _code==" + code + "   _result==" + result);
                } else {
                    if (type){
                        ToastUtil.showLongToast(LoginActivity.this,"一键登录功能暂时无法使用，请尝试验证码登录");
                    }
//                    showToast("一键登录功能暂时无法使用，请关闭wifi后使用数据重试");
                    //拉起授权页失败
                    Log.e("VVV", "拉起授权页失败： _code==" + code + "   _result==" + result);
//                    OneKeyLoginManager.getInstance().finishAuthActivity();
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int code, String result) {
                if (1011 == code) {
                    Log.e("VVV", "用户点击授权页返回： _code==" + code + "   _result==" + result);
                    return;
                } else if (1000 == code) {
                    Log.e("VVV", "用户点击登录获取token成功： _code==" + code + "   _result==" + result);

                    JSONObject jsonObject = JSONObject.parseObject(result);
                    String token = (String) jsonObject.get("token");
                    flashLogin(token);
                    //OneKeyLoginManager.getInstance().setLoadingVisibility(false);
                    //AbScreenUtils.showToast(getApplicationContext(), "用户点击登录获取token成功");
                } else {
                    if (type){
                        ToastUtil.showLongToast(LoginActivity.this,"用户信息获取失败");
                    }
                    Log.e("VVV", "用户点击登录获取token失败： _code==" + code + "   _result==" + result);
                }
            }
        });
    }

    private void flashLogin(String token) {
        HttpParams httpParams = new HttpParams();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", "Basic "+Base64Utils.encodeToString("app:app"));
        httpParams.put("token",token);
        OkGo.<QueryVoLzyResponse<LoginBean>>post(BaseUrl.YT_Base+BaseUrl.flashLogin)
                .params(httpParams)
                .headers(headers)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<LoginBean>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        if (desc.contains("需要注册")){
                            startActivity(RegisterActivity.class);
                        }else {
                            showToast(desc);
                        }
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
                        LoginBean data = response.body().getData();

                        ConfigUtils.saveToken(data.getAccess_token());
                        ConfigUtils.saveRefreshToken(data.getRefresh_token());
                        ConfigUtils.saveUserId(data.getUserId()+"");
                        ConfigUtils.saveGroupId(data.getGroupId());
                        ConfigUtils.saveUserName(data.getRealName());
                        int userFlag = data.getUserFlag();
                        ConfigUtils.saveUserFlag(userFlag);

                        if (userFlag == 4){//车队
                            startActivity(MainFleetActivity.class);
                        }else if(userFlag == 12 || userFlag == 8){//管理员，子管理员
                            startActivity(MainUrgentActivity.class);
                        }else if(userFlag == 9){//库管员
                            startActivity(MainStorekeeperActivity.class);
                        }else if (userFlag == 10){//门卫
                            startActivity(MainGuardActivity.class);
                        }else {//司机
                            startActivity(MainDriverActivity.class);
                        }
                        finish();
                    }
                });
    }

    private void updateApk() {
        OkGo.<QueryVoLzyResponse<UpdateBean>>get(BaseUrl.updateApk+BuildConfig.APPLICATION_ID)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<UpdateBean>>(LoginActivity.this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<UpdateBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<UpdateBean>> response, String desc) {
                        UpdateBean update = response.body().getData();
                        updateUrl = update.getPackage_url();
                        if (StringUtils.isNotBlank(update.getPackage_version())){
                            if (!FactoryApplication.getInstances().getVersion().equals(update.getPackage_version())){
                                try {
                                    int compare = AppUtils.compare(FactoryApplication.getInstances().getVersion(), update.getPackage_version());
                                    if (compare == 1){
                                        if ("1".equals(update.getPackage_flag())){//强制更新
                                            updateDialog.setContent(update.getPackage_desc(),update.getPackage_flag());
                                        }else if ("0".equals(update.getPackage_flag())){//推荐更新
                                            updateDialog.setContent(update.getPackage_desc(),update.getPackage_flag());
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    private void getCode(String userMobile, String captchaImg) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile", userMobile);
        if (imageCodeBean != null && imageCodeBean.getVerifyKey() != null){
            httpParams.put("verifyKey", imageCodeBean.getVerifyKey());
        }else {
            showToast("请重新获取图形验证码");
            return;
        }
        httpParams.put("inputCode", captchaImg);
        OkGo.<QueryVoLzyResponse<String>>get(BaseUrl.YT_Base+BaseUrl.getSmsCode)
                .params(httpParams)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        getCodeImg();
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<String>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        timerLogin.start();
                    }
                });
    }

    //倒计时功能
    CountDownTimer timerLogin = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            isVerify = true;
            tv_sendCode.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            isVerify = false;
            tv_sendCode.setText("发送验证码");
            timerLogin.cancel();
        }
    };

    private void login(int loginType, String username, String password, String code) {
        if (!cbx_account_login_gopwd_principal.isChecked()) {
            showToast("请先勾选用户服务协议");
            return;
        }
        String url = null;
        HttpParams httpParams = new HttpParams();
        HttpHeaders headers = new HttpHeaders();
        if (loginType == 0) {
            url = BaseUrl.YT_Base + BaseUrl.passwordLogin;
            httpParams.put("username", username);
            httpParams.put("password", password);
            httpParams.put("grant_type", "password");
            if (imageCodeBean.getVerifyKey() != null){
                httpParams.put("verifyKey", imageCodeBean.getVerifyKey());
            }else {
                showToast("请重新获取图形验证码");
                return;
            }
            httpParams.put("verifyCode", et_account_img.getText().toString().trim());
            headers.put("Authorization", "Basic "+ Base64Utils.encodeToString("app:app"));
        } else if (loginType == 1) {
            url = BaseUrl.YT_Base + BaseUrl.carOwnerByCode;
            httpParams.put("mobile",username);
            httpParams.put("smsCode", code);
            headers.put("Authorization", "Basic "+Base64Utils.encodeToString("app:app"));
        }

        OkGo.<QueryVoLzyResponse<LoginBean>>post(url)
                .params(httpParams)
                .headers(headers)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<LoginBean>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        getCodeImg();
                        et_captcha_img.setText("");
                        et_account_img.setText("");
                        et_password.setText("");
                        et_captcha.setText("");
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);

                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<LoginBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
//                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<LoginBean>> response, String desc) {
                        et_password.setText("");
                        et_captcha_img.setText("");
                        et_account_img.setText("");
                        et_captcha.setText("");
                        LoginBean data = response.body().getData();
                        ConfigUtils.saveToken(data.getAccess_token());
                        ConfigUtils.saveRefreshToken(data.getRefresh_token());
                        ConfigUtils.saveUserId(data.getUserId()+"");
                        ConfigUtils.saveGroupId(data.getGroupId());
                        ConfigUtils.saveUserName(data.getRealName());
                        int userFlag = data.getUserFlag();
                        ConfigUtils.saveUserFlag(userFlag);

                        if (userFlag == 4){//车队
                            startActivity(MainFleetActivity.class);
                        }else if(userFlag == 12 || userFlag == 8){//管理员，子管理员
                            startActivity(MainUrgentActivity.class);
                        }else if(userFlag == 9){//库管员
                            startActivity(MainStorekeeperActivity.class);
                        }else if (userFlag == 10){//门卫
                            startActivity(MainGuardActivity.class);
                        }else {//司机
                            startActivity(MainDriverActivity.class);
                        }
                        finish();
                    }
                });
    }

    private void getCodeImg() {
        HttpParams httpParams = new HttpParams();
        OkGo.<QueryVoLzyResponse<ImageCodeBean>>get(BaseUrl.YT_Base+BaseUrl.imgCode)
                .params(httpParams)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ImageCodeBean>>(this, false) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        iv_get_phone_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yt_login_refresh));
                        iv_get_account_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yt_login_refresh));
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                        iv_get_phone_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yt_login_refresh));
                        iv_get_account_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yt_login_refresh));
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ImageCodeBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ImageCodeBean>> response, String desc) {
                        imageCodeBean = response.body().getData();
                        if (imageCodeBean != null){
                            iv_get_phone_img.setImageBitmap(CropImageUtils.stringtoBitmap(imageCodeBean.getImg()));
                            iv_get_account_img.setImageBitmap(CropImageUtils.stringtoBitmap(imageCodeBean.getImg()));
                        }
                    }
                });
    }

    private void getLoginConfig() {
        HttpParams httpParams = new HttpParams();
        OkGo.<QueryVoLzyResponse<OneLoginBean>>get(BaseUrl.YT_Base+BaseUrl.getLoginConfig)
                .params(httpParams)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<OneLoginBean>>(this, false) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<OneLoginBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<OneLoginBean>> response, String desc) {
                        OneLoginBean data = response.body().getData();
                        if (data.getConfigStatus() == 1){
                            tv_oneLogin.setVisibility(View.VISIBLE);
                            OneKeyLoginManager.getInstance().setAuthThemeConfig(ShanYanConfigUtil.getCJSConfig(getApplicationContext()), null);
                            openLoginActivity(false);
                        }else {
                            tv_oneLogin.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    //白名单
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                LgnoreBatteryOptimizations.systemJudgment(this);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private boolean getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> pList = new ArrayList<>();
            pList.add(PermissionsUtils.CAMERA);
            pList.add(PermissionsUtils.ACCESS_FINE_LOCATION);
            pList.add(PermissionsUtils.READ_PHONE_STATE);
            pList.add(PermissionsUtils.SYSTEM_ALERT_WINDOW);
            pList.add(PermissionsUtils.RECORD_AUDIO);
            pList.add(PermissionsUtils.WAKE_LOCK);

            return PermissionsUtils.requestPermission(this, pList);

        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerLogin.cancel();
    }

    @Override
    public void update() {
        applyPermission();
    }

    /**
     * 申请相机权限开始ocr扫描
     */
    private void applyPermission() {
        String[] permissions = new String[]{PermissionsUtils.READ_EXTERNAL_STORAGE,PermissionsUtils.WRITE_EXTERNAL_STORAGE};

        String permissionDes = "手机存储";
        CreditPermissionUtil.applyPermission(LoginActivity.this, FactoryApplication.getInstances(), permissions, permissionDes,
                new CreditPermissionUtil.PermissionCallback() {
                    @Override
                    public void onGrant() {
                        onUpdateClick(updateUrl);
                    }
                }
        );
    }

    //升级下载按钮点击事件
    private void onUpdateClick(String updateUrl) {
        final DownloadTask downloadTask = new DownloadTask(LoginActivity.this,progressDialog);
        File existFile = downloadTask.isExistFile(updateUrl);
        if (existFile != null){
            try {
                boolean delete = existFile.delete();
                if (delete){
                    downloadTask.execute(updateUrl);
                    progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            downloadTask.cancel(true);
                        }
                    });
                }
            }catch (Exception e){
                downloadTask.execute(updateUrl);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true);
                    }
                });
            }
        }else {
            downloadTask.execute(updateUrl);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadTask.cancel(true);
                }
            });
        }
    }

    /**
     * 隐私协议弹窗
     */
    private void showPrivateWindow() {
        PutinDialog crownDialog = new PutinDialog(this, R.layout.dialog_private_app_main, 0.8, 0.6);
        View dialogView = crownDialog.getView();
        WebView privateWebView = (WebView) dialogView.findViewById(R.id.wb_private_app_main);
        privateWebView.getSettings().setDomStorageEnabled(true);
        privateWebView.getSettings().setLoadWithOverviewMode(true);
        privateWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        privateWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        privateWebView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        privateWebView.getSettings().setAppCacheEnabled(true);
        privateWebView.getSettings().setJavaScriptEnabled(true);
        privateWebView.loadUrl(BaseUrl.PRIVATE_URL);
        privateWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        Button btn = (Button) dialogView.findViewById(R.id.bt_close);
        TextView tv_closeApp = dialogView.findViewById(R.id.tv_closeApp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crownDialog.dismiss();
                //用户同意后，将sp标记置为true
                SpUtils.put(BaseUrl.IS_SHOW_PRIVATE, true);
            }
        });
        tv_closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
            }
        });
        crownDialog.show();
        crownDialog.setCancelable(false);
    }
}
