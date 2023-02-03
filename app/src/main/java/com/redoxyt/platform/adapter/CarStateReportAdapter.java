package com.redoxyt.platform.adapter;

import android.content.Context;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.KanBanStateInfosBean;


public class CarStateReportAdapter extends ListBaseAdapter<KanBanStateInfosBean> {


    public CarStateReportAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_car_state_report;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        KanBanStateInfosBean infosBean = mDataList.get(position);
        TextView tv_carCode = holder.getView(R.id.tv_carCode);
        TextView tv_status = holder.getView(R.id.tv_status);
        TextView tv_position = holder.getView(R.id.tv_position);

        tv_position.setText((position+1)+"");
        tv_carCode.setText(infosBean.getCarCode());
        tv_status.setText(infosBean.getCarInfo());
    }
}
