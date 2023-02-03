package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.AdminQueryPaymentAdapter;
import com.redoxyt.platform.adapter.ReservationRecordAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.base.BaseEventMessage;
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

/**
 * Created by zz.
 * description:紧急预约查询与付款
 */

public class AdminQueryPaymentActivity extends BaseActivity implements AdminQueryPaymentAdapter.OnItemClickListener{

    @BindView(R.id.lv_list)
    LRecyclerView lv_list;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private AdminQueryPaymentAdapter recordAdapter;
    private int page = 1;
    private YTDialog ytDialog;
    private String reserveId;

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    private String searchStr = "";

    @Override
    public int setView() {
        return R.layout.acitivty_admint_query_payment;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        ytDialog = new YTDialog(AdminQueryPaymentActivity.this);
        setListView();
        getByReserveList("");

        lv_list.setOnRefreshListener(() -> {
            lv_list.setNoMore(false);
            page = 1;
            getByReserveList(searchStr);
        });

        lv_list.setOnLoadMoreListener(() ->{
            getByReserveList(searchStr);
         });

        ytDialog.setCallBack(new YTDialog.SubmitCallBack() {
            @Override
            public void submit() {
                cancelReservation();
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = 1;
                searchStr = et_search.getText().toString();
                getByReserveList(searchStr);
            }
        });
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                String input=et_search.getText().toString().trim();
                page = 1;
                searchStr = input;
                getByReserveList(input);
                return true;
            }
            return false;
        });
    }

    private void setListView() {
        lv_list.setLayoutManager(new LinearLayoutManager(AdminQueryPaymentActivity.this));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        recordAdapter = new AdminQueryPaymentAdapter(AdminQueryPaymentActivity.this);
        recordAdapter.setOnItemClickListener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(recordAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(true);
        lv_list.setPullRefreshEnabled(true);
    }

    /**
     * 收索预约信息
     */
    public void getByReserveList(String searchText) {
        HttpParams params = new HttpParams();
        params.put("createUserId",ConfigUtils.getUserId());
        params.put("orderType",2);
        params.put("page",page);
        params.put("rows",20);
        params.put("searchText",searchText);
        OkGo.<QueryVoLzyResponse<ReservationListBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveList)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationListBean>>(AdminQueryPaymentActivity.this, true) {
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

    /**
     * 取消预约
     */
    public void cancelReservation() {
        HttpParams params = new HttpParams();
        params.put("reserveId",reserveId);
        OkGo.<QueryVoLzyResponse<String>>get(BaseUrl.YT_Base + BaseUrl.cancelReservation)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(AdminQueryPaymentActivity.this, true) {
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
                        getByReserveList("");
                        page = 1;
                    }
                });
    }

    @Override
    public void onItemPayment(String reserveId, String warehouseGroupId) {
        Bundle bundle = new Bundle();
        bundle.putString("reserveId",reserveId);
        bundle.putString("warehouseGroupId",warehouseGroupId);
        startActivity(PayOrderActivity.class,bundle);
    }

    @Override
    public void onItemCancelPay(String reserveId) {
        this.reserveId = reserveId;
        if (ytDialog != null && !ytDialog.isShowing()){
            ytDialog.setContent("提示","确定取消预约吗？","确定");
        }
    }

    // 接收普通事件的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(BaseEventMessage message) {
        if (message.isRefresh()) {
            page = 1;
            getByReserveList("");
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
