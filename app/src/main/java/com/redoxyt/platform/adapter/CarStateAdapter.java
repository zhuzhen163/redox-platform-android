package com.redoxyt.platform.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.KanBanCarBean;
import com.redoxyt.platform.bean.KanBanStateInfosBean;


public class CarStateAdapter extends ListBaseAdapter<KanBanStateInfosBean> {


    public CarStateAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_car_state;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        KanBanStateInfosBean infosBean = mDataList.get(position);
        TextView tv_carCode = holder.getView(R.id.tv_carCode);
        TextView tv_status = holder.getView(R.id.tv_status);
        TextView tv_position = holder.getView(R.id.tv_position);

        tv_position.setText((position+1)+"");
        tv_carCode.setText(infosBean.getCarCode());
        int carState = infosBean.getCarState();
        if (carState == 0){
            tv_status.setText("继续等待");
            tv_status.setTextColor(Color.parseColor("#F48833"));
        }else if (carState == 2){
            tv_status.setText("请进场");
            tv_status.setTextColor(Color.parseColor("#1AC47C"));
        }else if (carState == 3){
            tv_status.setText("作业中");
            tv_status.setTextColor(Color.parseColor("#1AC47C"));
        }else {
            tv_status.setText(infosBean.getCarInfo()!=null?infosBean.getCarInfo():"");
            tv_status.setTextColor(Color.parseColor("#999999"));
        }

    }
}
