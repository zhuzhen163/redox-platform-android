package com.redoxyt.platform.activity;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.BuildConfig;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.UpdateBean;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.uitl.AppUtils;
import com.redoxyt.platform.uitl.CreditPermissionUtil;
import com.redoxyt.platform.uitl.DownloadTask;
import com.redoxyt.platform.uitl.LgnoreBatteryOptimizations;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.UpdateDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.PermissionsUtils;
import utils.ConfigUtils;
import utils.PutinDialog;
import utils.SpUtils;

public abstract class MainActivity extends BaseActivity{

    private Unbinder unbinder;
    private UpdateDialog updateDialog;
    // 下载文件的广播
    //关于进度显示
    private ProgressDialog progressDialog;
    private String updateUrl = "";

    public abstract int setView();

    public abstract void initData();

    @Override
    protected void setStatusBar() {
    }

    private boolean getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> pList = new ArrayList<>();
            pList.add(PermissionsUtils.READ_EXTERNAL_STORAGE);
            pList.add(PermissionsUtils.WRITE_EXTERNAL_STORAGE);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setView());
        unbinder = ButterKnife.bind(this);
        if (SpUtils.get(BaseUrl.IS_SHOW_PRIVATE, false) == null || !(boolean) SpUtils.get(BaseUrl.IS_SHOW_PRIVATE, false)) {
            showPrivateWindow();
        }
        initData();

        updateDialog = new UpdateDialog(MainActivity.this);
        updateDialog.setUpdateCallBack(new UpdateDialog.UpdateCallBack() {
            @Override
            public void update() {
                applyPermission();
            }
        });

//        getPermissions();
        //相关属性
        progressDialog =new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("正在下载中...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!isIgnoringBatteryOptimizations()){
                requestIgnoreBatteryOptimizations();
            }
        }

        myInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateApk();
    }

    public void myInfo(){
        OkGo.<QueryVoLzyResponse<UserBean>>get(BaseUrl.YT_Base+BaseUrl.myInfo)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<UserBean>>(MainActivity.this, false) {
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
                            ConfigUtils.saveUserStatus(data.getUserStatus());
                            ConfigUtils.saveUserMoble(data.getUserMobile());
                            ConfigUtils.saveUserName(data.getUserName());
                            if (data.getTmsUserExt() != null){
                                ConfigUtils.saveGroupAbbr(data.getTmsUserExt().getGroupAbbr());
                            }
                        }
                    }
                });
    }

    //升级下载按钮点击事件
    private void onUpdateClick(String updateUrl) {
        final DownloadTask downloadTask = new DownloadTask(MainActivity.this,progressDialog);
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

    private void updateApk() {
        OkGo.<QueryVoLzyResponse<UpdateBean>>get(BaseUrl.updateApk+BuildConfig.APPLICATION_ID)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<UpdateBean>>(MainActivity.this, true) {
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


    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
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

    /**
     * 申请相机权限开始ocr扫描
     */
    private void applyPermission() {
        String[] permissions = new String[]{PermissionsUtils.READ_EXTERNAL_STORAGE,PermissionsUtils.WRITE_EXTERNAL_STORAGE};

        String permissionDes = "手机存储";
        CreditPermissionUtil.applyPermission(MainActivity.this, FactoryApplication.getInstances(), permissions, permissionDes,
                new CreditPermissionUtil.PermissionCallback() {
                    @Override
                    public void onGrant() {
                        onUpdateClick(updateUrl);
                    }
                }
        );
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
