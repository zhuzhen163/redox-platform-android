package com.redoxyt.platform.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.bean.BillBean;
import com.redoxyt.platform.bean.ReservationBean;


/**
 * @author zz
 * description：
 */

public class MyBillAdapter extends ListBaseAdapter <ReservationBean>{

    private TextView tv_reserveCode,tv_reserveStartDate,tv_carCode,tv_warehouseName,tv_driverName,
            tv_transportCode,tv_driverMobile,tv_platformName,tv_payTime,tv_payAmount;
    ImageView iv_status,iv_parking_status;
    private TextView tv_parking_payAmount,tv_parking_payTime,tv_parking_warehouseName;
    LinearLayout ll_parking;

    private OnItemClickListener mOnItemClickListener;

    public MyBillAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_bill;
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

        iv_parking_status = holder.getView(R.id.iv_parking_status);
        tv_parking_payAmount = holder.getView(R.id.tv_parking_payAmount);
        tv_parking_payTime = holder.getView(R.id.tv_parking_payTime);
        tv_parking_warehouseName = holder.getView(R.id.tv_parking_warehouseName);
        ll_parking = holder.getView(R.id.ll_parking);


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

        double payAmount = bean.getParkPayAmount();
        if (payAmount>0){
            ll_parking.setVisibility(View.VISIBLE);
            tv_parking_warehouseName.setText("预约仓库："+bean.getWarehouseName());
            tv_parking_payTime.setText("支付日期"+bean.getParkPayTime());
            tv_parking_payAmount.setText("￥"+payAmount);
            int parkPayStatus = bean.getParkPayStatus();
            if (parkPayStatus == 3){
                iv_parking_status.setImageResource(R.drawable.yt_yzf_ic);
            }
            ll_parking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(bean);
                    }
                }
            });
        }else {
            ll_parking.setVisibility(View.GONE);
        }


        ReservationBean.WechatPayOrder payOrder = bean.getWechatPayRecord();
        if (payOrder != null){
            if (payOrder.getPayStatus() == 2){//支付成功
                iv_status.setImageResource(R.drawable.yt_yzf_ic);
            }else if (payOrder.getPayStatus() == 3){//退款成功
                iv_status.setImageResource(R.drawable.yt_ytk_ic);
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(ReservationBean bean);
    }

}
