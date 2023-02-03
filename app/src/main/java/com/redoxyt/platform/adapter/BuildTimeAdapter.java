package com.redoxyt.platform.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.BuildReservationActivity;
import com.redoxyt.platform.bean.ReservationTimeBean;

import java.util.List;


/**
 * @author zz
 * description：
 */

public class BuildTimeAdapter extends ListBaseAdapter <ReservationTimeBean>{

    TextView tv_month,tv_week,tv_status;
    LinearLayout ll_bg;

    private OnTimeClickListener timeClickListener;
    BuildReservationActivity context;

    public BuildTimeAdapter(BuildReservationActivity context) {
        super(context);
        this.context = context;
    }

    public void setTimeClickListener(OnTimeClickListener timeClickListener) {
        this.timeClickListener = timeClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_build_time;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ReservationTimeBean bean = mDataList.get(position);
        ll_bg = holder.getView(R.id.ll_bg);
        tv_month = holder.getView(R.id.tv_month);
        tv_week = holder.getView(R.id.tv_week);
        tv_status = holder.getView(R.id.tv_status);

        tv_month.setText(bean.getMmdd());
        tv_week.setText(bean.getWeek());
        tv_status.setText(bean.getReserveStatusName());
        if (bean.getReserveStatus() == 0){
            tv_status.setTextColor(Color.parseColor("#1AC47C"));
        }else {
            tv_status.setTextColor(Color.parseColor("#FF625E"));
        }

        if (bean.isCheck()){
            ll_bg.setBackgroundColor(Color.parseColor("#F48833"));
        }else {
            ll_bg.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClick(position,ll_bg);
                if (timeClickListener != null){
                    timeClickListener.onTimeClick(bean);
                }
            }
        });

        if(position == getItemCount()-1){//已经到达列表的底部
            context.setData();
        }
    }

    private void setClick(int position,LinearLayout rb_code){
        for (int i = 0; i < mDataList.size(); i++) {
            ReservationTimeBean bean = mDataList.get(i);
            if (i == position){
                ll_bg.setBackgroundColor(Color.parseColor("#F48833"));
                bean.setCheck(true);
            }else {
                ll_bg.setBackgroundColor(Color.parseColor("#ffffff"));
                bean.setCheck(false);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnTimeClickListener {
        void onTimeClick(ReservationTimeBean bean);
    }

}
