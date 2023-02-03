package com.redoxyt.platform.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.TodayFindAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import http.utils.TimeUtilJL;
import utils.ConfigUtils;

/**
 * Created by zz.
 * description:当天预约查询
 */

public class TodayFindActivity extends BaseActivity {

    @BindView(R.id.lv_list)
    LRecyclerView lv_list;
    @BindView(R.id.tv_total)
    TextView tv_total;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private TodayFindAdapter todayFindAdapter;
    private int page = 1;

    @Override
    public int setView() {
        return R.layout.acitivty_today_find;
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
        lv_list.setLayoutManager(new LinearLayoutManager(TodayFindActivity.this));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        todayFindAdapter = new TodayFindAdapter(TodayFindActivity.this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(todayFindAdapter);
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
        params.put("reserveDate", TimeUtilJL.timeStampToDate(System.currentTimeMillis()));
        params.put("storagePlatform", "1");
        params.put("sortType", "1");
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<ReservationListBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveList)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationListBean>>(TodayFindActivity.this, true) {
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
                                todayFindAdapter.setDataList(reservationList);
                            }else {
                                todayFindAdapter.addAll(reservationList);
                            }
                            requestComplete();
                            if (data.getTotal() == todayFindAdapter.getDataList().size()){
                                lv_list.setNoMore(true);
                            }
                            tv_total.setText("今日预约总数：10"+data.getTotal());
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
