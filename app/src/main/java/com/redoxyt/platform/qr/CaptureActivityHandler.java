
package com.redoxyt.platform.qr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.redoxyt.platform.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Vector;


public final class CaptureActivityHandler extends Handler {

  private static final String TAG = CaptureActivityHandler.class.getSimpleName();

  private final CaptureActivity activity;
  private final DecodeThread decodeThread;
  private State state;

  private enum State {
    PREVIEW,
    SUCCESS,
    DONE
  }

  public CaptureActivityHandler(CaptureActivity activity, Vector<BarcodeFormat> decodeFormats,
                                String characterSet) {
    this.activity = activity;
    decodeThread = new DecodeThread(activity, decodeFormats, characterSet,
        new ViewfinderResultPointCallback(activity.getViewfinderView()));
    decodeThread.start();
    state = State.SUCCESS;

    // Start ourselves capturing previews and decoding.
    CameraManager.get().startPreview();
    restartPreviewAndDecode();
  }

  @Override
  public void handleMessage(Message message) {
    if(message.what== R.id.auto_focus){
      if (state == State.PREVIEW) {
        CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
      }
    }else if(message.what==R.id.restart_preview){
      Log.d(TAG, "Got restart preview message");
      restartPreviewAndDecode();
    }else if(message.what==R.id.decode_succeeded){
      Log.d(TAG, "Got decode succeeded message");
      state = State.SUCCESS;
      Bundle bundle = message.getData();
      Bitmap barcode = bundle == null ? null :
              (Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);
      activity.handleDecode((Result) message.obj, barcode);
    }else if(message.what==R.id.decode_failed){
      Log.d(TAG, "decode_failed");
      state = State.PREVIEW;
      CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
    }else if(message.what==R.id.return_scan_result){
      Log.d(TAG, "Got return scan result message");
      activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
      activity.finish();
    }else if(message.what==R.id.launch_product_query){
      Log.d(TAG, "Got product query message");
      String url = (String) message.obj;
      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
      activity.startActivity(intent);
    }
  }

  public void quitSynchronously() {
    state = State.DONE;
    CameraManager.get().stopPreview();
    Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
    quit.sendToTarget();
    try {
      decodeThread.join();
    } catch (InterruptedException e) {
    }

    removeMessages(R.id.decode_succeeded);
    removeMessages(R.id.decode_failed);
  }

  private void restartPreviewAndDecode() {
    if (state == State.SUCCESS) {
      state = State.PREVIEW;
      CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
      CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
      activity.drawViewfinder();
    }
  }

}
