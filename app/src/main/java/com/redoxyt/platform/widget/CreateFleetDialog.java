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

public class CreateFleetDialog extends Dialog {

    TextView tv_cancel,tv_sure;

    private SubmitCallBack callBack;

    public interface  SubmitCallBack{
        void submit();
        void cancel();
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public CreateFleetDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_sure.setOnClickListener(v -> {
            if (callBack != null){
                dismiss();
                callBack.submit();
            }
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
            callBack.cancel();
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
