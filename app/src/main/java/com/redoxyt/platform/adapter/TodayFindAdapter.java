package com.redoxyt.platform.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.ReservationBean;


/**
 * @author zz
 * description：
 */

public class TodayFindAdapter extends ListBaseAdapter <ReservationBean>{

    private TextView tv_reserveCode,tv_reserveStartDate,tv_carCode,tv_warehouseName,tv_driverName,
            tv_transportCode,tv_driverMobile,tv_platformName,tv_payTime,tv_payAmount;
    ImageView iv_status;

    private OnItemClickListener mOnItemClickListener;

    public TodayFindAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_today_find;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ReservationBean bean = mDataList.get(position);
        tv_reserveCode = holder.getView(R.id.tv_reserveCode);
        tv_reserveStartDate = holder.getView(R.id.tv_reserveStartDate);
        tv_carCode = holder.getView(R.id.tv_carCode);
        tv_warehouseName = holder.getView(R.id.tv_warehouseName);
        tv_driverName = holder.getView(R.id.tv_driverName);
        tv_transportCode = holder.getView(R.id.tv_transportCode);
        tv_driverMobile = holder.getView(R.id.tv_driverMobile);
        tv_platformName = holder.getView(R.id.tv_platformName);
        tv_payTime = holder.getView(R.id.tv_payTime);
        tv_payAmount = holder.getView(R.id.tv_payAmount);
        iv_status = holder.getView(R.id.iv_status);

        tv_reserveCode.setText("预约号："+bean.getReserveCode());
        tv_reserveStartDate.setText("预约时间："+bean.getReserveStartDate());
        tv_carCode.setText("车牌号："+bean.getCarCode());
        tv_warehouseName.setText("预约仓库："+bean.getWarehouseName());
        tv_driverName.setText("司机姓名："+bean.getDriverName());
        tv_transportCode.setText("运单号："+bean.getTransportCode());
        tv_driverMobile.setText("手机号："+bean.getDriverMobile());
        tv_platformName.setText("月台名称："+bean.getPlatformName());
        tv_payTime.setText("支付日期："+(bean.getPayTime()!=null?bean.getPayTime():""));
        tv_payAmount.setText("￥"+bean.getPayAmount());

        if (bean.getReserveStatus() == 4){
            iv_status.setImageResource(R.drawable.yt_ywc_bg);
        }else if (bean.getReserveStatus() == 5){
            iv_status.setImageResource(R.drawable.yt_yqx_bg);
        }else if (bean.getReserveStatus() == 6){
            iv_status.setImageResource(R.drawable.yt_ysx_bg);
        }else {
            iv_status.setImageResource(R.drawable.yt_wwc_bg);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
