package com.redoxyt.platform.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.ComplaintListBean;

import java.util.List;


/**
 * Created by zz.
 * description:
 */

public class ComplaintDialog extends Dialog {

    TextView tv_title,tv_content,tv_cancel,tv_sure,tv_textNum;
    EditText et_content;
    Spinner Spinner01,Spinner02;
    private Context context;

    private ArrayAdapter<String> adapter1,adapter2;
    private  String[] sp1= null;
    private  String[] sp2= null;

    private int type1 = 1,type2 = 1;

    private SubmitCallBack callBack;
    private List<ComplaintListBean> data;

    public interface  SubmitCallBack{
        void submit(int type1, int type2, String content);
    }

    public void setCallBack(SubmitCallBack callBack) {
        this.callBack = callBack;
    }

    public ComplaintDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        this.context = context;
    }

    public void setList(List<ComplaintListBean> data){
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_complaint);
        initView();
    }

    public void initView() {
        setCancelable(false);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);
        Spinner01 = findViewById(R.id.Spinner01);
        Spinner02 = findViewById(R.id.Spinner02);
        et_content = findViewById(R.id.et_content);
        tv_textNum = findViewById(R.id.tv_textNum);
        tv_sure.setOnClickListener(v -> {
            if (callBack != null){
                String content = et_content.getText().toString().trim();
                callBack.submit(type1,type2,content);
                et_content.setText("");
                dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_content.setText("");
                dismiss();
            }
        });

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 100) {
                    s.delete(100, s.length());
                }
                tv_textNum.setText((String.valueOf(s.length()))+"/100");
            }
        });

        initDate();

    }

    List<ComplaintListBean.ChildrenBean> children;

    private void initDate(){
        //将可选内容与ArrayAdapter连接起来
        if (data!= null && data.size()>0){
            sp1 = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                ComplaintListBean complaintListBean = data.get(i);
                sp1[i] = complaintListBean.getComplainClassName();
            }
            children = data.get(0).getChildren();
            sp2 = new String[children.size()];
            for (int i = 0; i < children.size(); i++) {
                sp2[i] = children.get(i).getComplainTypeName();
            }

        }
        adapter1 = new ArrayAdapter<String>(context,R.layout.simple_spinner_item,sp1);
        //设置下拉列表的风格
        adapter1.setDropDownViewResource(R.layout.item_select);
        //将adapter 添加到spinner中
        Spinner01.setAdapter(adapter1);
        Spinner01.setPopupBackgroundResource(R.drawable.corner_complaint_bg);
        Spinner01.setDropDownVerticalOffset(8);
        //添加事件Spinner事件监听
        Spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ComplaintListBean complaintListBean = data.get(i);
                children = complaintListBean.getChildren();
                sp2 = new String[children.size()];
                for (int j = 0; j < children.size(); j++) {
                    sp2[j] = children.get(j).getComplainTypeName();
                }
                catalog(children);
                type1 = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        catalog(children);

    }

    private void catalog(List<ComplaintListBean.ChildrenBean> children){
        //将可选内容与ArrayAdapter连接起来
        adapter2 = new ArrayAdapter<String>(context,R.layout.simple_spinner_item,sp2);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(R.layout.item_select);
        //将adapter 添加到spinner中
        Spinner02.setAdapter(adapter2);
        Spinner02.setPopupBackgroundResource(R.drawable.corner_complaint_bg);
        Spinner02.setDropDownVerticalOffset(8);
        //添加事件Spinner事件监听
        Spinner02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type2 = children.get(i).getComplainTypeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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
