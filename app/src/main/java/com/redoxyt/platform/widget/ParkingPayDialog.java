package com.redoxyt.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.redoxyt.platform.R;

/**
 * Created by zz.
 * description:停车费
 */

public class ParkingPayDialog extends Dialog {

    TextView tv_title,tv_content,tv_sure,tv_payNum;

    private SubmitCallBack callBack;

    public interface  SubmitCallBack{
        void submit();
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public ParkingPayDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_parking_pay);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_sure = findViewById(R.id.tv_sure);
        tv_payNum = findViewById(R.id.tv_payNum);

        tv_sure.setOnClickListener(v -> {
            if (callBack != null){
                callBack.submit();
            }
        });

    }

    public void setContent(String content,String payNum){
        this.show();
        tv_content.setText(content);
        tv_payNum.setText("￥"+payNum);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
