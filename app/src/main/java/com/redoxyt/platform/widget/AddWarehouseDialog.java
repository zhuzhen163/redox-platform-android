package com.redoxyt.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.uitl.ToastUtil;

/**
 * Created by zz.
 * description:添加仓库
 */

public class AddWarehouseDialog extends Dialog {

    TextView tv_warehouseName,tv_cancel,tv_sure,tv_find;
    EditText et_inputCode;

    private SubmitCallBack callBack;

    public interface  SubmitCallBack{
        void submit();
        void findWarehouse(String code);
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public AddWarehouseDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_wearhouse);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_warehouseName = findViewById(R.id.tv_warehouseName);
        tv_find = findViewById(R.id.tv_find);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);
        et_inputCode = findViewById(R.id.et_inputCode);

        tv_sure.setOnClickListener(v -> {
            if (callBack != null){
                dismiss();
                tv_warehouseName.setText("");
                et_inputCode.setText("");
                callBack.submit();
            }
        });

        tv_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputCode = et_inputCode.getText().toString().trim();
                if (StringUtils.isNotBlank(inputCode)){
                    if (callBack != null){
                        callBack.findWarehouse(inputCode);
                    }
                }else {
                    ToastUtil.showToast("请输入仓库码");
                }
            }
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
            tv_warehouseName.setText("");
            et_inputCode.setText("");
        });
    }

    public void setContent(){
        this.show();
    }

    public void setWarehouseName(String name){
        tv_warehouseName.setText(name);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
