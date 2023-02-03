package util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import http.utils.Constant;
import utils.TextUtil;
import utils.ToastUtil;
import view.GetPathFromUri;


/**
 * Created by 高大爽 on 2018/4/21.
 */
public class CropImageUtils {
    //7.0  ContentUri
    public static final String FILE_CONTENT_FILEPROVIDER = "com.bdt.app.businss_wuliu.fileProvider";
    private static CropImageUtils instance;
    public static final String APP_NAME = "test";
    //打开相机的返回码
    public static final int REQUEST_CODE_TAKE_PHOTO = 11111;
    //打开相册的返回码
    public static final int REQUEST_CODE_SELECT_PICTURE = 11112;
    //剪切图片的返回码
    public static final int REQUEST_CODE_CROP_PICTURE = 11113;
    //相机拍照默认存储路径
    public static final String PICTURE_DIR = Environment.getExternalStorageDirectory() + "/test/pictures/";
    public String DATE = "";
    //照片图片名
    private String photo_image;
    //截图图片名
    private String crop_image;

    public static CropImageUtils getInstance() {
        if (instance == null) {
            synchronized (CropImageUtils.class) {
                if (instance == null) {
                    instance = new CropImageUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 打开系统相册
     *
     * @param activity
     */
    public void openAlbum(Activity activity) {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist()) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            activity.startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
        } else {
            //     ToastUtils.showToast(activity, activity.getResources().getString(com.bdt.app.common.R.string.sdcard_no_exist));
        }
    }

    /**
     * 打开系统相机
     */
    public void takePhoto(Activity activity) {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist()) {
            photo_image = createImagePath(APP_NAME + DATE);
            File file = new File(photo_image);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Android7.0以上URI
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //通过FileProvider创建一个content类型的Uri
                Uri uri = FileProvider.getUriForFile(activity, FILE_CONTENT_FILEPROVIDER, file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            try {
                activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
            } catch (ActivityNotFoundException anf) {
                //        ToastUtils.showToast(activity, activity.getResources().getString(com.bdt.app.common.R.string.camera_not_prepared));
            }
        } else {
            //   ToastUtils.showToast(activity, activity.getResources().getString(com.bdt.app.common.R.string.sdcard_no_exist));
        }
    }

    /**
     * 调用系统剪裁功能
     */
    public void cropPicture(Activity activity, String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri;
        Uri outputUri;
        crop_image = createImagePath(APP_NAME + "_crop_" + DATE);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(activity, FILE_CONTENT_FILEPROVIDER, file);
            outputUri = Uri.fromFile(new File(crop_image));
            //TODO:outputUri不需要ContentUri,否则失败
            //outputUri = FileProvider.getUriForFile(activity, "com.solux.furniture.fileprovider", new File(crop_image));
        } else {
            imageUri = Uri.fromFile(file);
            outputUri = Uri.fromFile(new File(crop_image));
        }
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        //设置宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP_PICTURE);
    }

    /**
     * 拍照/打开相册/剪裁图片的回调
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(Activity activity, int requestCode, int resultCode
            , Intent data, OnResultListener listener) {
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                if (!TextUtils.isEmpty(photo_image)) {
                    File file = new File(photo_image);
                    if (file.isFile() && listener != null)
                        listener.takePhotoFinish(photo_image);
                }
                break;
            case REQUEST_CODE_SELECT_PICTURE:
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        String path = GetPathFromUri.getInstance().getPath(activity, uri);
                        File file = new File(path);
                        if (file.isFile() && listener != null)
                            listener.selectPictureFinish(path);
                    }
                }
                break;
            case REQUEST_CODE_CROP_PICTURE:
                if (!TextUtils.isEmpty(crop_image)) {
                    File file = new File(crop_image);
                    if (file.isFile() && listener != null)
                        listener.cropPictureFinish(crop_image);
                }
                break;
        }
    }

    /**
     * 创建图片的存储路径
     */
    public static String createImagePath(String imageName) {
        String dir = PICTURE_DIR;
        File destDir = new File(dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = null;
        if (!TextUtils.isEmpty(imageName)) {
            file = new File(dir, imageName + ".jpeg");
        }
        return file.getAbsolutePath();
    }

    /**
     * 检查SD卡是否存在
     */
    public boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public interface OnResultListener {
        //拍照回调
        void takePhotoFinish(String path);

        //选择图片回调
        void selectPictureFinish(String path);

        //截图回调
        void cropPictureFinish(String path);
    }

    //图片缩放
    public static Bitmap changeBitmapsize(Bitmap bitmap, int newWidth, int newHeight) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    /**
     * 图片上传前压缩
     * <p>
     * bitmap:原始图片
     * wantSizeKb:希望的图片尺寸
     */
    public static File compressImageBit(Bitmap bitmap, int wantSizeKb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > wantSizeKb) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
//        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), "ccbFace" + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }


    /**
     * @param bitmap     图片
     * @param wantSizeKb 大小
     * @return
     */
    public static String fileToBase64(Bitmap bitmap, int wantSizeKb) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(compressImageBit(bitmap, wantSizeKb));
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return base64;
    }

    /**

     * 将图片转换成Base64编码的字符串

     */

    public static String imageToBase64(String path){

        if(TextUtils.isEmpty(path)){

            return null;

        }

        InputStream is = null;

        byte[] data = null;

        String result = null;

        try{

            is = new FileInputStream(path);

            //创建一个字符流大小的数组。

            data = new byte[is.available()];

            //写入数组

            is.read(data);

            //用默认的编码格式进行编码

            result = Base64.encodeToString(data,Base64.DEFAULT);

        }catch (Exception e){

            e.printStackTrace();

        }finally {

            if(null !=is){

                try {

                    is.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }



        }

        return result;

    }

    /**
     * bitmap转为base64  * @param bitmap  * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {



        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 图片上传前压缩
     * <p>
     * bitmap:原始图片
     * wantSizeKb:希望的图片尺寸
     */
    public static File compressImage(String path, int wantSizeKb) {

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 800) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 20;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        File file = new File(Environment.getExternalStorageDirectory(),  "compress.png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    /**
     * 采样压缩
     */
    public static File compress(String path) {
        if (TextUtil.isBlank(path)) return null;
        File originFile = new File(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置此参数是仅仅读取图片的宽高到options中，不会将整张图片读到内存中，防止oom
        options.inJustDecodeBounds = true;
        Bitmap emptyBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);

        options.inSampleSize = calculateInSampleSize(options,800,800);
        options.inJustDecodeBounds = false;
        Bitmap resultBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(), options);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        File file = new File(Environment.getExternalStorageDirectory(), "compress.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recycleBitmap(resultBitmap);
        return file;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int heightRatio = height / reqHeight;
            int widthRatio  = width  / reqWidth;
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static boolean saveImageToGallery(Context context, Bitmap bitmap, String fileName) {
        // 保存图片至指定路径
        File appDir = new File(Constant.CCB_PATH);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片(80代表压缩20%)
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();

            //发送广播通知系统图库刷新数据
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存bitmap到本地
     *
     */
    public static void saveBitmap(Context context,Bitmap bitmap,String path) {
        File file = new File(Constant.CCB_PATH + path);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out))
            {
                out.flush();
                out.close();
            }
            //发送广播通知系统图库刷新数据
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            ToastUtil.ToastMessage(context,"","保存成功",-1);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
