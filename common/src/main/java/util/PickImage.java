package util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片选择类
 *
 * @Description:
 * @ClassName: PickImage
 * @author: zhm
 * @date: 2015年8月19日 下午9:07:42
 */
public class PickImage {

    private final static String LOG_TAG = "log";

    /**
     * @param activity
     * @param requestCode
     * @throws
     * @Title:pickImageFromPhoto
     * @Description:从相册选择图片
     * @return:void
     * @Create: 2014-3-31 下午6:07:19
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static void pickImageFromPhoto(Activity activity, int requestCode) {
        if (!getSDCardStatus()) {
            commonToast(activity, "存储卡不可用");
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            commonToast(activity, "无法打开手机相册");
        }
    }

    /**
     * @return
     * @throws
     * @Title:getSDCardStatus
     * @Description:判断sd卡是可用
     * @return:boolean
     * @Create: 2014-3-31 下午6:03:45
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static boolean getSDCardStatus() {

        String state = android.os.Environment.getExternalStorageState();
        if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
            if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     * @param resId
     * @throws
     * @Title:commonToast
     * @Description:toast提示
     * @return:void
     * @Create: 2014-3-14 下午5:18:13
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static void commonToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void commonToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param activity
     * @param filepath
     * @param requestCode
     * @throws
     * @Title:pickImageFromCamera
     * @Description:相机拍照
     * @return:void
     * @Create: 2014-3-31 下午6:07:26
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static void pickImageFromCamera(Activity activity, String filepath,
                                           int requestCode) {
        if (!getSDCardStatus()) {
            commonToast(activity, "存储卡不可用");
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(filepath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
//        Uri mImageCaptureUri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是7.0android系统
//            ContentValues contentValues = new ContentValues(1);
//            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
//            mImageCaptureUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//        } else {
//            mImageCaptureUri = Uri.fromFile(file);
//        }
//        // 下面这句指定调用相机拍照后的照片存储的路径
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
//        activity.startActivityForResult(intent, requestCode);

//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Android7.0以上URI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            Uri uri = FileProvider.getUriForFile(activity, "com.redoxyt.platform.fileprovider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException anf) {
            //        ToastUtils.showToast(activity, activity.getResources().getString(com.bdt.app.common.R.string.camera_not_prepared));
        }
    }

    /**
     * @param context
     * @param uri
     * @return
     * @throws
     * @Title:getPathFromUri
     * @Description:从媒体库中获取图片
     * @return:String
     * @Create: 2014-4-1 下午6:06:12
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static String getPathFromUri(Context context, Uri uri) {
        String img_path = null;
        if (uri.toString().contains("content")) {
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                android.support.v4.content.CursorLoader cursorLoader = new android.support.v4.content.CursorLoader(
                        context, uri, proj, null, null, null);
                Cursor actualimagecursor = cursorLoader.loadInBackground();
                int actual_image_column_index = actualimagecursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                actualimagecursor.moveToFirst();
                img_path = actualimagecursor
                        .getString(actual_image_column_index);

            } catch (Exception e) {
                Log.e(LOG_TAG, "[getPathFromPhoto]", e);
            } finally {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "[getPathFromPhoto]", e);
                    }
                }
            }
        } else {
            img_path = uri.getPath();
        }
        return img_path;

    }

    /**
     * @param context
     * @param filePath
     * @param limitSize
     * @param quality
     * @return
     * @throws
     * @Title:compressImage
     * @Description:
     * @return:String
     * @Create: 2014年11月18日 上午11:05:38
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static String compressImage(Context context, String filePath,
                                       String savePath, int limitSize, int quality) {
        if (filePath == null || !new File(filePath).exists()) {
            return filePath;
        }
        try {

            int degree = readPictureDegree(filePath);

            int scale = 1;
            Log.i("log", "limitSize:" + limitSize);
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, option);

            if (option.outWidth > limitSize || option.outHeight > limitSize) {// 1
                scale = (int) Math.pow(
                        2.0,
                        (int) Math.round(Math.log(limitSize
                                / (double) Math.max(option.outHeight,
                                option.outWidth))
                                / Math.log(0.5)));
            }
            option.inJustDecodeBounds = false;
            option.inSampleSize = scale;

            Bitmap bitmap = BitmapFactory.decodeFile(filePath, option);
            Bitmap bm = null;
            if (degree != 0) {
                bm = rotateBitmap(degree, bitmap);
            }
            if (!TextUtils.isEmpty(savePath)) {
                if (bm == null) {
                    filePath = saveBitmapToFile(bitmap, savePath, quality);
                } else {
                    filePath = saveBitmapToFile(bm, savePath, quality);
                }
            }

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (bm != null && !bm.isRecycled()) {
                bm.recycle();
            }

        } catch (Exception e) {
            Log.e("log", "", e);
        } catch (OutOfMemoryError e) {
            Log.e("log", "", e);
        }
        return filePath;
    }

    /**
     * @param angle
     * @param bitmap
     * @return
     * @throws
     * @Title:rotateBitmap
     * @Description: 旋转图片
     * @return:Bitmap
     * @Create: 2014-4-1 下午6:04:01
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }

    /**
     * @param imagePath
     * @return
     * @throws
     * @Title:readPictureDegree
     * @Description:读取图片旋转角度
     * @return:int
     * @Create: 2014-4-1 下午6:05:46
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static int readPictureDegree(String imagePath) {
        int imageDegree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(imagePath);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    imageDegree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    imageDegree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    imageDegree = 270;
                    break;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "readPictureDegree", e);
        }
        return imageDegree;
    }

    /**
     * @param filePath
     * @param bitmap
     * @param quality
     * @throws
     * @Title:saveBitmapToFile
     * @Description:保存图片到文件中
     * @return:void
     * @Create: 2014-4-1 下午6:04:14
     * @Author : zhm 邮箱：zhaomeng@baihe.com
     */
    public static String saveBitmapToFile(Bitmap bitmap, String filePath,
                                          int quality) {
        Log.i("linqiang", "这里是图片的开始");
        if (bitmap == null) {
            return null;
        }
        BufferedOutputStream bos = null;
        File file = new File(filePath);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                file.getParentFile().mkdirs();
            }
        } else {
            file.delete();
        }
        try {
            file.createNewFile();
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "[saveImageFile]", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "[saveImageFile]", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "[saveImageFile]", e);
                }
            }
        }
        Log.i("linqiang", "这里是图片的完成");
        return filePath;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void cropImage(Activity activity, Uri uri, Uri newuri,
                                 int requestCode) {
        Log.i("linqiang", "这里是图片裁剪的开始");
        try {
            //Uri uri = Uri.fromFile(new File(filePath));
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("return-data", true);

            //uritempFile为Uri类变量，实例化uritempFile
            // intent.putExtra(MediaStore.EXTRA_OUTPUT, newuri);
            // intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.getStackTraceString(e);
            Log.i("liqniang", e.toString());
            Toast.makeText(activity, "图片裁剪异常", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveCropImage(Intent picdata, String filePath) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            saveBitmapToFile(photo, filePath, 100);
        }
    }


    /**
     * 根据Uri获取文件的绝对路径，解决Android4.4以上版本Uri转换
     */
    @TargetApi(19)
    public static String getFileAbsolutePath(Activity context, Uri fileUri) {
        if (context == null || fileUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, fileUri)) {
            if (isExternalStorageDocument(fileUri)) {
                String docId = DocumentsContract.getDocumentId(fileUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(fileUri)) {
                String id = DocumentsContract.getDocumentId(fileUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(fileUri)) {
                String docId = DocumentsContract.getDocumentId(fileUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(fileUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(fileUri))
                return fileUri.getLastPathSegment();
            return getDataColumn(context, fileUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(fileUri.getScheme())) {
            return fileUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());

    }
}
