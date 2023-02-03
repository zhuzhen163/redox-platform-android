package com.redoxyt.platform.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.PayOrderActivity;
import com.redoxyt.platform.activity.SearchActivity;
import com.redoxyt.platform.adapter.ReservationQueryAdapter;
import com.redoxyt.platform.base.BaseEventMessage;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;
import com.redoxyt.platform.widget.YTDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;
import widget.CommonToolbar;

import static android.app.Activity.RESULT_OK;

/**
 * 车队--预约查询
 */

public class ReservationQueryFragment extends BaseFragment implements ReservationQueryAdapter.OnItemClickListener{

    @BindView(R.id.lv_list)
    LRecyclerView lv_list;
    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private ReservationQueryAdapter queryAdapter;
    private int page = 1;
    private int type = 1;//1预约查询,2预约付款
    private String reserveId, warehouseGroupId;
    private YTDialog ytDialog;

    @Override
    protected void initData() {
        ytDialog = new YTDialog(getActivity());
        setQueryList();
        getByReserveList();
        lv_list.setOnRefreshListener(() -> {
            page = 1;
            lv_list.setNoMore(false);
            getByReserveList();
        });

        lv_list.setOnLoadMoreListener(() ->{
            getByReserveList();
        });
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("type",type);
                startActivity(SearchActivity.class,bundle);
            }
        });
        ytDialog.setCallBack(new YTDialog.SubmitCallBack() {
            @Override
            public void submit() {
                cancelReservation();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_reservation_query;
    }


    private void setQueryList() {
        lv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        queryAdapter = new ReservationQueryAdapter(getActivity());
        queryAdapter.setOnItemClickListener(this);
        queryAdapter.setType(type);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(queryAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(true);
        lv_list.setPullRefreshEnabled(true);
    }

    /**
     *
     * @param type 1查询，2付款
     */
    public void setCheck(int type){
        if (ctb_title != null){
            this.type = type;
            if (queryAdapter != null){
                queryAdapter.setType(type);
                queryAdapter.clear();
            }
            if (type == 1){
                ctb_title.setTitle("预约查询");
            }else if (type == 2){
                ctb_title.setTitle("预约付款");
            }
            page = 1;
            getByReserveList();
        }
    }

    @Override
    public void onItemClick(String reserveId,String warehouseGroupId) {
        if (ytDialog != null && !ytDialog.isShowing()){
            this.reserveId = reserveId;
            this.warehouseGroupId = warehouseGroupId;
            if (type == 1){
                ytDialog.setContent("提示","确定取消预约吗？","确定");
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("reserveId",reserveId);
                bundle.putString("warehouseGroupId",warehouseGroupId);
                startActivity(PayOrderActivity.class,bundle);
            }
        }
    }

    /**
     * 查询预约信息
     */
    public void getByReserveList() {
        HttpParams params = new HttpParams();
        int userFlag = ConfigUtils.getUserFlag();
        if (userFlag == 4){
            params.put("fleetId", ConfigUtils.getGroupId());
        }else {
            params.put("warehouseGroupId", ConfigUtils.getGroupId());
        }
        params.put("reserveStatus",0);
        if (type == 2){
            params.put("payStatus",0);
        }
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
                        queryAdapter.clear();
                        queryAdapter.notifyDataSetChanged();
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
                                lv_list.setNoMore(true);
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
            lv_list.refreshComplete(20);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 取消预约
     */
    public void cancelReservation() {
        HttpParams params = new HttpParams();
        params.put("reserveId",reserveId);
        OkGo.<QueryVoLzyResponse<String>>get(BaseUrl.YT_Base + BaseUrl.cancelReservation)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<String>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        queryAdapter.clear();
                        getByReserveList();
                    }
                });
    }

    // 接收普通事件的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(BaseEventMessage message) {
        if (message.isRefresh()) {
            page = 1;
            getByReserveList();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 取消订阅
        EventBus.getDefault().unregister(this);
    }
}
