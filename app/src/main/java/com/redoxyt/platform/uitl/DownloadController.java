package com.redoxyt.platform.uitl;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.redoxyt.platform.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.ToastUtil;

/**
 * Created by zz.
 * description:
 */

public class DownloadController {
    private DownloadManager mDownManager;
    private Context mCtx;
    private String substr;
    private static DownloadController instance;
    String path_apk = "";
    private List<Long> mIdList = new ArrayList<Long>();

    private DownloadController(Context context) {
        this.mCtx = context;
        mDownManager = (DownloadManager) mCtx.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static DownloadController getInstance(Context context) {
        if (instance == null) {
            instance = new DownloadController(context);
        }
        return instance;
    }

    public long startLauncherDownLoader(String url, long downId) {
        if (TextUtils.isEmpty(url)) {
            return -1;
        }
        if (mIdList.contains(downId)) {
            ToastUtil.ToastMessage(mCtx,"","正在下载...",-1);
            return downId;
        }
        ToastUtil.ToastMessage(mCtx,"","开始下载",-1);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        String appNmae = mCtx.getResources().getString(R.string.app_name);
        request.setTitle(appNmae);
        request.setDescription(appNmae + "正在下载...");
        request.setAllowedOverRoaming(true);
        // 设置文件存放目录
        substr = url.substring(url.lastIndexOf("/") + 1);
        File file = getDownloadFile();
        path_apk = file.getPath() + "/" + substr;
        if (file != null) {
            request.setDestinationUri(Uri.fromFile(new File(file.getPath() + "/" + substr)));
        } else {
            request.setDestinationInExternalFilesDir(mCtx, Environment.DIRECTORY_DOWNLOADS,substr);

        }
        long id = mDownManager.enqueue(request);
        mIdList.add(id);
        return id;
    }

    /**
     * 安装apk
     *
     * @paramid
     */
    public void installApkByFile(File file, Context mContext) {
        try {
            Intent install = new Intent(Intent.ACTION_VIEW);
            //兼容8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + mCtx.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
            //7.0系统的安装
            if (Build.VERSION.SDK_INT>=24){
                Uri apkUri = FileProvider.getUriForFile(mContext, "com.redoxyt.platform.fileprovider", file);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }
            //<24的安装
            else {
                Uri downloadFileUri = Uri.fromFile(file);
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mCtx.startActivity(install);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public File isExistFile(String url) {
        substr = url.substring(url.lastIndexOf("/") + 1);
        File file = getDownloadFile();
        if (file != null && !TextUtils.isEmpty(url)) {
            File apkFile = new File(file.getPath() + "/" + url.substring(url.lastIndexOf("/") + 1));
            if (apkFile.exists()) {
                return apkFile;
            } }
        return null;
    }
    public void installAPK(Context mContext) {
        installApkByFile(new File(path_apk),mContext);
    }

    public File getDownloadFile() {
        File files = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS);
        File[] ffs = files.listFiles();
        File file2 = null;
        if (ffs != null) {
            for (int i = 0; i < ffs.length; i++) {
                if (ffs[i].getName().equals(substr)) {
                    file2 = new File(substr);
                }
            }
        }
        if (file2 != null) {
            if (!file2.exists()) {
                file2 = files;
            }
        } else {
            file2 = files;
        }
        return file2;
    }
}
