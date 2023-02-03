package com.redoxyt.platform.activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.CarStateNoReportAdapter;
import com.redoxyt.platform.adapter.WarehouseCarAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.KanBanInfosBean;
import com.redoxyt.platform.bean.KanBanStateInfosBean;
import com.redoxyt.platform.bean.ManagerKanBanBean;
import com.redoxyt.platform.bean.WarehouseBean;
import com.redoxyt.platform.bean.WarehouseCarListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.DateFormatUtils;
import util.TimeUtil;
import view.CustomDatePicker;

/**
 * Created by zz.
 * description:仓库车辆状态
 */

public class WarehouseCarStateActivity extends BaseActivity implements WarehouseCarAdapter.OnItemClickListener{
    @BindView(R.id.lv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.ll_warehouse)
    LinearLayout ll_warehouse;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.dl)
    DrawerLayout dl;
    @BindView(R.id.car_list)
    RecyclerView car_list;
    @BindView(R.id.iv_workMore_finish)
    ImageView iv_workMore_finish;
    @BindView(R.id.tv_workState)
    TextView tv_workState;
    @BindView(R.id.tv_message_1)
    TextView tv_message_1;
    @BindView(R.id.tv_message_2)
    TextView tv_message_2;

    private WarehouseCarAdapter adapter;
    private String selectTime;
    private List<WarehouseBean> warehouseData = new ArrayList<>();
    private int page = 1;
    private int position;
    private String warehouseId;
    private CarStateNoReportAdapter carStateAdapter;

    @Override
    public int setView() {
        return R.layout.acitivty_warehouse_car_state;
    }

    @Override
    public void initData() {
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        String warehouseName = getIntent().getStringExtra("warehouseName");
        warehouseId = getIntent().getStringExtra("warehouseId");
        String platformId = getIntent().getStringExtra("platformId");
        position = getIntent().getIntExtra("position",0);

        tv_warehouseName.setText(warehouseName);

        selectTime = TimeUtil.getDateID();
        tv_time.setText(selectTime.substring(5,selectTime.length()));
        setListView(platformId);
        setCarListView();

        managementTrackingKanBan(warehouseId);

        dl.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                page = 1;
//                dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
        dl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dl.closeDrawer(Gravity.RIGHT);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(String platformId, int reserveStatus,String stateNum) {
        tv_workState.setText(stateNum);
        if (reserveStatus == 2||reserveStatus == 3){
            tv_message_1.setText("排队号");
            tv_message_2.setText("状态");
            tv_workState.setTextColor(Color.parseColor("#1EE3CF"));
            iv_workMore_finish.setBackgroundResource(R.drawable.yt_work_finish);
        }else if (reserveStatus == 0){
            tv_message_1.setText("序号");
            tv_message_2.setText("预计到达时间");
            tv_workState.setTextColor(Color.parseColor("#6B48FF"));
            iv_workMore_finish.setBackgroundResource(R.drawable.yt_noreport_finish);
        }else {
            tv_message_1.setText("序号");
            tv_message_2.setText("完成时间");
            tv_workState.setTextColor(Color.parseColor("#52A7F3"));
            iv_workMore_finish.setBackgroundResource(R.drawable.yt_report_finish);
        }
        managementTrackingKanBanInfo(platformId,reserveStatus);
    }

    private void showDrawerLayout() {
        if (!dl.isDrawerOpen(Gravity.RIGHT)) {
            dl.openDrawer(Gravity.RIGHT);
        } else {
            dl.closeDrawer(Gravity.RIGHT);
            page = 1;
        }
    }

    private void setListView(String platformId) {
        LinearLayoutManager manager = new LinearLayoutManager(WarehouseCarStateActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new WarehouseCarAdapter(WarehouseCarStateActivity.this);
        adapter.setPlatformId(platformId);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void setCarListView() {
        LinearLayoutManager manager = new LinearLayoutManager(WarehouseCarStateActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        car_list.setLayoutManager(manager);
        carStateAdapter = new CarStateNoReportAdapter(WarehouseCarStateActivity.this);
        car_list.setAdapter(carStateAdapter);
    }

    @OnClick({R.id.ll_warehouse})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.ll_warehouse:
                listWarehouse(false);
                break;
        }
    }


    /**
     * 查询仓库
     * @param isFirst
     */
    public void listWarehouse(boolean isFirst) {
        OkGo.<QueryVoLzyResponse<List<WarehouseBean>>>get(BaseUrl.YT_Base + BaseUrl.listWarehouse)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<WarehouseBean>>>(WarehouseCarStateActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<WarehouseBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<WarehouseBean>>> response, String desc) {
                        warehouseData = response.body().getData();
                        if (warehouseData != null && warehouseData.size()>0) {
                            typeList.clear();
                            for (int i = 0; i < warehouseData.size(); i++) {
                                WarehouseBean warehouseBean = warehouseData.get(i);
                                typeList.add(warehouseBean.getPlatformWarehouseName());
                            }
                            if (isFirst){
                                WarehouseBean warehouseBean = warehouseData.get(0);
                                tv_warehouseName.setText(warehouseBean.getPlatformWarehouseName());
                            }else {
                                initWarehousePicker();
                            }
                        }
                    }
                });
    }

    private List<String> typeList = new ArrayList<>();
    private OptionsPickerView typeOptions;

    private void initWarehousePicker() {
        if (typeOptions == null){
            typeOptions = new OptionsPickerBuilder(WarehouseCarStateActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    String value = typeList.get(options1);
                    tv_warehouseName.setText(value);
                    warehouseId = warehouseData.get(options1).getWarehouseId();
                    managementTrackingKanBan(warehouseId);
                }
            })
                    .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                        @Override
                        public void customLayout(View v) {
                            final TextView tvSubmit = v.findViewById(R.id.tv_confirm);
                            TextView ivCancel = v.findViewById(R.id.tv_cancel);
                            tvSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeOptions.returnData();
                                    typeOptions.dismiss();
                                }
                            });

                            ivCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeOptions.dismiss();
                                }
                            });
                        }
                    })
                    .isDialog(false)
                    .setOutSideCancelable(false)
                    .build();
        }

        typeOptions.setPicker(typeList);
        typeOptions.show();
    }

    public void setData(){
        managementTrackingKanBan(warehouseId);
    }


    public void managementTrackingKanBan(String warehouseId) {
        HttpParams params = new HttpParams();
        params.put("warehouseId",warehouseId);
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<ManagerKanBanBean>>get(BaseUrl.YT_Base + BaseUrl.managementTrackingKanBan)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ManagerKanBanBean>>(WarehouseCarStateActivity.this, true) {
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
                    public void onSuccess(Response<QueryVoLzyResponse<ManagerKanBanBean>> response, String desc) {
                        ManagerKanBanBean data = response.body().getData();
                        List<WarehouseCarListBean> list = data.getList();
                        if (list.size()>0){
                            if (page == 1){
                                adapter.setDataList(list);
                            }else {
                                adapter.addAll(list);
                            }
                            adapter.notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(position);
                        }
                    }
                });
    }

    public void managementTrackingKanBanInfo(String platformId,int reserveStatus) {
        HttpParams params = new HttpParams();
        params.put("reserveStatus",reserveStatus);
        params.put("platformId",platformId);
        OkGo.<QueryVoLzyResponse<KanBanInfosBean>>get(BaseUrl.YT_Base + BaseUrl.managementTrackingKanBanInfo)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<KanBanInfosBean>>(WarehouseCarStateActivity.this, true) {
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
                    public void onSuccess(Response<QueryVoLzyResponse<KanBanInfosBean>> response, String desc) {
                        KanBanInfosBean data = response.body().getData();
                        List<KanBanStateInfosBean> list = data.getList();
                        if (list.size()>0){
                            carStateAdapter.setDataList(list);
                            carStateAdapter.notifyDataSetChanged();
                            showDrawerLayout();
                        }
                    }
                });
    }

}
