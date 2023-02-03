package com.redoxyt.platform.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.BuildConfig;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import butterknife.BindView;
import widget.CommonToolbar;


/**
 * Created by zhuzhen
 * webview加载类
 */
public class WebViewPDFActivity extends BaseActivity implements TbsReaderView.ReaderCallback{

    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;

    RelativeLayout tbsView;
    private TbsReaderView mTbsReaderView;

    // title
    private String mTitle;

    private String docUrl = "";
    private String download = Environment.getExternalStorageDirectory() +"/";
    String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";

    @Override
    public int setView() {
        return R.layout.webview_activity;
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra("mTitle");
            docUrl = getIntent().getStringExtra("mUrl");
        }
        ctb_title.setTitle(mTitle);

        tbsView = findViewById(R.id.tbsView);
        mTbsReaderView = new TbsReaderView(WebViewPDFActivity.this, WebViewPDFActivity.this);
        tbsView.addView(mTbsReaderView,new RelativeLayout.LayoutParams(-1,-1));

        docName();
    }


    private void docName() {
        int i = docUrl.lastIndexOf("/");
        String docName = docUrl.substring(i+1, docUrl.length());

        //判断是否在本地/[下载/直接打开]
//        File docFile = new File(download, docName);
//        if (docFile.exists()) {
//            //存在本地;
//            Log.d("print", "本地存在");
//            displayFile(download,  docName);
//        } else {
//        }
        OkGo.<File>get(docUrl).execute(new FileCallback(download,docName) {
            @Override
            public void onSuccess(Response<File> response) {
                Log.d("print", "下载文件成功");
                displayFile(download,docName);
            }

            @Override
            public File convertResponse(okhttp3.Response response) throws Throwable {
                return super.convertResponse(response);
            }
        });
    }

    private void displayFile(String filePath, String fileName) {
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile =new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.d("print","准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if(!mkdir){
                Log.d("print","创建/TbsReaderTemp失败！！！！！");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath+fileName);
        bundle.putString("tempPath", tbsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType(fileName), false);
        Log.d("print","查看文档---"+result);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }else{
            getPdfFileIntent(filePath,fileName);
        }
    }

    /**
     * 查看失败调用外部程序查看
     * @param path
     */
    public void getPdfFileIntent(String path ,String name){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);//Intent.ACTION_VIEW = "android.intent.action.VIEW"
            intent.addCategory(Intent.CATEGORY_DEFAULT);//Intent.CATEGORY_DEFAULT = "android.intent.category.DEFAULT"
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri;
            //7.0 兼容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(WebViewPDFActivity.this, path,new File(path+name));
                try {
                    grantUriPermission(BuildConfig.APPLICATION_ID, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 判断版本大于等于 7.0
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

            } else {
                uri = Uri.fromFile(new File(path+name));
            }
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        }catch (Exception e){
            showToast("查看失败，请前往手机文件管理查看");
            e.printStackTrace();
        }
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }

    @Override
    protected void onDestroy() {
        mTbsReaderView.onStop();
        super.onDestroy();
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
