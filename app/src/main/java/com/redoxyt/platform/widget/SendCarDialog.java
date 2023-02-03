package com.redoxyt.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.uitl.StringUtils;

/**
 * Created by zz.
 * description:
 */

public class SendCarDialog extends Dialog {

    TextView tv_title,tv_cancel,tv_sure;
    EditText et_phone;

    private SubmitCallBack callBack;

    public interface  SubmitCallBack{
        void submit(String phone);
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public SendCarDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sent_car);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_title = findViewById(R.id.tv_title);
        et_phone = findViewById(R.id.et_phone);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_sure.setOnClickListener(v -> {
            if (callBack != null){
                String phone = et_phone.getText().toString().trim();
                if (StringUtils.isNotBlank(phone)){
                    dismiss();
                    callBack.submit(phone);
                }
            }
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void setContent(String phone){
        this.show();
        et_phone.setText(phone);
        et_phone.setSelection(phone.length());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
