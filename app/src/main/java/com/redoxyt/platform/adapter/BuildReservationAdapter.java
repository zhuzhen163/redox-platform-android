package com.redoxyt.platform.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;

import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.BuildReservationActivity;
import com.redoxyt.platform.bean.YTListBean;


/**
 * @author zz
 * description：
 */

public class BuildReservationAdapter extends ListBaseAdapter <YTListBean>{


    private OnItemClickListener mOnItemClickListener;
    Activity context;

    public BuildReservationAdapter(Activity context) {
        super(context);
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_build_reservation;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        YTListBean bean = mDataList.get(position);
        RadioButton rb_code = holder.getView(R.id.rb_code);
        rb_code.setText(bean.getPlatformName());
        if (bean.isCheck()){
            rb_code.setChecked(true);
        }else {
            rb_code.setChecked(false);
        }
        rb_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setClick(position,rb_code);
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(position,bean);
                }
            }
        });
    }

    private void setClick(int position,RadioButton rb_code){
        for (int i = 0; i < mDataList.size(); i++) {
            YTListBean bean = mDataList.get(i);
            if (i == position){
                rb_code.setChecked(true);
                bean.setCheck(true);
            }else {
                rb_code.setChecked(false);
                bean.setCheck(false);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position,YTListBean bean);
    }

}
