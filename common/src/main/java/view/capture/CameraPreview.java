package view.capture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import utils.ToastUtil;

/**
 * Created by dong on 2018/5/23.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = "CameraPreview";
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private Activity mContext;
    private CameraListener listener;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private int displayDegree = 0;
    private long lastModirTime;
    private OnPreviewFrameListener mOnPreviewFrameListener;
    private Handler mBackgroundHandler;
    private int num;

    public CameraPreview(Activity context, OnPreviewFrameListener listener) {
        super(context);
        mContext = context;
        mCamera = Camera.open(cameraId);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.mOnPreviewFrameListener = listener;
    }


    /**
     * 拍照获取bitmap
     */
    public void captureImage() {
        try {
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (null != listener) {
                        Bitmap bitmap = rotateBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), -90);
                        listener.onCaptured(bitmap);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (null != listener) {
                listener.onCaptured(null);
            }
        }
    }

    /**
     * 预览拍照
     */
    public void startPreview() {
        mCamera.setPreviewCallback(this);
        mCamera.startPreview();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != mCamera) {
            mCamera.autoFocus(null);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            startCamera(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            startCamera(mHolder);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void startCamera(SurfaceHolder holder) throws IOException {
        mCamera.setPreviewDisplay(holder);
        setCameraDisplayOrientation(mContext, cameraId, mCamera);
        Camera.Size preSize = getCameraSize();
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size previewSize = parameters.getPreviewSize();
        int height = previewSize.height;
        int width = previewSize.width;
        Log.d("getPreviewSize", "height:" + height + ",width:" + width);
        float s = height / width;
        parameters.setPreviewSize(preSize.width * 2 / 3, preSize.height * 2 / 3);
        parameters.setPictureSize(preSize.width * 2 / 3, preSize.height * 2 / 3);
        parameters.setJpegQuality(100);
        // 不对焦，拍摄电脑上的图片都模糊
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            // 连续对焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    public Camera.Size getCameraSize() {
        if (null != mCamera) {
            Camera.Parameters parameters = mCamera.getParameters();
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            Camera.Size preSize = Util.getCloselyPreSize(true, metrics.widthPixels, metrics.heightPixels,
                    parameters.getSupportedPreviewSizes());
            return preSize;
        }
        return null;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    /**
     * Android API: Display Orientation Setting
     * Just change screen display orientation,
     * the rawFrame data never be changed.
     */
    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayDegree = (info.orientation + degrees) % 360;
            displayDegree = (360 - displayDegree) % 360;  // compensate the mirror
        } else {
            displayDegree = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(displayDegree);
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    private Bitmap rotateBitmap(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 释放资源
     */
    public synchronized void releaseCamera() {
        try {
            if (null != mCamera) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();//停止预览
                mCamera.release(); // 释放相机资源
                mCamera = null;
            }
            if (null != mHolder) {
                mHolder.removeCallback(this);
                mHolder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.d(TAG, "onPreviewFrame:" + camera);
        if (System.currentTimeMillis() - lastModirTime <= 1000 || bytes == null || bytes.length == 0) {
            return;
        }
        face(bytes, camera);
        // new FaceThread(bytes, camera).start();
        lastModirTime = System.currentTimeMillis();
    }

    private void face(byte[] bytes, Camera camera) {
        Camera.Size size = camera.getParameters().getPreviewSize();
        YuvImage image = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
        if (image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(-90);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_4444, true);
            //  Bitmap bitmapTailoring = cropBitmap(bitmap);
            FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 1);
            FaceDetector.Face[] face = new FaceDetector.Face[1];
            int faces = faceDetector.findFaces(bitmap, face);
            if (faces > 0) {
                Log.d(TAG, "onPreviewFrame检测到人脸");
//                Bitmap bitmapTailoring = cropBitmap(bitmap);
                num++;
                if (mOnPreviewFrameListener != null && num == 2) {
                    mOnPreviewFrameListener.onPreviewFrame(bitmap);
                    num = 1;
                }
            }else {
                Toast.makeText(mContext,"没有检测到面部",Toast.LENGTH_LONG).show();
            }
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    private Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        Log.d("cropBitmap:", "w:" + w + ",h:" + h);
        return Bitmap.createBitmap(bitmap, w / 6, h / 4, w * 2 / 3, h * 2 / 3);
    }

    public interface OnPreviewFrameListener {
        void onPreviewFrame(Bitmap bitmap);
    }
}