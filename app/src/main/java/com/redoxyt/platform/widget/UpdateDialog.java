package com.redoxyt.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.redoxyt.platform.R;


/**
 * Created by zz.
 * description:
 */

public class UpdateDialog extends Dialog {
    private UpdateCallBack callBack;
    TextView tv_updateContent,tv_cancel,tv_sure;

    public UpdateDialog(Context context) {
        super(context, R.style.custom_dialog);
    }

    public interface  UpdateCallBack{
        void update();
    }

    public void setUpdateCallBack(UpdateCallBack callBack){
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_updateContent = findViewById(R.id.tv_updateContent);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null){
                    callBack.update();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setContent(String content,String isforce){
        this.show();
        if (!TextUtils.isEmpty(content)){
            tv_updateContent.setText(content);
        }
        if ("1".equals(isforce)){
            tv_cancel.setVisibility(View.GONE);
        }else {
            tv_cancel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
