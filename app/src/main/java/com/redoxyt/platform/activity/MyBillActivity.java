package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.MyBillAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.BillBean;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;
import widget.CommonToolbar;

/**
 * Created by zz.
 * description:我的账单
 */

public class MyBillActivity extends BaseActivity implements MyBillAdapter.OnItemClickListener{

    @BindView(R.id.ctl_bar)
    CommonToolbar ctl_bar;
    @BindView(R.id.lv_list)
    LRecyclerView lv_list;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private MyBillAdapter billAdapter;
    private int page = 1;

    @Override
    public int setView() {
        return R.layout.acitivty_my_bill;
    }

    @Override
    public void initData() {
        setListView();
        getByReserveList();

        lv_list.setOnRefreshListener(() -> {
            lv_list.setNoMore(false);
            page = 1;
            getByReserveList();
        });

        lv_list.setOnLoadMoreListener(() ->{
            getByReserveList();
         });
    }

    private void setListView() {
        lv_list.setLayoutManager(new LinearLayoutManager(MyBillActivity.this));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        billAdapter = new MyBillAdapter(MyBillActivity.this);
        billAdapter.setOnItemClickListener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(billAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(true);
        lv_list.setPullRefreshEnabled(true);
    }

    /**
     * 账单列表
     */
    public void getByReserveList() {
        HttpParams params = new HttpParams();
        params.put("driverId", ConfigUtils.getUserId());
        params.put("needPay","1");
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<ReservationListBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveList)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationListBean>>(MyBillActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ReservationListBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ReservationListBean>> response, String desc) {
                        ReservationListBean data = response.body().getData();
                        if (data != null){
                            List<ReservationBean> reservationList = data.getReservationList();
                            if (page == 1){
                                billAdapter.setDataList(reservationList);
                            }else {
                                billAdapter.addAll(reservationList);
                            }
                            requestComplete();
                            if (data.getTotal() == billAdapter.getDataList().size()){
                                lv_list.setNoMore(true);
                            }
                            page++;
                        }
                    }
                });
    }

    private void requestComplete(){
        try {
            mLRecyclerViewAdapter.notifyDataSetChanged();
            lv_list.refreshComplete(20);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(ReservationBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("warehouseName",bean.getWarehouseName());
        bundle.putString("parkPayTime",bean.getParkPayTime());
        bundle.putString("parkPayAmount",bean.getParkPayAmount()+"");
        startActivity(ParkingPaySuccessActivity.class,bundle);
    }
}
