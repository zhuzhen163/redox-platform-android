package com.redoxyt.platform.widget;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.RequiresApi;

import java.util.Locale;

/**
 * Created by zz.
 * description:
 */

public class SystemTTS extends UtteranceProgressListener implements TTS, TextToSpeech.OnUtteranceCompletedListener{
    private Context mContext;
    private static SystemTTS singleton;
    private TextToSpeech textToSpeech; // 系统语音播报类
    private boolean isSuccess = true;

    AssetManager am;
    MediaPlayer player;

    public static SystemTTS getInstance(Context context) {
        if (singleton == null) {
            synchronized (SystemTTS.class) {
                if (singleton == null) {
                    singleton = new SystemTTS(context);
                }
            }
        }
        return singleton;
    }

    private SystemTTS(Context context) {
        am=context.getAssets();
        this.mContext = context.getApplicationContext();
        textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                //系统语音初始化成功
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    textToSpeech.setSpeechRate(1.0f);
                    textToSpeech.setOnUtteranceProgressListener(SystemTTS.this);
                    textToSpeech.setOnUtteranceCompletedListener(SystemTTS.this);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        //系统不支持中文播报
                        isSuccess = false;
                    }
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playText(String playText) {
        if (!isSuccess) {
            // 系统不支持中文播报采用本地播放
            try {
                if (player == null){
                    player=new MediaPlayer();
                    AssetFileDescriptor afd = am.openFd("voice.mp3");
                    player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                    player.prepare();
                }
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (textToSpeech != null) {
            textToSpeech.speak(playText,
                    TextToSpeech.QUEUE_ADD, null, null);
        }
    }

    public void stopSpeak() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }


//    public boolean isSpeaking() {
//        if (textToSpeech.isSpeaking()) {
//            return true;
//        }
//        return false;
//    }


    //播报完成回调
    @Override
    public void onUtteranceCompleted(String utteranceId) {

    }

    @Override
    public void onStart(String utteranceId) {

    }

    @Override
    public void onDone(String utteranceId) {
    }

    @Override
    public void onError(String utteranceId) {

    }
}
