package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ReservationBean;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import widget.CommonToolbar;

/**
 * 司机开始结束作业
 */
public class DriverWorkActivity extends  BaseActivity{

    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;
    @BindView(R.id.tv_carCode)
    TextView tv_carCode;
    @BindView(R.id.tv_reserveCode)
    TextView tv_reserveCode;
    @BindView(R.id.tv_transportCode)
    TextView tv_transportCode;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.tv_platformId)
    TextView tv_platformId;
    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_start_complete)
    TextView tv_start_complete;

    private String reserveId;
    private int reserveStatus;
    private String reserveCode;
    private String platformId;//月台id

    @Override
    public int setView() {
        return R.layout.acitivty_driver_work;
    }

    @Override
    public void initData() {
        reserveStatus = getIntent().getIntExtra("reserveStatus",0);
        platformId = getIntent().getStringExtra("platformId");
        reserveCode = getIntent().getStringExtra("reserveCode");
        if (reserveStatus == 3){
            ctb_title.setTitle("完成确认");
        }
        getByReserveList();

        tv_start_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeJob();
            }
        });
    }

    /**
     * 查询预约信息
     * @param
     */
    public void getByReserveList() {
        HttpParams params = new HttpParams();
        params.put("reserveCode",reserveCode);
        OkGo.<QueryVoLzyResponse<ReservationBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveCode)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationBean>>(DriverWorkActivity.this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ReservationBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ReservationBean>> response, String desc) {
                        ReservationBean reservationBean = response.body().getData();
                        if (reservationBean != null){
                            tv_carCode.setText(reservationBean.getCarCode());
                            reserveId = reservationBean.getReserveId();
                            tv_reserveCode.setText("预约码："+reservationBean.getReserveCode());
                            tv_transportCode.setText("运单号："+reservationBean.getTransportCode());
                            tv_warehouseName.setText("货主："+reservationBean.getWarehouseName());
                            tv_platformId.setText("月台名称："+reservationBean.getPlatformName());
                            reserveStatus = reservationBean.getReserveStatus();
                            if (reserveStatus == 3){
                                tv_start_complete.setText("完成作业");
                                tv_startTime.setVisibility(View.VISIBLE);
                                tv_startTime.setText("开始时间："+reservationBean.getWorkStartDatetime());
                            }else if (reserveStatus == 2){
                                tv_startTime.setVisibility(View.GONE);
                                tv_start_complete.setText("开始作业");
                            }
                        }
                    }
                });
    }

    /**
     * 开始作业
     */
    public void executeJob() {
        HttpParams params = new HttpParams();
        params.put("reserveId",reserveId);
        params.put("platformId",platformId);
        OkGo.<QueryVoLzyResponse<ReservationBean>>post(BaseUrl.YT_Base + BaseUrl.executeJob)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationBean>>(DriverWorkActivity.this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ReservationBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ReservationBean>> response, String desc) {
                        ReservationBean data = response.body().getData();
                        if (data != null){
                            if (reserveStatus == 3){//完成作业返回扫码状态
                                finish();
                            }else {
                                getByReserveList();
                            }
                        }
                    }
                });
    }
}
