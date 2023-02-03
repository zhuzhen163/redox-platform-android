package com.redoxyt.platform.adapter;

import android.content.Context;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.BillBean;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.StatusQueryBean;


/**
 * @author zz
 * description：
 */

public class StatusQueryAdapter extends ListBaseAdapter <ReservationBean>{

    TextView tv_reserveCode,tv_startTime,tv_transportCode,tv_warehouseName,tv_carCode,
    tv_driverMobile;

    private OnItemClickListener mOnItemClickListener;

    public StatusQueryAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_status_query;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ReservationBean bean = mDataList.get(position);
        tv_reserveCode = holder.getView(R.id.tv_reserveCode);
        tv_startTime = holder.getView(R.id.tv_startTime);
        tv_transportCode = holder.getView(R.id.tv_transportCode);
        tv_warehouseName = holder.getView(R.id.tv_warehouseName);
        tv_carCode = holder.getView(R.id.tv_carCode);
        tv_driverMobile = holder.getView(R.id.tv_driverMobile);

        tv_reserveCode.setText("预约码："+bean.getReserveCode());
        tv_startTime.setText("开始时间："+(bean.getWorkStartDatetime()!=null?bean.getWorkStartDatetime():""));
        tv_transportCode.setText("运单号："+bean.getTransportCode());
        tv_warehouseName.setText("货主："+bean.getWarehouseName());
        tv_carCode.setText("车牌号："+bean.getCarCode());
        tv_driverMobile.setText("手机号："+bean.getDriverMobile());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
