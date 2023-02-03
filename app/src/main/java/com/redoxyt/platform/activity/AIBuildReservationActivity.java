package com.redoxyt.platform.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.AIBuildTimeAdapter;
import com.redoxyt.platform.adapter.BuildCheckAdapter;
import com.redoxyt.platform.adapter.BuildReservationAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.AiBuildResetBean;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationTimeBean;
import com.redoxyt.platform.bean.WarehouseListBean;
import com.redoxyt.platform.bean.YTListBean;
import com.redoxyt.platform.uitl.AppUtils;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.YTDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;
import utils.TextUtil;

/**
 * Created by zz.
 * description:AI新建预约
 */

public class AIBuildReservationActivity extends BaseActivity implements BuildReservationAdapter.OnItemClickListener,AIBuildTimeAdapter.OnTimeClickListener
    ,BuildCheckAdapter.OnOrderClickListener{

    @BindView(R.id.dl)
    DrawerLayout dl;
    @BindView(R.id.ll_slid)
    LinearLayout ll_slid;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.et_userName)
    EditText et_userName;
    @BindView(R.id.et_userMobile)
    EditText et_userMobile;
    @BindView(R.id.tv_changeCar)
    TextView tv_changeCar;
    @BindView(R.id.et_warehouseCode)
    EditText et_warehouseCode;
    @BindView(R.id.et_carCode)
    EditText et_carCode;
    @BindView(R.id.et_transportCode)
    EditText et_transportCode;
    @BindView(R.id.et_weight)
    EditText et_weight;
    @BindView(R.id.lv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.time_list)
    RecyclerView time_list;
    @BindView(R.id.check_list)
    RecyclerView check_list;

    @BindView(R.id.ll_driverName)
    LinearLayout ll_driverName;
    @BindView(R.id.ll_driverPhone)
    LinearLayout ll_driverPhone;
    @BindView(R.id.ll_warehouseCode)
    LinearLayout ll_warehouseCode;
    @BindView(R.id.ll_warehouse)
    LinearLayout ll_warehouse;
    @BindView(R.id.et_shipper)
    EditText et_shipper;
    @BindView(R.id.tv_addWarehouse)
    TextView tv_addWarehouse;
    @BindView(R.id.tv_deliveryGoods)
    TextView tv_deliveryGoods;
    @BindView(R.id.tv_takeGoods)
    TextView tv_takeGoods;
    @BindView(R.id.ll_more)
    LinearLayout ll_more;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.ll_moreMessage)
    LinearLayout ll_moreMessage;
    @BindView(R.id.tv_startEndTime)
    TextView tv_startEndTime;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_changeStartEndTime)
    TextView tv_changeStartEndTime;
    @BindView(R.id.ll_changeTime)
    LinearLayout ll_changeTime;
    private int reserveType = 1;//预约类型,1:送货预约;2:提货预约
    private int page = 1;
    private BuildReservationAdapter adapter;//月台
    private AIBuildTimeAdapter timeAdapter;//时间
    private BuildCheckAdapter checkAdapter;//选择
    private String warehouseGroupId,platformId,platformName,yyyyMMdd,platformRuleId;
    private String carId;
    private String warehouseId;
    private Handler handler = new Handler();
    private boolean isMore = true;
    private AiBuildResetBean aiBuildResetBean;

    private String lastPlatformName,lastPlatformId;
    private int selectPosition = 0;
    private List<ReservationBean> list = new ArrayList<>();
    private YTDialog dialog;

    @Override
    public void onItemClick(int position,YTListBean bean) {
        if (TextUtil.isEtNull(et_carCode,"请输入车牌号")) {
            adapter.getDataList().get(position).setCheck(false);
            adapter.notifyDataSetChanged();
            return;
        }
        lastPlatformId = this.platformId;
        lastPlatformName = this.platformName;
        setClick(position);
        this.platformName = bean.getPlatformName();
        this.platformId = bean.getPlatformId();
        getReserveDateTimezone(platformId);
    }

    private void setClick(int position){
        for (int i = 0; i < adapter.getDataList().size(); i++) {
            YTListBean bean = adapter.getDataList().get(i);
            if (i == position){
                bean.setCheck(true);
            }else {
                bean.setCheck(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void setClickWithId(String platformId){
        for (int i = 0; i < adapter.getDataList().size(); i++) {
            YTListBean bean = adapter.getDataList().get(i);
            if (platformId.equals(bean.getPlatformId())){
                bean.setCheck(true);
                selectPosition = i;
            }else {
                bean.setCheck(false);
            }
        }
        mRecyclerView.scrollToPosition(selectPosition);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int setView() {
        return R.layout.acitivty_ai_build_reservation;
    }

    @Override
    public void initData() {
        dialog = new YTDialog(AIBuildReservationActivity.this);
        et_userName.setText(ConfigUtils.getUserName());
        et_userMobile.setText(ConfigUtils.getUserMoble());

        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setListView();

        setTimeList();
        setCheckList();
        aiBuildResetBean = (AiBuildResetBean) getIntent().getSerializableExtra("AiBuildResetBean");
        et_warehouseCode.setText(aiBuildResetBean.getWarehouseCode());
        listByWarehouseCode(aiBuildResetBean.getWarehouseCode());
        tv_startEndTime.setText(aiBuildResetBean.getStartEndTime());
        int reserveType = aiBuildResetBean.getReserveType();
        if (reserveType == 1){
            setTabLayout(R.id.tv_deliveryGoods);
        }else {
            setTabLayout(R.id.tv_takeGoods);
        }

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
                        platformId = lastPlatformId;
                        platformName = lastPlatformName;
                        setClick(selectPosition);
                        dl.closeDrawer(Gravity.RIGHT);
                        break;
                }
                return false;
            }
        });

        et_warehouseCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String code = et_warehouseCode.getText().toString().trim();
                String replace = code.replace(" ", "");
                int length = replace.length();
                if (length == 6){
                    listByWarehouseCode(replace);
                }
            }
        });

        et_warehouseCode.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                String input=et_warehouseCode.getText().toString().trim();
                if(!TextUtils.isEmpty(input)){
                    listByWarehouseCode(input);
                }else {
                    showToast("请输入仓库码");
                }
                return true;
            }
            return false;
        });

        tv_warehouseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.hideInputMethod(AIBuildReservationActivity.this);
                if (warehouseList.size()>0){
                    initWarehousePicker();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListByFleetId();
    }

    private void setListView() {
        LinearLayoutManager manager = new LinearLayoutManager(AIBuildReservationActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new BuildReservationAdapter(AIBuildReservationActivity.this);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void setTimeList() {
        LinearLayoutManager manager = new LinearLayoutManager(AIBuildReservationActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        time_list.setLayoutManager(manager);
        timeAdapter = new AIBuildTimeAdapter(AIBuildReservationActivity.this);
        timeAdapter.setTimeClickListener(this);
        time_list.setAdapter(timeAdapter);
    }

    private void setCheckList() {
        LinearLayoutManager manager = new LinearLayoutManager(AIBuildReservationActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        check_list.setLayoutManager(manager);
        checkAdapter = new BuildCheckAdapter(AIBuildReservationActivity.this);
        checkAdapter.setOrderClickListener(this);
        check_list.setAdapter(checkAdapter);
    }

    @OnClick({R.id.tv_changeCar,R.id.tv_addWarehouse,R.id.tv_deliveryGoods,R.id.tv_takeGoods,R.id.ll_more,R.id.tv_next,R.id.tv_changeStartEndTime})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_changeCar:
                Bundle bundle = new Bundle();
                bundle.putString("type","BuildReservation");
                startActivityForResult(CarListActivity.class,bundle,0x11);
                return;
            case R.id.tv_addWarehouse:
                startActivityForResult(UserWarehouseActivity.class,0x12);
                break;
            case R.id.tv_deliveryGoods:
                setTabLayout(R.id.tv_deliveryGoods);
                break;
            case R.id.tv_takeGoods:
                setTabLayout(R.id.tv_takeGoods);
                break;
            case R.id.ll_more:
                if (isMore){
                    iv_more.setBackgroundResource(R.drawable.yt_ai_yuyue_xiala);
                    ll_moreMessage.setVisibility(View.VISIBLE);
                }else {
                    iv_more.setBackgroundResource(R.drawable.yt_ai_yuyue_join);
                    ll_moreMessage.setVisibility(View.GONE);
                }
                isMore = !isMore;
                break;
            case R.id.tv_next:
                String userName = et_userName.getText().toString();
                String userMobile = et_userMobile.getText().toString();
                if (StringUtils.isBlank(userName) || StringUtils.isBlank(userMobile)){
                    if (dialog != null){
                        dialog.setCancel("提示","您有必填项未填写，请“更多信息”进行内容补充","关闭",true);
                    }
                }else {
                    if (TextUtil.isEtNull(et_carCode,"请输入车牌号")) return;
                    createReservation(aiBuildResetBean.getPlatformTimezoneId(),aiBuildResetBean.getStartEndTime());
                }
                break;
            case R.id.tv_changeStartEndTime:
                if (TextUtil.isEtNull(et_carCode,"请输入车牌号")) return;
                getReserveDateTimezone(platformId);
                break;
        }
    }

    @Override
    public void onTimeClick(ReservationTimeBean bean) {
        if (bean != null){
            yyyyMMdd = bean.getYyyyMMdd();
            platformRuleId = bean.getPlatformRuleId();
            if (bean.getReserveStatus() == 0){
                getTimezones(platformId,yyyyMMdd);
            }else {
                checkAdapter.clear();
            }
        }
    }

    @Override
    public void onOrderClick(int platformTimezoneId, String startEndTime) {
        createReservation(platformTimezoneId,startEndTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 0x11:
                    String carCode = data.getStringExtra("carCode");
                    carId = data.getStringExtra("carId");
                    et_carCode.setText(carCode);
                    et_carCode.setSelection(carCode.length());
                    break;
                case 0x12:
                    String warehouseName = data.getStringExtra("warehouseName");
                    String warehouseCode = data.getStringExtra("warehouseCode");
                    warehouseId = data.getStringExtra("warehouseId");
                    et_warehouseCode.setText(warehouseCode);
                    tv_warehouseName.setText(warehouseName);
                    break;
            }
        }
    }

    private void showDrawerLayout() {
        if (!dl.isDrawerOpen(Gravity.RIGHT)) {
            dl.openDrawer(Gravity.RIGHT);
        } else {
            dl.closeDrawer(Gravity.RIGHT);
        }
    }


    public void setData(){
        getReserveDateTimezone(platformId);
    }


    /**
     * 司机创建预约
     */
    public void createReservation(int platformTimezoneId, String startEndTime) {
        HttpParams params = new HttpParams();
        params.put("orderType",1);
        params.put("reserveType",reserveType);
        String input=et_warehouseCode.getText().toString().trim();
        params.put("warehouseCode",input);

        params.put("warehouseGroupId",warehouseGroupId);
        params.put("warehouseName",tv_warehouseName.getText().toString());
        params.put("driverName",ConfigUtils.getUserName());
        params.put("driverMobile",ConfigUtils.getUserMoble());
        params.put("driverId",ConfigUtils.getUserId());
        params.put("carId",carId);
        params.put("carCode",et_carCode.getText().toString().trim());
        params.put("platformId",platformId);
        params.put("platformName",platformName);
        params.put("transportCode",et_transportCode.getText().toString());
        params.put("weight",et_weight.getText().toString());
        params.put("platformTimezoneId",platformTimezoneId);
        params.put("yyyyMMdd",yyyyMMdd);
        params.put("startEndTime",startEndTime);
        params.put("platformRuleId",platformRuleId);
        params.put("shipper",et_shipper.getText().toString());
        params.put("warehouseId",warehouseId);


        OkGo.<QueryVoLzyResponse<ReservationBean>>post(BaseUrl.YT_Base + BaseUrl.createReservation)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationBean>>(this, true) {
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
                        showToast(desc);
                        ReservationBean data = response.body().getData();
                        if (data != null){
                            String reserveCode = data.getReserveCode();
                            ConfigUtils.saveReserveCode(reserveCode);
                            BaseUrl.TO_MAIN_MYRESERVATIONFRAGMENT = 1;
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isAiBuid",true);
                            startActivity(MainDriverActivity.class,bundle);
                        }
                    }
                });
    }

    public void getReserveDateTimezone(String platformId) {
        HttpParams params = new HttpParams();
        params.put("platformId",platformId);
        params.put("page",page);
        params.put("rows",20);
        OkGo.<QueryVoLzyResponse<List<ReservationTimeBean>>>get(BaseUrl.YT_Base + BaseUrl.getReserveDateTimezone)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<ReservationTimeBean>>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<ReservationTimeBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<ReservationTimeBean>>> response, String desc) {
                        List<ReservationTimeBean> data = response.body().getData();
                        if (data != null && data.size()>0){
                            if (page == 1){
                                timeAdapter.setDataList(data);
                                showDrawerLayout();
                                ReservationTimeBean reservationTimeBean = data.get(0);
                                reservationTimeBean.setCheck(true);
                                yyyyMMdd = reservationTimeBean.getYyyyMMdd();
                                platformRuleId = reservationTimeBean.getPlatformRuleId();
                                if (reservationTimeBean != null && reservationTimeBean.getSubs() != null && reservationTimeBean.getSubs().size()>0){
                                    List<ReservationTimeBean.SubsBean> subs = reservationTimeBean.getSubs();
                                    checkAdapter.setDataList(subs);
                                }else {
                                    checkAdapter.clear();
                                }

                                checkAdapter.notifyDataSetChanged();
                            }else {
                                timeAdapter.addAll(data);
                            }
                            timeAdapter.notifyDataSetChanged();
                            page++;
                        }
                    }
                });
    }

    public void getTimezones(String platformId,String yyyymmdd) {
        HttpParams params = new HttpParams();
        params.put("platformId",platformId);
        params.put("yyyymmdd",yyyymmdd);
        OkGo.<QueryVoLzyResponse<List<ReservationTimeBean.SubsBean>>>get(BaseUrl.YT_Base + BaseUrl.getTimezones)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<ReservationTimeBean.SubsBean>>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<ReservationTimeBean.SubsBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<ReservationTimeBean.SubsBean>>> response, String desc) {
                        List<ReservationTimeBean.SubsBean> data = response.body().getData();
                        if (data != null && data.size()>0){
                            checkAdapter.setDataList(data);
                            checkAdapter.notifyDataSetChanged();
                        }else {
                            checkAdapter.clear();
                        }
                    }
                });
    }

    public void listByWarehouseCode(String warehouseCode) {
        adapter.clear();
        HttpParams params = new HttpParams();
        params.put("warehouseCode",warehouseCode);
        OkGo.<QueryVoLzyResponse<List<YTListBean>>>get(BaseUrl.YT_Base + BaseUrl.litsByWarehouseCode)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<YTListBean>>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<YTListBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<YTListBean>>> response, String desc) {
                        List<YTListBean> data = response.body().getData();
                        if (data != null && data.size()>0){
                            YTListBean ytListBean = data.get(0);
                            warehouseId = ytListBean.getWarehouseId();
                            tv_warehouseName.setText(ytListBean.getWarehouseName());
                            warehouseGroupId = ytListBean.getWarehouseGroupId();
                            adapter.setDataList(data);
                            if (aiBuildResetBean.getWarehouseCode().equals(warehouseCode)){//推荐选中的月台
                                setClickWithId(aiBuildResetBean.getPlatformId());
                                platformId = aiBuildResetBean.getPlatformId();
                                tv_startEndTime.setText(aiBuildResetBean.getStartEndTime());
                                yyyyMMdd = aiBuildResetBean.getYyyymmdd();
                                platformRuleId = aiBuildResetBean.getPlatformRuleId();
                                platformName = aiBuildResetBean.getPlatformName();
                            }else {
                                ll_changeTime.setVisibility(View.GONE);
                                tv_next.setVisibility(View.GONE);
                            }
                            adapter.notifyDataSetChanged();
                        }else {
                            adapter.clear();
                            showToast("未查询到月台信息");
                        }
                    }
                });
    }

    /**
     * 查询常用仓库
     */
    public void getListByFleetId() {
        list.clear();
        warehouseList.clear();
        HttpParams params = new HttpParams();
        params.put("fleetId",ConfigUtils.getGroupId());
        OkGo.<QueryVoLzyResponse<WarehouseListBean>>get(BaseUrl.YT_Base + BaseUrl.getListByFleetId)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<WarehouseListBean>>(AIBuildReservationActivity.this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<WarehouseListBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<WarehouseListBean>> response, String desc) {
                        WarehouseListBean data = response.body().getData();
                        if (data != null){
                            list = data.getList();
                            if (list.size()>0){
                                for (int i = 0; i < list.size(); i++) {
                                    ReservationBean bean = list.get(i);
                                    warehouseList.add(bean.getWarehouseName());
                                    warehouseIdList.add(bean.getWarehouseId());
                                }
                            }
                        }
                    }
                });
    }

    private List<String> warehouseList = new ArrayList<>();
    private List<String> warehouseIdList = new ArrayList<>();
    private OptionsPickerView typeOptions;

    private void initWarehousePicker() {
        typeOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String value = warehouseList.get(options1);
            warehouseId = warehouseIdList.get(options1);
            tv_warehouseName.setText(value);

            ReservationBean bean = list.get(options1);
            et_warehouseCode.setText(bean.getWarehouseCode());
            et_warehouseCode.setSelection(bean.getWarehouseCode()!=null?bean.getWarehouseCode().length():0);
        })
                .setLayoutRes(R.layout.pickerview_custom_options, v -> {
                    final TextView tvSubmit = v.findViewById(R.id.tv_confirm);
                    TextView ivCancel = v.findViewById(R.id.tv_cancel);
                    tvSubmit.setOnClickListener(v1 -> {
                        typeOptions.dismiss();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                typeOptions.returnData();
                            }
                        },500);
                    });

                    ivCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            typeOptions.dismiss();
                        }
                    });
                })
                .isDialog(false)
                .setOutSideCancelable(false)
                .build();

        typeOptions.setPicker(warehouseList);
        typeOptions.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void setTabLayout(int view){
        tv_deliveryGoods.setTextColor(getResources().getColor(R.color.black_333));
        tv_deliveryGoods.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tv_takeGoods.setTextColor(getResources().getColor(R.color.black_333));
        tv_takeGoods.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        Drawable bottomDrawable = getResources().getDrawable(R.drawable.yt_tab_zsx);
        bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
        tv_deliveryGoods.setCompoundDrawables(null, null, null, null);
        tv_takeGoods.setCompoundDrawables(null, null, null, null);
        if (view == R.id.tv_deliveryGoods){
            reserveType = 1;
            tv_deliveryGoods.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_deliveryGoods.setTextColor(getResources().getColor(R.color.black_333));
            tv_deliveryGoods.setCompoundDrawables(null, null, null, bottomDrawable);
        }else if (view == R.id.tv_takeGoods){
            reserveType = 2;
            tv_takeGoods.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_takeGoods.setTextColor(getResources().getColor(R.color.black_333));
            tv_takeGoods.setCompoundDrawables(null, null, null, bottomDrawable);
        }
    }
}
