package com.redoxyt.platform.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.BuildCheckAdapter;
import com.redoxyt.platform.adapter.BuildReservationAdapter;
import com.redoxyt.platform.adapter.BuildTimeAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationTimeBean;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.bean.WarehouseListBean;
import com.redoxyt.platform.bean.YTListBean;
import com.redoxyt.platform.uitl.AppUtils;
import com.redoxyt.platform.uitl.KeyboardUtil;
import com.redoxyt.platform.uitl.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
 * description:新建预约
 */

public class BuildReservationActivity extends BaseActivity implements BuildReservationAdapter.OnItemClickListener,BuildTimeAdapter.OnTimeClickListener
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
    @BindView(R.id.ll_fleet)
    LinearLayout ll_fleet;
    @BindView(R.id.ll_warehouseCode)
    LinearLayout ll_warehouseCode;
    @BindView(R.id.ll_warehouse)
    LinearLayout ll_warehouse;
    @BindView(R.id.et_fleet)
    EditText et_fleet;
    @BindView(R.id.et_shipper)
    EditText et_shipper;
    @BindView(R.id.tv_addWarehouse)
    TextView tv_addWarehouse;
    @BindView(R.id.tv_deliveryGoods)
    TextView tv_deliveryGoods;
    @BindView(R.id.tv_takeGoods)
    TextView tv_takeGoods;
    private int reserveType = 1;//预约类型,1:送货预约;2:提货预约
    private int page = 1;
    private int userFlag;
    private BuildReservationAdapter adapter;//月台
    private BuildTimeAdapter timeAdapter;//时间
    private BuildCheckAdapter checkAdapter;//选择
    private String warehouseGroupId,platformId,platformName,yyyyMMdd,platformRuleId;
    private KeyboardUtil keyboardUtil;
    private String carId;
    private String warehouseId;
    private Handler handler = new Handler();

    private List<ReservationBean> list = new ArrayList<>();

    @Override
    public void onItemClick(int position,YTListBean bean) {
        this.platformName = bean.getPlatformName();
        this.platformId = bean.getPlatformId();
        setClick(position);
        if (TextUtil.isEtNull(et_carCode,"请输入车牌号")) return;
        if (userFlag == 4){
            String userName = et_userName.getText().toString().trim();
            if (StringUtils.isBlank(userName)){
                showToast("请输入司机姓名");
                return;
            }
            String userMoble = et_userMobile.getText().toString().trim();
            if (StringUtils.isBlank(userMoble)){
                showToast("请输入司机电话");
                return;
            }
        }
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

    @Override
    public int setView() {
        return R.layout.acitivty_build_reservation;
    }

    @Override
    public void initData() {
        userFlag = ConfigUtils.getUserFlag();
        if (userFlag == 4){//车队
            tv_changeCar.setVisibility(View.GONE);
            et_fleet.setEnabled(false);
            et_fleet.setText(ConfigUtils.getGroupAbbr());
        }else {//司机
            ll_driverName.setVisibility(View.GONE);
            ll_driverPhone.setVisibility(View.GONE);
            ll_fleet.setVisibility(View.GONE);
        }

        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setListView();

        setTimeList();
        setCheckList();

        String warehouseCode = getIntent().getStringExtra("warehouseCode");
        if (StringUtils.isNotBlank(warehouseCode)){
            et_warehouseCode.setText(warehouseCode);
            listByWarehouseCode(warehouseCode);
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

        et_carCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if (userFlag != 3){
                        hideSoftInputMethod();
                        if (dialog != null && !dialog.isShowing()){
                            dialog.show();
                        }else {
                            showKeyboardDialog();
                        }
                    }
                }
                return false;
            }
        });

        tv_warehouseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.hideInputMethod(BuildReservationActivity.this);
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
        LinearLayoutManager manager = new LinearLayoutManager(BuildReservationActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new BuildReservationAdapter(BuildReservationActivity.this);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void setTimeList() {
        LinearLayoutManager manager = new LinearLayoutManager(BuildReservationActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        time_list.setLayoutManager(manager);
        timeAdapter = new BuildTimeAdapter(BuildReservationActivity.this);
        timeAdapter.setTimeClickListener(this);
        time_list.setAdapter(timeAdapter);
    }

    private void setCheckList() {
        LinearLayoutManager manager = new LinearLayoutManager(BuildReservationActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        check_list.setLayoutManager(manager);
        checkAdapter = new BuildCheckAdapter(BuildReservationActivity.this);
        checkAdapter.setOrderClickListener(this);
        check_list.setAdapter(checkAdapter);
    }

    @OnClick({R.id.tv_changeCar,R.id.tv_addWarehouse,R.id.tv_deliveryGoods,R.id.tv_takeGoods})
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

    Dialog dialog = null;
    private void showKeyboardDialog() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.Dialog);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_keyboard_layout, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.dialogAnim);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (keyboardUtil == null) {
            keyboardUtil = new KeyboardUtil(BuildReservationActivity.this, et_carCode,view);
            keyboardUtil.hideSoftInputMethod();
            keyboardUtil.showKeyboard();
        } else {
            keyboardUtil.showKeyboard();
        }
        dialog.show();

    }

    /**
     * 禁掉系统软键盘
     */
    public void hideSoftInputMethod() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            et_carCode.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(et_carCode, false);
            } catch (NoSuchMethodException e) {
                et_carCode.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
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

        if (userFlag == 4){
            String userName = et_userName.getText().toString().trim();
            params.put("driverName",userName);
            String userMoble = et_userMobile.getText().toString().trim();
            params.put("driverMobile",userMoble);
            params.put("fleetId",ConfigUtils.getGroupId());
            params.put("fleetName",ConfigUtils.getUserName());
        }else {
            params.put("warehouseGroupId",warehouseGroupId);
            params.put("warehouseName",tv_warehouseName.getText().toString());
            params.put("driverName",ConfigUtils.getUserName());
            params.put("driverMobile",ConfigUtils.getUserMoble());
            params.put("driverId",ConfigUtils.getUserId());
            params.put("carId",carId);
        }
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
                            Intent intent = new Intent();
                            intent.putExtra("reserveCode", reserveCode);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
    }

    public void getReserveDateTimezone(String platformId) {
        HttpParams params = new HttpParams();
        params.put("platformId",platformId);
        if (userFlag == 4){
            params.put("fleetId",ConfigUtils.getGroupId());
        }
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
        if (userFlag == 4){
            params.put("fleetId",ConfigUtils.getGroupId());
        }
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
        if (userFlag == 4){
            params.put("fleetId",ConfigUtils.getGroupId());
        }
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
                            adapter.notifyDataSetChanged();
                        }else {
                            adapter.clear();
                            showToast("未查询到月台信息");
                        }
                    }
                });
    }

    /**
     * 根据车队Id查询车队名称
     * @param id
     */
    public void tmsGroupGet(String id) {
        HttpParams params = new HttpParams();
        params.put("groupId",id);
        OkGo.<QueryVoLzyResponse<UserBean>>get(BaseUrl.YT_Base + BaseUrl.selectByGroupId)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<UserBean>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<UserBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<UserBean>> response, String desc) {
                        UserBean data = response.body().getData();
                        if (data != null){
                            et_fleet.setText(data.getGroupAbbr());
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
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<WarehouseListBean>>(BuildReservationActivity.this, true) {
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
