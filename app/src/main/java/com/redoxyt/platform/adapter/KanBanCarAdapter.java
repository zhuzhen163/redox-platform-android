package com.redoxyt.platform.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.CarStatusLsitEntity;
import com.redoxyt.platform.bean.KanBanCarBean;


public class KanBanCarAdapter extends ListBaseAdapter<KanBanCarBean.Subs> {


    public KanBanCarAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_kanban_car;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        KanBanCarBean.Subs subs = mDataList.get(position);
        TextView tv_carCode = holder.getView(R.id.tv_carCode);
        TextView tv_status = holder.getView(R.id.tv_status);
        TextView tv_position = holder.getView(R.id.tv_position);

        tv_position.setText((position+1)+"");
        tv_carCode.setText(subs.getCarCode());
        int carState = subs.getCarState();
        if (carState == 0){
            tv_status.setText("继续等待");
            tv_status.setTextColor(Color.parseColor("#F48833"));
        }else if (carState == 2){
            tv_status.setText("请进场");
            tv_status.setTextColor(Color.parseColor("#1AC47C"));
        }

    }
}
