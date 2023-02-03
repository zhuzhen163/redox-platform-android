package com.redoxyt.platform.qr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.redoxyt.platform.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.redoxyt.platform.uitl.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Vector;

import util.PermissionsUtils;
import util.PickImage;
import utils.ToastUtil;


public class CaptureActivity extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private TextView tv_check;
    private TextView tv_camera_flash;
    private boolean click = false;

    //感光
    private SensorManager sm;
    private Sensor ligthSensor;
    int num = 0;
    float all = 0;
    ImageView ivBack;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        title = getIntent().getExtras().getString("title");
        CameraManager.init(this);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        tv_check = findViewById(R.id.tv_check);
        tv_camera_flash = findViewById(R.id.tv_camera_flash);
//        viewfinderView.setText(title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //相册
                ArrayList<String> list1 = new ArrayList<>();
                list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
                list1.add("android.permission.CAMERA");
                if (PermissionsUtils.requestPermission(CaptureActivity.this, list1)) {
                    PickImage.pickImageFromPhoto(CaptureActivity.this, 100);
                } else {
                    ToastUtil.showLongToast(CaptureActivity.this,"需要照相机权限");
                }
            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        //获取SensorManager对象
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取Sensor对象
        ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

//        sm.registerListener(new MySensorListener(), ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
        tv_camera_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = !click;
                if (click){
                    tv_camera_flash.setText("关闭闪光灯");
                    CameraManager.get().turnOn();
                }else {
                    tv_camera_flash.setText("开启闪光灯");
                    CameraManager.get().turnOff();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    Uri uri = null;
                    uri = data.getData();
                    String photoPath = FileUtil.getFilePathByUri(CaptureActivity.this, uri);
                    String result= parseQRcode(photoPath);
                    if (!TextUtils.isEmpty(result)) {
                        inactivityTimer.onActivity();
                        playBeepSoundAndVibrate();
                        Intent intent = new Intent();
                        intent.putExtra("uuid", result);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        ToastUtil.showLongToast(CaptureActivity.this,"二维码识别失败");
                    }
                    break;
            }
        }
    }

    /**
     * 解析二维码图片
     * @param bitmapPath 文件路径
     * @return
     */
    public String parseQRcode(String bitmapPath){
        Bitmap bitmap = compressPicture(bitmapPath);
        String result=parseQRcode(bitmap);
        return result;
    }

    //bitmap压缩  如果不压缩的话在低配置的手机上解码很慢
    public Bitmap compressPicture(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);

        options.inSampleSize = calculateInSampleSize(options,800,800);
        options.inJustDecodeBounds = false;
        Bitmap afterCompressBm = BitmapFactory.decodeFile(imgPath, options);
//      //默认的图片格式是Bitmap.Config.ARGB_8888
        return afterCompressBm;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    public String parseQRcode(Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        QRCodeReader reader = new QRCodeReader();
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);//优化精度
        hints.put(DecodeHintType.CHARACTER_SET,"utf-8");//解码设置编码方式为：utf-8
        try {
            Result result = reader.decode(new BinaryBitmap(
                    new HybridBinarizer(new RGBLuminanceSource(width, height, pixels))), hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public class MySensorListener implements SensorEventListener {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            //获取精度
            float acc = event.accuracy;
            //获取光线强度
            float lux = event.values[0];
            num++;
            all = all + lux;
            //开灯
            if (num == 10 && (all / 10) < 50) {
                CameraManager.get().turnOn();
            }
        }
    }

    /**
     * 作用：用户是否同意打开相机权限
     *
     * @return true 同意 false 拒绝
     */
    public boolean isCameraPermission() {

        try {
            Camera.open().release();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_camera_flash.setText("开启闪光灯");
        click = false;
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        sm = null;
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public void handleDecode(final Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        Intent aintent = new Intent();
        String result = obj.toString();
        aintent.putExtra("uuid", result);
        setResult(RESULT_OK, aintent);
        finish();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    @SuppressLint("MissingPermission")
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    //跳转方法
    public static void invoke(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

}