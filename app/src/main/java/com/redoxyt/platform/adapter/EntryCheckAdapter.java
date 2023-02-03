package com.redoxyt.platform.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.EntryCheckBean;
import com.redoxyt.platform.uitl.StringUtils;


/**
 * @author zz
 * description：
 */

public class EntryCheckAdapter extends ListBaseAdapter <EntryCheckBean.WarehouseRule>{

    TextView tv_upload,tv_ruleText;
    RadioGroup rg_select_bar;
    RadioButton tab_yes,tab_no;

    private OnClickListener onClickListener;

    public EntryCheckAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_entry_check;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        EntryCheckBean.WarehouseRule bean = mDataList.get(position);
        tv_upload = holder.getView(R.id.tv_upload);
        rg_select_bar = holder.getView(R.id.rg_select_bar);
        tab_yes = holder.getView(R.id.tab_yes);
        tab_no = holder.getView(R.id.tab_no);
        tv_ruleText = holder.getView(R.id.tv_ruleText);

        tv_ruleText.setText(bean.getRuleText());

        if (StringUtils.isNotBlank(bean.getStatus())){
            if (bean.getStatus().equals("1")){
                tab_yes.setChecked(true);
            }else{
                tab_no.setChecked(true);
            }
        }else {
            tab_yes.setChecked(false);
            tab_no.setChecked(false);
        }

        rg_select_bar.setOnCheckedChangeListener((radioGroup, id) -> {
            switch (id){
                case R.id.tab_yes:
                    bean.setStatus("1");
                    break;
                case R.id.tab_no:
                    bean.setStatus("0");
                    break;
            }
        });

        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null){
                    onClickListener.onUploadClick(position);
                }
            }
        });
    }

    public interface OnClickListener {
        void onUploadClick(int position);
    }
}
