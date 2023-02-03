package com.redoxyt.platform.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.BuildReservationActivity;
import com.redoxyt.platform.bean.ReservationTimeBean;


/**
 * @author zz
 * description：
 */

public class BuildCheckAdapter extends ListBaseAdapter <ReservationTimeBean.SubsBean>{

    TextView tv_time,tv_num;
    RadioButton rb_order;

    private OnOrderClickListener orderClickListener;
    Activity context;

    public BuildCheckAdapter(Activity context) {
        super(context);
        this.context = context;
    }

    public void setOrderClickListener(OnOrderClickListener orderClickListener) {
        this.orderClickListener = orderClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_build_check;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ReservationTimeBean.SubsBean bean = mDataList.get(position);
        tv_time = holder.getView(R.id.tv_time);
        tv_num = holder.getView(R.id.tv_num);
        rb_order = holder.getView(R.id.rb_order);

        tv_time.setText(bean.getStartEndTime());
        int classNum = bean.getClassNum();
        tv_num.setText("剩余："+classNum);

        if (classNum > 0){
            rb_order.setEnabled(true);
            rb_order.setChecked(true);
        }else {
            rb_order.setEnabled(false);
            rb_order.setChecked(false);
        }
        rb_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderClickListener != null){
                    orderClickListener.onOrderClick(bean.getPlatformTimezoneId(),bean.getStartEndTime());
                }
            }
        });
    }

    public interface OnOrderClickListener {
        void onOrderClick(int platformTimezoneId, String startEndTime);
    }

}
