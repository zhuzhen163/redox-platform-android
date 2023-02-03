package util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.TypedValue;



import java.io.IOException;
import java.util.Hashtable;

/**
 * @author zhangwenbin
 * @version version_code (e.g, V1.0)
 * @Copyright (c) 2015
 * @Description class 图片工具类
 * @date 15/11/22
 */
public class ImageUtil {

    /**
     * 读取图片的旋转角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //		LogHelper.print("== degreee" + e.toString());
        }
        return degree;
    }

    /*
     * 旋转图片
     *
     * @param angle
     *
     * @param bitmap
     *
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {

        if (bitmap == null) {
            return null;
        }

        // 旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    // 将方形的图片利用混合模式画成圆的
    public static Bitmap getAvator(Context context, Bitmap avator) {
        Bitmap bitmap = Bitmap.createBitmap(avator.getWidth(),
                avator.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        // 两者取其小
        float radius = Math.min(avator.getWidth() / 2, avator.getHeight() / 2);
        // 先画一个黑色的圆 混合模式中的DST
        canvas.drawCircle(avator.getWidth() / 2, avator.getHeight() / 2,
                radius, paint);
        // 设置混合模式！！
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // 画SRC 图像（原图像）
        canvas.drawBitmap(avator, 0, 0, paint);

        // 画白边
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources()
                        .getDisplayMetrics());
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(avator.getWidth() / 2, avator.getHeight() / 2, radius
                - strokeWidth / 2 - 1, paint);
        return bitmap;
    }

    // 将方形的图片利用混合模式画成圆的
    public static Bitmap getAvator(Context context, String string) {
        Bitmap avator = BitmapFactory.decodeFile(string);
        Bitmap bitmap = Bitmap.createBitmap(avator.getWidth(),
                avator.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        // 两者取其小
        float radius = Math.min(avator.getWidth() / 2, avator.getHeight() / 2);
        // 先画一个黑色的圆 混合模式中的DST
        canvas.drawCircle(avator.getWidth() / 2, avator.getHeight() / 2,
                radius, paint);
        // 设置混合模式！！
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // 画SRC 图像（原图像）
        canvas.drawBitmap(avator, 0, 0, paint);

        // 画白边

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
                        context.getResources().getDisplayMetrics());
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(avator.getWidth() / 2, avator.getHeight() / 2,
                radius - strokeWidth / 2 - 1, paint);

        return bitmap;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * @param bitmap
     * @param context
     * @return
     * @throws
     * @Description: 图片模糊处理
     * @Title:blurBitmap
     * @return:Bitmap
     * @Create: 2016年7月13日 下午2:08:40
     * @Author : zhm
     */
    @SuppressLint("NewApi")
    public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs,
                Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        // 这个参数设置模糊程度
        blurScript.setRadius(5.f);

        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        allOut.copyTo(outBitmap);
        bitmap.recycle();

        rs.destroy();

        return outBitmap;

    }

    public static Bitmap changeBitmapsize(Bitmap bitmap, int newWidth, int newHeight) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
//		int newWidth = 500;
//		int newHeight = 400;
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

    public static Bitmap changeBitmapsize(Bitmap bitmap, double newWidth, double newHeight) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
//		int newWidth = 500;
//		int newHeight = 400;
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
      //Bitmap的缩放，20171120   金亮
    public static Bitmap big(Bitmap b, float x, float y) {
        int w = b.getWidth();
        int h = b.getHeight();
        float sx = (float) x / w;//要强制转换，不转换我的在这总是死掉。
        float sy = (float) y / h;
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w,
                h, matrix, true);
        return resizeBmp;
    }

}
