package com.redoxyt.platform.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.uitl.X5WebView;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import widget.CommonToolbar;

/**
 * Created by zhuzhen
 * webview加载类
 */
public class WebViewActivity extends BaseActivity {

    // 进度条
    @BindView(R.id.pb_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;

    @BindView(R.id.webView1)
    FrameLayout mViewParent;
    private X5WebView webView;

    // title
    private String mTitle;
    // 网页链接
    private String mUrl;

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public int setView() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra("mTitle");
            mUrl = getIntent().getStringExtra("mUrl");
        }
        webView = new X5WebView(WebViewActivity.this,null);

        mViewParent.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));
        ctb_title.setTitle(mTitle);
        initWebView();
    }

    private void initWebView() {

        webView.addJavascriptInterface(new JSClient(), "AndroidJs");
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.loadUrl(mUrl);
    }

    public class MyWebViewClient extends WebViewClient {


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            hindProgressBar();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            hindProgressBar();
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            hindProgressBar();
            super.onPageFinished(view, url);
        }

    }

    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            startProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (StringUtils.isNotBlank(title)){
                String regex = ".*[a-zA-Z].*";
                boolean result = title.matches(regex);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(false);
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            mProgressBar.clearAnimation();
            webView = null;
        }
        super.onDestroy();
    }


    public void hindProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void startProgress(int newProgress) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(newProgress);
        if (newProgress == 100) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
                //退出网页
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * js调用本地方法
     */
    public class JSClient {

        @JavascriptInterface
        public void assignToNewPages(String  title,String link){
        }

    }

}
