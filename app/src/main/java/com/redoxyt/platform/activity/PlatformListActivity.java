package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.base.CommonAdapter;
import com.redoxyt.platform.base.ViewHolder;
import com.redoxyt.platform.bean.AiBuildResetBean;
import com.redoxyt.platform.bean.PlatFormOrderPlanBean;
import com.redoxyt.platform.bean.QiyeXinXiReportBean;
import com.redoxyt.platform.bean.ReserveListBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;

public class  PlatformListActivity extends BaseActivity {

    @BindView(R.id.rc_yt_list)
    RecyclerView rcYtList;
    @BindView(R.id.bt_goto_order)
    Button btGotoOrder;
    private List<ReserveListBean> mListData = new ArrayList<>();
    private List<ReserveListBean> mListDataSelected = new ArrayList<>();
    private CommonAdapter<ReserveListBean> mAdapter;

    @Override
    public int setView() {
        return R.layout.activity_platform_list;
    }

    @OnClick(R.id.bt_goto_order)
    public void onClick() {
        List<ReserveListBean> selectedOrderList = getSelectedOrderList();
        if (selectedOrderList == null || selectedOrderList.size() == 0) {
            showToast("请选择仓库");
            return;
        }
        goToMoreOrder(selectedOrderList);
    }

    private void goToMoreOrder(List<ReserveListBean> selectedOrderList) {
        ArrayList<Long> muchIds = new ArrayList<Long>();
        for (int i = 0; i < selectedOrderList.size(); i++) {
            muchIds.add((long) selectedOrderList.get(i).getMuchId());
        }
        PlatFormOrderPlanBean platFormOrderPlanBean = new PlatFormOrderPlanBean();
        platFormOrderPlanBean.setMuchIds(muchIds);
        Gson gson = new Gson();
        String toJson = gson.toJson(platFormOrderPlanBean);
        OkGo.<QueryVoLzyResponse<AiBuildResetBean>>post(BaseUrl.YT_Base + BaseUrl.MUCHWAREHOUSEONNEXTSTEP)
                .upJson(toJson)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<AiBuildResetBean>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<AiBuildResetBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);

                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<AiBuildResetBean>> response, String desc) {
                        AiBuildResetBean data = response.body().data;
                        if (data != null) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("AiBuildResetBean", data);
                            startActivity(AIBuildReservationActivity.class, bundle);
                        }
                    }
                });
    }

    @Override
    public void initData() {
        rcYtList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = initRecyclerViewAdapter();
        this.rcYtList.setAdapter( mAdapter);
        getMuchWarehouseReserveList();

    }

    private void getMuchWarehouseReserveList() {
        HttpParams params = new HttpParams();
        params.put("muchWarehouseState", 0);
        OkGo.<QueryVoLzyResponse<List<ReserveListBean>>>get(BaseUrl.YT_Base + BaseUrl.GETMUCHWAREHOUSERESERVELIST)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<ReserveListBean>>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<ReserveListBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<ReserveListBean>>> response, String desc) {
                        mListData.clear();
                        mListData = response.body().data;
                        mAdapter.setData(mListData);
                    }
                });


    }

    protected CommonAdapter<ReserveListBean> initRecyclerViewAdapter() {
        CommonAdapter<ReserveListBean> adapter = new CommonAdapter<ReserveListBean>(this,
                R.layout.item_yt_list, mListData) {
            @Override
            public void convert(ViewHolder itemViewHolder, ReserveListBean bean, int position) {
                itemViewHolder.setOnClickListener(R.id.bt_goto_order_platformlist, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<ReserveListBean> selectedOrderList=new ArrayList<>();
                        selectedOrderList.add(bean);
                        goToMoreOrder(selectedOrderList);
                    }
                });
                if (bean.isSelected) {
                    itemViewHolder.setImageResource(R.id.iv_yt_select, R.drawable.app_select_yt);
                } else {
                    itemViewHolder.setImageResource(R.id.iv_yt_select, R.drawable.app_unselect_yt);
                }
                itemViewHolder.setText(R.id.warehouseName, bean.getWarehouseName());
                itemViewHolder.setText(R.id.tv_queueNum, String.valueOf(bean.getQueueNum()));
                itemViewHolder.setText(R.id.tv_platformName, bean.getPlatformName());
                itemViewHolder.setText(R.id.tv_startEndTime, bean.getStartEndTime());
            }
        };
        //adapter.setEmptyView(R.layout.view_inv_smart_emptyview);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<ReserveListBean>() {
            @Override
            public void onItemClick(View view, ViewHolder holder, ReserveListBean bean, int position) {
                selectOrder(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, ReserveListBean bean) {
                return false;
            }
        });
        return adapter;
    }

    /**
     * 选中月台条目
     *
     * @param index
     */
    private void selectOrder(int index) {
        if (index > this.mListData.size() - 1) {
            return;
        }
        ReserveListBean record = mListData.get(index);
        if (record.isSelected) {
            record.isSelected = false;
        } else {
            record.isSelected = true;
        }
    }

    /**
     * 获取选中的月台条目列表
     *
     * @return
     */
    private List<ReserveListBean> getSelectedOrderList() {
        if (this.mListData.isEmpty()) {
            return null;
        }
        mListDataSelected.clear();
        for (ReserveListBean record : this.mListData) {
            if (record.isSelected) {
                mListDataSelected.add(record);
            }
        }
        return mListDataSelected;
    }

}