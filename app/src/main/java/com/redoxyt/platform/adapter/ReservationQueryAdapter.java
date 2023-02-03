package com.redoxyt.platform.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.ReservationBean;


/**
 * @author zz
 * description：
 */

public class ReservationQueryAdapter extends ListBaseAdapter <ReservationBean>{

    private TextView tv_reserveCode,tv_reserveStartDate,tv_carCode,tv_warehouseName,tv_driverName,
            tv_transportCode,tv_driverMobile,tv_platformName,tv_payTime,tv_payAmount,tv_pay;
    private int type;

    private OnItemClickListener mOnItemClickListener;

    public ReservationQueryAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_query;
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
        tv_pay = holder.getView(R.id.tv_pay);

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
        if (type == 1){
            tv_pay.setText("取消预约");
            tv_pay.setBackgroundResource(R.drawable.yt_info_auth_bg);
            tv_pay.setTextColor(Color.parseColor("#666666"));
        }else {
            tv_pay.setText("预约付款");
            tv_pay.setBackgroundResource(R.drawable.yt_button_3);
            tv_pay.setTextColor(Color.parseColor("#F48833"));
        }
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(bean.getReserveId(),bean.getWarehouseGroupId());
                }
            }
        });

    }

    public void setType(int type){
        this.type = type;
    }

    public interface OnItemClickListener {
        void onItemClick(String reserveId,String warehouseGroupId);
    }

}
