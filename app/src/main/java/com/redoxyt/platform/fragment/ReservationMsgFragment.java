package com.redoxyt.platform.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.AdminReservationListActivity;
import com.redoxyt.platform.activity.WarehouseCarStateActivity;
import com.redoxyt.platform.adapter.KanBanAdapter;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.KanBanCarBean;
import com.redoxyt.platform.bean.KanBanCarListBean;
import com.redoxyt.platform.bean.PlatformRuleBean;
import com.redoxyt.platform.bean.ReservationIndexBean;
import com.redoxyt.platform.bean.ReserveTypeBean;
import com.redoxyt.platform.bean.WarehouseBean;
import com.redoxyt.platform.bean.YTListBean;
import com.redoxyt.platform.widget.PanelRoseChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.DateFormatUtils;
import util.StatusBarUtil;
import util.TimeUtil;
import view.CustomDatePicker;

/**
 * 紧急预约-预约信息
 */
public class ReservationMsgFragment extends BaseFragment implements KanBanAdapter.OnItemClickListener{

    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_inStorageNumber)
    TextView tv_inStorageNumber;
    @BindView(R.id.tv_outStorageNumber)
    TextView tv_outStorageNumber;
    @BindView(R.id.roseChart)
    PanelRoseChart roseChart;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.ll_warehouse)
    LinearLayout ll_warehouse;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.tv_detailed)
    TextView tv_detailed;
    @BindView(R.id.ll_null)
    LinearLayout ll_null;
    @BindView(R.id.lv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_inFinishedNumber)
    TextView tv_inFinishedNumber;
    @BindView(R.id.tv_inUnfinishedNumber)
    TextView tv_inUnfinishedNumber;
    @BindView(R.id.pb_in)
    ProgressBar pb_in;
    @BindView(R.id.tv_outFinishedNumber)
    TextView tv_outFinishedNumber;
    @BindView(R.id.tv_outUnfinishedNumber)
    TextView tv_outUnfinishedNumber;
    @BindView(R.id.pb_out)
    ProgressBar pb_out;
    @BindView(R.id.tv_totalRate)
    TextView tv_totalRate;
    private String selectTime;
    private String warehouseId;
    private String warehouseName;
    private String platformWarehouseCode;

    ArrayList<String> tabList = new ArrayList<>();
    List<YTListBean> list = new ArrayList<>();

    List<Integer> reservationList = new ArrayList<>();//已预约车数
    List<String> timeList = new ArrayList<>();//时间段

    private List<WarehouseBean> warehouseData = new ArrayList<>();
    private KanBanAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void initData() {
        selectTime = TimeUtil.getDateID();
        tv_time.setText(selectTime.substring(5,selectTime.length()));
        setListView();
        listWarehouse(true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                YTListBean ytListBean = list.get(tab.getPosition());
                String warehouseId = ytListBean.getWarehouseId();
                String platformId = ytListBean.getPlatformId();
                reservationIndex(selectTime,platformId,warehouseId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onItemClick(KanBanCarBean bean,int position) {
        Bundle bundle = new Bundle();
        bundle.putString("warehouseId",warehouseId);
        bundle.putString("warehouseName",warehouseName);
        bundle.putString("platformId",bean.getPlatformId());
        bundle.putInt("position",position);
        startActivity(WarehouseCarStateActivity.class,bundle);
    }

    @OnClick({R.id.tv_time,R.id.ll_warehouse,R.id.tv_detailed})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_time:
                initTimerPicker();
                break;
            case R.id.ll_warehouse:
                listWarehouse(false);
                break;
            case R.id.tv_detailed:
                startActivity(AdminReservationListActivity.class);
                break;
        }
    }

    private void setListView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new KanBanAdapter(getActivity());
        adapter.setmOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void setStatusBar() {
       StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 100, null);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_reservation_msg;
    }

    //仓库出入库预约统计
    public void countByReserveType(String warehouseId,String reserveDate) {
        HttpParams params = new HttpParams();
        params.put("warehouseId",warehouseId);
        params.put("reserveDate",reserveDate);
        OkGo.<QueryVoLzyResponse<ReserveTypeBean>>get(BaseUrl.YT_Base + BaseUrl.countByReserveType)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReserveTypeBean>>(getActivity(), false) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ReserveTypeBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ReserveTypeBean>> response, String desc) {
                        ReserveTypeBean data = response.body().getData();
                        if (data != null) {
                            tv_total.setText(data.getTotal());
                            int inStorageNumber = (int) data.getInStorageNumber();
                            tv_inStorageNumber.setText(inStorageNumber+"");
                            int outStorageNumber = (int) data.getOutStorageNumber();
                            tv_outStorageNumber.setText(outStorageNumber+"");
                            tv_inFinishedNumber.setText("入库 "+data.getInFinishedNumber());
                            tv_inUnfinishedNumber.setText(data.getInUnfinishedNumber());
                            tv_totalRate.setText(data.getTotalRate());
                            if (data.getInStorageNumber() == 0 || data.getInStorageNumber() == 0) {
                                pb_in.setProgress(0);
                            }else {
                                float v = data.getInFinishedNumber() / data.getInStorageNumber();
                                int p = (int) (v*100);
                                pb_in.setProgress(p);
                            }
                            tv_outFinishedNumber.setText("出库 "+data.getOutFinishedNumber());
                            tv_outUnfinishedNumber.setText(data.getOutUnfinishedNumber());
                            if (data.getOutStorageNumber() == 0 || data.getOutStorageNumber() == 0) {
                                pb_out.setProgress(0);
                            }else {
                                float v = data.getOutFinishedNumber() / data.getOutStorageNumber();
                                int p = (int) (v*100);
                                pb_out.setProgress(p);
                            }
                        }
                    }
                });
    }

    //月台列表
    public void listByWarehouseCode(String warehouseCode) {
        HttpParams params = new HttpParams();
        params.put("warehouseCode",warehouseCode);
        OkGo.<QueryVoLzyResponse<List<YTListBean>>>get(BaseUrl.YT_Base + BaseUrl.litsByWarehouseCode)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<YTListBean>>>(getActivity(), true) {
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
                    public void onSuccess(Response<QueryVoLzyResponse<List<YTListBean>>> response, String desc) {
                        List<YTListBean> data = response.body().getData();
                        if (data != null && data.size() > 0) {
                            ll_null.setVisibility(View.VISIBLE);
                            list.clear();
                            tabList.clear();
                            tabLayout.removeAllTabs();

                            list = data;
                            if (list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    YTListBean bean = list.get(i);
                                    tabList.add(bean.getPlatformName());
                                }

                                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                for (int i = 0; i < tabList.size(); i++) {
                                    tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
                                }
                            } else {
                                showToast("未查询到月台信息");
                            }
                        }else {
                            ll_null.setVisibility(View.GONE);
                        }
                    }
                });
    }

    //月台时间段饼图
    public void reservationIndex(String reserveDate,String platformId,String warehouseId) {
        HttpParams params = new HttpParams();
        params.put("reserveDate",reserveDate);
        params.put("platformId",platformId);
        params.put("warehouseId",warehouseId);
        params.put("breakTime",1);
        OkGo.<QueryVoLzyResponse<List<List<ReservationIndexBean>>>>get(BaseUrl.YT_Base + BaseUrl.reservationIndex)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<List<ReservationIndexBean>>>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<List<ReservationIndexBean>>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<List<ReservationIndexBean>>>> response, String desc) {
                        List<List<ReservationIndexBean>> data = response.body().getData();
                        if (data != null && data.size()>0) {
                            List<ReservationIndexBean> reservationIndexBeans = data.get(0);
                            ReservationIndexBean reservationIndexBean = reservationIndexBeans.get(0);
                            if (reservationIndexBean != null){
                                PlatformRuleBean platformRuleExt = reservationIndexBean.getPlatformRuleExt();
                                List<PlatformRuleBean.PlatformTimezoneExt> platformTimezoneList = platformRuleExt.getPlatformTimezoneList();
                                float classNum = 0.0f;
                                reservationList.clear();
                                timeList.clear();
                                for (int i = 0; i <platformTimezoneList.size() ; i++) {
                                    PlatformRuleBean.PlatformTimezoneExt platformTimezoneExt = platformTimezoneList.get(i);
                                    if (platformTimezoneExt.getClassNum() != 0){
                                        classNum = platformTimezoneExt.getClassNum();
                                    }
                                    int reservationNum = platformTimezoneExt.getReservationNum();
                                    reservationList.add(reservationNum);
                                    String startTime = platformTimezoneExt.getStartTime();
                                    String[] split = startTime.split(":");
                                    timeList.add(split[0]+":"+split[1]);
                                }
                                roseChart.setDataList(classNum,reservationList,timeList);
                            }
                        }
                    }
                });
    }

    /**
     * 弹出时间选择器
     */
    private void initTimerPicker() {
        String beginTime = "2019-01-01 00:00";
        String current = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        String endTime = "2039-12-31 23:59";

        CustomDatePicker mQycStartPicker = new CustomDatePicker(getActivity(), timestamp -> {
            selectTime = DateFormatUtils.long2Str(timestamp, false);
            if (selectTime.equals(TimeUtil.getDateID())){
                mRecyclerView.setVisibility(View.VISIBLE);
            }else {
                mRecyclerView.setVisibility(View.INVISIBLE);
            }
            tv_time.setText(selectTime.substring(5,selectTime.length()));
            countByReserveType(warehouseId,selectTime);
            listByWarehouseCode(platformWarehouseCode);

        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mQycStartPicker.setCancelable(false);
        // 显示时和分
        mQycStartPicker.setCanShowPreciseTime(false);
        mQycStartPicker.setScrollLoop(true);
        // 允许滚动动画
        mQycStartPicker.setCanShowAnim(true);
        mQycStartPicker.show(current);
    }

    /**
     * 查询仓库
     * @param isFirst
     */
    public void listWarehouse(boolean isFirst) {
        OkGo.<QueryVoLzyResponse<List<WarehouseBean>>>get(BaseUrl.YT_Base + BaseUrl.listWarehouse)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<WarehouseBean>>>(getActivity(), true) {
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
                                warehouseName = warehouseBean.getPlatformWarehouseName();
                                tv_warehouseName.setText(warehouseName);
                                platformWarehouseCode = warehouseBean.getPlatformWarehouseCode();
                                listByWarehouseCode(platformWarehouseCode);
                                warehouseId = warehouseBean.getWarehouseId();
                                countByReserveType(warehouseId,selectTime);
                                trackingKanBan(warehouseId);
                            }else {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWarehousePicker();
                                    }
                                },200);
                            }
                        }
                    }
                });
    }

    private List<String> typeList = new ArrayList<>();
    private OptionsPickerView typeOptions;

    private void initWarehousePicker() {
        if (typeOptions == null){
            typeOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    warehouseName = typeList.get(options1);
                    tv_warehouseName.setText(warehouseName);
                    platformWarehouseCode = warehouseData.get(options1).getPlatformWarehouseCode();
                    listByWarehouseCode(platformWarehouseCode);
                    warehouseId = warehouseData.get(options1).getWarehouseId();
                    countByReserveType(warehouseId,selectTime);
                    trackingKanBan(warehouseId);
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

    /**
     * 车辆状态
     */
    public void trackingKanBan(String warehouseId) {
        HttpParams params = new HttpParams();
        params.put("warehouseId",warehouseId);
        params.put("page","1");
        params.put("rows",30);
        OkGo.<QueryVoLzyResponse<KanBanCarListBean>>get(BaseUrl.YT_Base + BaseUrl.trackingKanBan)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<KanBanCarListBean>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<KanBanCarListBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<KanBanCarListBean>> response, String desc) {
                        KanBanCarListBean data = response.body().getData();
                        if (data != null) {
                            List<KanBanCarBean> list = data.getList();
                            adapter.setDataList(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
