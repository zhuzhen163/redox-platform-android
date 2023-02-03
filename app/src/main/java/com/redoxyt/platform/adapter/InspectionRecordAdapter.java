package com.redoxyt.platform.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.EntryCheckListBean;
import com.redoxyt.platform.bean.ReservationBean;


/**
 * @author zz
 * description：
 */

public class InspectionRecordAdapter extends ListBaseAdapter <EntryCheckListBean.WarehouseRule>{

    TextView tv_time,tv_carCode,tv_driver,tv_reserveCode,tv_stutas;


    public InspectionRecordAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.adapter_inspection_record;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        EntryCheckListBean.WarehouseRule bean = mDataList.get(position);
        tv_reserveCode = holder.getView(R.id.tv_reserveCode);
        tv_carCode = holder.getView(R.id.tv_carCode);
        tv_time = holder.getView(R.id.tv_time);
        tv_driver = holder.getView(R.id.tv_driver);
        tv_stutas = holder.getView(R.id.tv_stutas);

        tv_reserveCode.setText("预约码："+bean.getReserveCode());
        tv_driver.setText("司  机："+bean.getDriverName());
        tv_time.setText(bean.getCreateTime()!=null?bean.getCreateTime():"");
        tv_carCode.setText("车牌号："+bean.getCarCode());
        if (bean.getStatus() == 1){
            tv_stutas.setText("通过");
            tv_stutas.setTextColor(Color.parseColor("#19C37B"));
        }else {
            tv_stutas.setText("不通过");
            tv_stutas.setTextColor(Color.parseColor("#FF615D"));
        }

    }

}
