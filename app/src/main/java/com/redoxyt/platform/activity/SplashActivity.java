package com.redoxyt.platform.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.LoginBean;
import com.redoxyt.platform.fragment.KanBanFragment;
import com.redoxyt.platform.uitl.Base64Utils;
import com.redoxyt.platform.uitl.StringUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.lang.ref.WeakReference;

import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;
import utils.PutinDialog;
import utils.SpUtils;

/**
 * Created by zz.
 * description:
 */

public class SplashActivity extends Activity{
    private boolean isIn;

    private BuildHandle handler = new BuildHandle(this);

    private static class BuildHandle extends Handler {
        private WeakReference<SplashActivity> reference;//弱引用
        public BuildHandle(SplashActivity context) {
            reference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity activity = reference.get();
            if (StringUtils.isNotBlank(ConfigUtils.getToken())){
                activity.refreshUserToken();
            }else {
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams. FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.acitivty_splash);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
//         加载网络图片
//        Glide.with(this)
//                .load("")
//                .placeholder(R.drawable.img_transition_default)
//                .error(R.drawable.img_transition_default)
//                .into(iv_pic);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SpUtils.get(BaseUrl.IS_SHOW_PRIVATE, false) == null || !(boolean) SpUtils.get(BaseUrl.IS_SHOW_PRIVATE, false)) {
            showPrivateWindow();
        }else {
            initSplash();
        }
    }

    private void initSplash(){
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
//        CrashReport.initCrashReport(getApplicationContext(), "d35118a4f8", true);

        //x5
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean b) {
                Log.e("print","加载内核是否成功:"+b);
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
        //闪验SDK配置debug开关 （必须放在初始化之前，开启后可打印闪验SDK更加详细日志信息）
        OneKeyLoginManager.getInstance().setDebug(true);
        //闪验SDK初始化（建议放在Application的onCreate方法中执行）
        initShanyanSDK(getApplicationContext());

        handler.sendEmptyMessageDelayed(0,1500);
    }

    private void initShanyanSDK(Context context) {
        OneKeyLoginManager.getInstance().init(context,"xYf7LLRK", new InitListener() {
            @Override
            public void getInitStatus(int code, String result) {
                //闪验SDK初始化结果回调
                Log.e("VVV", "初始化： code==" + code + "   result==" + result);
            }
        });

        OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
            @Override
            public void getPhoneInfoStatus(int code, String result) {
                //预取号回调
                Log.e("VVV", "预取号： code==" + code + "   result==" + result);

            }
        });
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = null;
        int userFlag = ConfigUtils.getUserFlag();
        if (userFlag == 4){//车队
            intent = new Intent(this, MainFleetActivity.class);
        }else if(userFlag == 12 || userFlag == 8){//管理员，子管理员
            intent = new Intent(this, MainUrgentActivity.class);
        }else if(userFlag == 9){//库管员
            intent = new Intent(this, MainStorekeeperActivity.class);
        }else if (userFlag == 10){//门卫
            intent = new Intent(this, MainGuardActivity.class);
        }else {//司机
            intent = new Intent(this, MainDriverActivity.class);
        }
        startActivity(intent);
        isIn = true;
    }

    /**
     * 刷新token
     */
    public void refreshUserToken() {
        HttpParams httpParams = new HttpParams();
        HttpHeaders headers = new HttpHeaders();
        String url = BaseUrl.YT_Base + BaseUrl.carOwnerByPassword;
        httpParams.put("grant_type", "refresh_token");
        httpParams.put("refresh_token", ConfigUtils.getRefreshToken());
        headers.put("Authorization", "Basic "+ Base64Utils.encodeToString("app:app"));
        OkGo.<QueryVoLzyResponse<LoginBean>>post(url)
                .params(httpParams)
                .headers(headers)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<LoginBean>>(this, false) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<LoginBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        toMainActivity();
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<LoginBean>> response, String desc) {
                        LoginBean data = response.body().getData();
                        ConfigUtils.saveToken(data.getAccess_token());
                        ConfigUtils.saveRefreshToken(data.getRefresh_token());
                        toMainActivity();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
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
                initSplash();
            }
        });
        tv_closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crownDialog.dismiss();
                moveTaskToBack(true);
            }
        });
        crownDialog.show();
        crownDialog.setCancelable(false);
    }
}
