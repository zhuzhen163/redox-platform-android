package com.redoxyt.platform.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.ReservationRecordAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;
import widget.CommonToolbar;

/**
 * Created by zz.
 * description:预约记录
 */

public class ReservationRecordActivity extends BaseActivity {

    @BindView(R.id.ctl_bar)
    CommonToolbar ctl_bar;
    @BindView(R.id.lv_list)
    LRecyclerView lv_list;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private ReservationRecordAdapter recordAdapter;
    private int page = 1;

    @Override
    public int setView() {
        return R.layout.acitivty_reservation_record;
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
        lv_list.setLayoutManager(new LinearLayoutManager(ReservationRecordActivity.this));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        recordAdapter = new ReservationRecordAdapter(ReservationRecordActivity.this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(recordAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(true);
        lv_list.setPullRefreshEnabled(true);
    }

    /**
     * 收索预约信息
     */
    public void getByReserveList() {
        HttpParams params = new HttpParams();
        int userFlag = ConfigUtils.getUserFlag();
        if (userFlag == 3){
            params.put("driverId", ConfigUtils.getUserId());
        }else if (userFlag == 4){
            params.put("fleetId", ConfigUtils.getGroupId());
        }else {
            params.put("warehouseGroupId", ConfigUtils.getGroupId());
        }
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<ReservationListBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveList)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationListBean>>(ReservationRecordActivity.this, true) {
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
                                recordAdapter.setDataList(reservationList);
                            }else {
                                recordAdapter.addAll(reservationList);
                            }
                            requestComplete();
                            if (data.getTotal() == recordAdapter.getDataList().size()){
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
}
