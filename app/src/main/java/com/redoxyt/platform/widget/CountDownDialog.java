package com.redoxyt.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.TextView;

import com.redoxyt.platform.R;

import util.TimeUtil;

/**
 * Created by zz.
 * description:倒计时
 */

public class CountDownDialog extends Dialog {

    TextView tv_count_down_cancel,tv_count_down_sure,tv_count_down;

    private SubmitCallBack callBack;

    public interface  SubmitCallBack{
        void countDownSubmit();
        void countDownCancel();
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public CountDownDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_count_down);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_count_down_sure = findViewById(R.id.tv_count_down_sure);
        tv_count_down_cancel = findViewById(R.id.tv_count_down_cancel);
        tv_count_down = findViewById(R.id.tv_count_down);

        tv_count_down_sure.setOnClickListener(v -> {
            if (callBack != null){
                dismiss();
                callBack.countDownSubmit();
            }
        });

        tv_count_down_cancel.setOnClickListener(v -> {
            dismiss();
            callBack.countDownCancel();
        });
    }

    public void setTv_count_down(long time){
        if (this.isShowing())
        tv_count_down.setText(TimeUtil.secondFormat(time));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
