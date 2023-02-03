package com.redoxyt.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.TextView;

import com.redoxyt.platform.R;

/**
 * Created by zz.
 * description:
 */

public class InfoAuthDialog extends Dialog {

    TextView tv_title,tv_content,tv_cancel,tv_sure;

    private SubmitCallBack callBack;

    public interface  SubmitCallBack{
        void submit();
        void cancel();
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public InfoAuthDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info_auth);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_sure.setOnClickListener(v -> {
            if (callBack != null){
                dismiss();
                callBack.submit();
            }
        });

        tv_cancel.setOnClickListener(v -> {
            if (callBack != null){
                callBack.cancel();
            }
        });
    }

    public void setContent(String title,String content,String commitText){
        this.show();
        tv_title.setText(title);
        tv_content.setText(content);
        tv_sure.setText(commitText);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
