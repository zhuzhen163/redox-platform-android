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
 * description:
 */

public class YTDialog extends Dialog {

    TextView tv_title, tv_content, tv_cancel, tv_sure;
    View view_shu;

    private SubmitCallBack callBack;
    private CancelCallBack cancelCallBack;

    public interface SubmitCallBack {
        void submit();
    }

    public interface CancelCallBack {
        void cancel();
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public void setCallBack(SubmitCallBack callBack, CancelCallBack cancelCallBack) {
        this.callBack = callBack;
        this.cancelCallBack = cancelCallBack;
    }

    public YTDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_yt);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);
        view_shu = findViewById(R.id.view_shu);

        tv_sure.setOnClickListener(v -> {
            if (callBack != null) {
                dismiss();
                callBack.submit();
            }
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
            if (cancelCallBack != null) {
                cancel();
            }

        });
    }

    public void setContent(String title, String content, String commitText) {
        this.show();
        tv_cancel.setVisibility(View.VISIBLE);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_sure.setText(commitText);
    }

    public void setContent(String title, String content, String commitText, String concelText) {
        this.show();
        tv_cancel.setVisibility(View.VISIBLE);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_sure.setText(commitText);
        tv_cancel.setText(concelText);
    }

    public void setCancel(String title, String content, String commitText, boolean cancel) {
        this.show();
        tv_title.setText(title);
        tv_content.setText(content);
        tv_sure.setText(commitText);
        if (cancel) {
            tv_cancel.setVisibility(View.GONE);
            view_shu.setVisibility(View.GONE);
        } else {
            tv_cancel.setVisibility(View.VISIBLE);
            view_shu.setVisibility(View.VISIBLE);
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
