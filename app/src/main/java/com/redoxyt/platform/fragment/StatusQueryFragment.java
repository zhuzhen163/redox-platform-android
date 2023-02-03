package com.redoxyt.platform.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.StatusQueryAdapter;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.BillBean;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;
import com.redoxyt.platform.bean.StatusQueryBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * 库管员--状态查询
 */

public class StatusQueryFragment extends BaseFragment {

    @BindView(R.id.lv_list)
    LRecyclerView mRecyclerView;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private StatusQueryAdapter queryAdapter;
    private int page = 1;

    @Override
    protected void initData() {
        setListView();
        getByReserveList();

        mRecyclerView.setOnRefreshListener(() -> {
            refresh();
        });

        mRecyclerView.setOnLoadMoreListener(() ->{
            getByReserveList();
        });
    }

    public void refresh(){
        page = 1;
        mRecyclerView.setNoMore(false);
        getByReserveList();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_status_query;
    }

    private void setListView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.refresh();
        queryAdapter = new StatusQueryAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(queryAdapter);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(true);
    }

    /**
     * 查询预约信息
     */
    public void getByReserveList() {
        HttpParams params = new HttpParams();
        params.put("warehouseGroupId", ConfigUtils.getGroupId());
        params.put("reserveStatus",3);
        params.put("workStartUserId",ConfigUtils.getUserId());
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<ReservationListBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveList)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationListBean>>(getActivity(), true) {
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
                                queryAdapter.setDataList(reservationList);
                            }else {
                                queryAdapter.addAll(reservationList);
                            }
                            requestComplete();
                            if (data.getTotal() == queryAdapter.getDataList().size()){
                                mRecyclerView.setNoMore(true);
                            }
                            page++;
                        }
                    }
                });
    }

    /**
     *
     */
    private void requestComplete(){
        try {
            mLRecyclerViewAdapter.notifyDataSetChanged();
            mRecyclerView.refreshComplete(20);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
