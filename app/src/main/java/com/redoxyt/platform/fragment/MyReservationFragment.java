package com.redoxyt.platform.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.BuildReservationActivity;
import com.redoxyt.platform.activity.DriverWorkActivity;
import com.redoxyt.platform.activity.InfoAuthActivity;
import com.redoxyt.platform.activity.MainActivity;
import com.redoxyt.platform.activity.MainDriverActivity;
import com.redoxyt.platform.activity.MoreWareHouseOrderActivity;
import com.redoxyt.platform.activity.ParkingPayLoadingActivity;
import com.redoxyt.platform.activity.ParkingPayOrderActivity;
import com.redoxyt.platform.activity.PayLoadingActivity;
import com.redoxyt.platform.activity.PayOrderActivity;
import com.redoxyt.platform.activity.PlatformListActivity;
import com.redoxyt.platform.base.BaseEventMessage;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.AddressMessageBean;
import com.redoxyt.platform.bean.AiBuildResetBean;
import com.redoxyt.platform.bean.DriverReportBean;
import com.redoxyt.platform.bean.QiyeXinXiReportBean;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;
import com.redoxyt.platform.qr.CaptureActivity;
import com.redoxyt.platform.service.LocationServiceNew;
import com.redoxyt.platform.uitl.CreditPermissionUtil;
import com.redoxyt.platform.uitl.MapUtil;
import com.redoxyt.platform.uitl.OnMultiClickUtils;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.CountDownDialog;
import com.redoxyt.platform.widget.MarqueeText;
import com.redoxyt.platform.widget.ParkingPayDialog;
import com.redoxyt.platform.widget.SystemTTS;
import com.redoxyt.platform.widget.YTDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.PermissionsUtils;
import util.TimeUtil;
import utils.ConfigUtils;
import view.RequestDialog;
import widget.CommonToolbar;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_AUTO_CREATE;

/**
 * 司机身份--我的预约
 */

public class MyReservationFragment extends BaseFragment implements ServiceConnection, YTDialog.SubmitCallBack {

    @BindView(R.id.ctv_title)
    CommonToolbar ctv_title;
    @BindView(R.id.tv_build)
    TextView tv_build;
    @BindView(R.id.tv_build_more)
    TextView tv_build_more;
    @BindView(R.id.rl_newOrder)
    RelativeLayout rl_newOrder;
    @BindView(R.id.sc_orderStats)
    ScrollView sc_orderStats;
    @BindView(R.id.tv_reportTime)
    TextView tv_reportTime;
    @BindView(R.id.tv_report)
    TextView tv_report;
    @BindView(R.id.tv_carCode)
    TextView tv_carCode;
    @BindView(R.id.tv_reserveCode)
    TextView tv_reserveCode;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_startEndTime)
    TextView tv_startEndTime;
    @BindView(R.id.tv_platformName)
    TextView tv_platformName;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.tv_warehouseAddress)
    TextView tv_warehouseAddress;
    @BindView(R.id.ll_lineup)
    LinearLayout ll_lineup;
    @BindView(R.id.iv_reservationStatus)
    ImageView iv_reservationStatus;
    @BindView(R.id.tv_tips)
    MarqueeText tv_tips;
    @BindView(R.id.tv_lineNum)
    TextView tv_lineNum;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_countDown_text)
    TextView tv_countDown_text;
    @BindView(R.id.tv_refresh)
    TextView tv_refresh;
    @BindView(R.id.ll_scanWork)
    LinearLayout ll_scanWork;
    @BindView(R.id.ll_working_scan)
    LinearLayout ll_working_scan;
    @BindView(R.id.tv_scan)
    TextView tv_scan;

    private String reserveCode;//预约码
    private int reserveStatus;//预约状态
    private String reserveId;//预约ID
    private String warehouseGroupId;//仓库机构Id
    private String warehouseId;//仓库Id
    private TextView btnRight;
    private YTDialog ytDialog;
    private int dialogStatus = -1;//1表示取消预约,2去认证,3去支付,4停车费
    private double payAmount;
    private ParkingPayDialog parkingPayDialog;
    private double longitude, latitude;
    private CountDownDialog countDownDialog;

    private CountDownTimer countDownTimer;
    private boolean isBaodao;

    @Override
    protected void initData() {
        parkingPayDialog = new ParkingPayDialog(getActivity());
        parkingPayDialog.setCallBack(() -> {
            Bundle bundle = new Bundle();
            bundle.putString("reserveId", reserveId);
            startActivityForResult(ParkingPayOrderActivity.class, bundle, 0x11);
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getByReserveList();
            swipeRefreshLayout.setRefreshing(false);
        });

        btnRight = ctv_title.getBtnRight();
        btnRight.setText("取消预约");
        btnRight.setTextSize(12);
        btnRight.setTextColor(getResources().getColor(R.color.white));
        btnRight.setPadding(10, 5, 10, 5);
        btnRight.setBackground(getResources().getDrawable(R.drawable.yt_button));
        btnRight.setVisibility(View.GONE);

        btnRight.setOnClickListener(view -> {
            if (ytDialog != null && !ytDialog.isShowing()) {
                dialogStatus = 1;
                ytDialog.setContent("提示", "确定取消预约吗？", "确定");
            }
        });

        ytDialog = new YTDialog(getActivity());
        ytDialog.setCallBack(this);

        tv_warehouseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        countDownDialog = new CountDownDialog(getActivity());
        countDownDialog.setCallBack(new CountDownDialog.SubmitCallBack() {
            @Override
            public void countDownSubmit() {
                tv_countDown_text.setVisibility(View.VISIBLE);
            }

            @Override
            public void countDownCancel() {
                noOnTimeArrive();
            }
        });

    }

    /**
     * 选择图片弹窗
     */
    private void showPictureDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
        //2、设置布局
        View view = View.inflate(getActivity(), R.layout.dialog_map_layout, null);
        TextView tv_baidu = view.findViewById(R.id.tv_baidu);
        TextView tv_gaode = view.findViewById(R.id.tv_gaode);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.dialogAnim);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (MapUtil.isBaiduMapInstalled()) {
            tv_baidu.setVisibility(View.VISIBLE);
        }
        if (MapUtil.isGdMapInstalled()) {
            tv_gaode.setVisibility(View.VISIBLE);
        }
        if (tv_baidu.getVisibility() == View.VISIBLE || tv_gaode.getVisibility() == View.VISIBLE) {
            dialog.show();
        } else {
            showToast("尚未地图应用");
        }

        tv_baidu.setOnClickListener(view1 -> {
            String s = tv_warehouseAddress.getText().toString();
            if (s.length() > 5) {
                s = s.substring(5, s.length());
            }
            MapUtil.openBaiDuNavi(getActivity(), 0, 0, tv_warehouseName.getText().toString(), latitude, longitude, s);
        });

        tv_gaode.setOnClickListener(view12 -> {
            String s = tv_warehouseAddress.getText().toString();
            if (s.length() > 5) {
                s = s.substring(5, s.length());
            }
            MapUtil.openGaoDeNavi(getActivity(), 0, 0, tv_warehouseName.getText().toString(), latitude, longitude, s);
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view14 -> {
            //取消
            dialog.dismiss();
        });

    }

    //倒计时功能
    CountDownTimer timerLogin = new CountDownTimer(20000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (reserveStatus < 3) {
                timerLogin.start();
                countDownQueueNum(reserveId);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getByReserveList();
    }

    @Override
    public void onPause() {
        super.onPause();
        timerLogin.cancel();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my_reservation;
    }

    @OnClick({R.id.tv_build, R.id.tv_report, R.id.tv_refresh, R.id.ll_scanWork, R.id.ll_working_scan, R.id.tv_build_more, R.id.tv_scan,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_build:
                if (ConfigUtils.getUserStatus().equals("1")) {
                    if (ytDialog != null && !ytDialog.isShowing()) {
                        dialogStatus = 2;
                        ytDialog.setContent("提示", "未认证信息暂不能创建预约", "去认证");
                    }
                } else {
                    startActivityForResult(BuildReservationActivity.class, 0x11);//创建预约判断黑名单
//                    getBydriverPhone();//检查是否在黑名单
                }
                break;
            case R.id.tv_report:
                applyPermission();
               /* if (getPermissions()) {
                    applyPermission();
                    if (OnMultiClickUtils.isMultiClickClick(getActivity())) {
                        isBaodao=true;
                        startLocation();
                    }
                } else {
                    showPermissionDialog();
                }*/
                break;
            case R.id.tv_refresh:
                countDownQueueNum(reserveId);
                break;
            case R.id.ll_scanWork://司机扫码作业
            case R.id.ll_working_scan://司机完成作业
                ArrayList<String> list1 = new ArrayList<>();
                list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
                list1.add("android.permission.CAMERA");
                if (PermissionsUtils.requestPermission(getActivity(), list1)) {
                    Bundle bundleCar = new Bundle();
                    bundleCar.putString("title", "扫描月台二维码");
                    startActivityForResult(CaptureActivity.class, bundleCar, 0x13);
                } else {
                    showToast("需要照相机权限");
                }
                break;
            case R.id.tv_scan://司机扫码预约
                applyCameraPermission();
                break;
            case R.id.tv_build_more:
                startActivity(MoreWareHouseOrderActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x11:
                    getByReserveList();
                    break;
                case 0x12:
                    String scan = data.getExtras().getString("uuid").toString();
                    try {
                        HashMap<String, String> car = new Gson().fromJson(scan, new TypeToken<HashMap<String, String>>() {
                        }.getType());
                        if (StringUtils.isNotBlank(car.get("codeType"))) {
                            String codeType = car.get("codeType");
                            if (codeType.equals("1")) {//企业二维码
                                addMuchWareHouse(car.get("groupNum"));
                            } else if (codeType.equals("2")) {//仓库二维码
                                String warehouseCode = car.get("warehouseCode");
                                Bundle bundle = new Bundle();
                                bundle.putString("warehouseCode", warehouseCode);
                                startActivity(BuildReservationActivity.class, bundle);
                            }else {
                                showToast("请扫描企业或仓库二维码");
                            }
                        } else {
                            showToast("此二维码无效");
                        }
                    } catch (Exception e) {
                        showToast("此二维码无效");
                        e.printStackTrace();
                    }
                    break;
                case 0x13:
                    String scanWorking = data.getExtras().getString("uuid").toString();
                    try {
                        HashMap<String, String> car = new Gson().fromJson(scanWorking, new TypeToken<HashMap<String, String>>() {
                        }.getType());
                        if (StringUtils.isNotBlank(car.get("codeType"))) {
                            String codeType = car.get("codeType");
                            if (codeType.equals("3")) {//月台二维码
                                String platformId = car.get("platformId");
                                Bundle bundle = new Bundle();
                                bundle.putString("platformId", platformId);
                                bundle.putInt("reserveStatus", reserveStatus);
                                bundle.putString("reserveCode", reserveCode);
                                startActivity(DriverWorkActivity.class, bundle);
                            } else {
                                showToast("此码不是月台二维码");
                            }
                        } else {
                            showToast("此二维码无效");
                        }
                    } catch (Exception e) {
                        showToast("此二维码无效");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * 查询预约信息
     */
    public void getByReserveList() {
        HttpParams params = new HttpParams();
        params.put("driverId", ConfigUtils.getUserId());
        params.put("finished", 0);
        params.put("sortType", 1);
        params.put("needLatLon", 1);
        OkGo.<QueryVoLzyResponse<ReservationListBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveList)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationListBean>>(getActivity(), false) {
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
                        if (data != null) {
                            List<ReservationBean> reservationList = data.getReservationList();
                            if (reservationList.size() > 0) {
                                rl_newOrder.setVisibility(View.GONE);
                                sc_orderStats.setVisibility(View.VISIBLE);
                                ReservationBean bean = reservationList.get(0);
                                tv_carCode.setText(bean.getCarCode());
                                reserveId = bean.getReserveId();
                                reserveStatus = bean.getReserveStatus();
                                countDownQueueNum(reserveId);
                                warehouseGroupId = bean.getWarehouseGroupId();
                                warehouseId = bean.getWarehouseId();
                                longitude = bean.getPlatformLon();
                                latitude = bean.getPlatformLat();
                                reserveCode = bean.getReserveCode();
                                tv_reserveCode.setText("预约码：" + reserveCode);
                                ConfigUtils.saveReserveCode(bean.getReserveCode());
                                ConfigUtils.saveWarehouseId(bean.getWarehouseId());
                                tv_date.setText("预约日期：" + bean.getReserveDate());
                                tv_startEndTime.setText("预约时间： " + bean.getReserveTimeZone());
                                tv_platformName.setText("预约月台：" + bean.getPlatformName());
                                tv_warehouseName.setText("预约仓库：" + bean.getWarehouseName());
                                tv_warehouseAddress.setText("详细地址：" + bean.getWarehouseAddress());
                                payAmount = bean.getPayAmount();
                                if (reserveStatus == 0 || reserveStatus == 1) {
                                    tv_report.setVisibility(View.VISIBLE);
                                    timerLogin.start();//20s刷新一次时间
                                    btnRight.setVisibility(View.VISIBLE);
                                    ll_lineup.setVisibility(View.GONE);
                                    iv_reservationStatus.setImageResource(R.drawable.yt_wbd_bg);
                                } else if (reserveStatus == 2) {
                                    timerLogin.start();//20s刷新一次时间
                                    ll_lineup.setVisibility(View.VISIBLE);
                                    iv_reservationStatus.setImageResource(R.drawable.yt_ybd_bg);
                                    btnRight.setVisibility(View.VISIBLE);
                                } else if (reserveStatus == 3) {
                                    tv_countDown_text.setVisibility(View.GONE);
                                    if (countDownTimer != null) {
                                        countDownTimer.cancel();
                                    }
                                    timerLogin.cancel();
                                    tv_report.setVisibility(View.GONE);
                                    btnRight.setVisibility(View.GONE);
                                    ll_lineup.setVisibility(View.GONE);
                                    iv_reservationStatus.setImageResource(R.drawable.yt_zyz);
                                } else if (reserveStatus == 4) {//已完成
                                    isSpeak = true;
                                    if (bean.getParkPayStatus() == 1) {//停车费待支付
                                        if (parkingPayDialog != null && !parkingPayDialog.isShowing()) {
                                            parkingPayDialog.setContent("您已完成" + bean.getReserveTypeName() + "操作，您停车时长为" + bean.getParkingMinutes() + "分钟,请缴纳停车费", bean.getParkPayAmount() + "");
                                        }
                                    } else if (bean.getParkPayStatus() == 2) {//停车费支付中
                                        if (parkingPayDialog != null && parkingPayDialog.isShowing()) {
                                            parkingPayDialog.dismiss();
                                        }
                                        if (ytDialog != null && !ytDialog.isShowing()) {
                                            dialogStatus = 4;
                                            ytDialog.setCancel("提示", "停车费用正在支付中，请稍后...", "我知道了", true);
                                        }
                                    } else {
                                        if (parkingPayDialog != null && parkingPayDialog.isShowing()) {
                                            parkingPayDialog.dismiss();
                                        }
                                    }
                                    timerLogin.cancel();
                                    ConfigUtils.saveReserveCode("");
                                    ConfigUtils.saveWarehouseId("");
                                    btnRight.setVisibility(View.GONE);
                                    ll_lineup.setVisibility(View.VISIBLE);
                                    iv_reservationStatus.setImageResource(R.drawable.yt_ywc);
                                }
                            } else {
                                tv_countDown_text.setVisibility(View.GONE);
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
                                boolean isAiBuid = getActivity().getIntent().getBooleanExtra("isAiBuid", false);
                                if (!isAiBuid) {
                                    ((MainDriverActivity) getActivity()).remainMuchWarehouse();
                                }
                                if (parkingPayDialog != null && parkingPayDialog.isShowing()) {
                                    parkingPayDialog.dismiss();
                                }
                                timerLogin.cancel();
                                ConfigUtils.saveReserveCode("");
                                ConfigUtils.saveWarehouseId("");
                                rl_newOrder.setVisibility(View.VISIBLE);
                                sc_orderStats.setVisibility(View.GONE);
                                btnRight.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }


    /**
     * 司机是否满足报道(预约时间、距离)
     */
    public void driverReport(double longitudeFrom, double latitudeFrom) {
        HttpParams params = new HttpParams();
        params.put("reserveId", reserveId);
        params.put("warehouseGroupId", warehouseGroupId);
        params.put("warehouseId", warehouseId);
        params.put("longitudeFrom", longitudeFrom);
        params.put("latitudeFrom", latitudeFrom);
        OkGo.<QueryVoLzyResponse<DriverReportBean>>get(BaseUrl.YT_Base + BaseUrl.driverReport)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<DriverReportBean>>(getActivity(), true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        if (code == -1) {
                            if (ytDialog != null && !ytDialog.isShowing()) {
                                ytDialog.setCancel("提示", desc, "我知道了", true);
                            }
                        } else {
                            showToast(desc);
                        }
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccessNotData(Response<QueryVoLzyResponse<DriverReportBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<DriverReportBean>> response, String desc) {
                        DriverReportBean data = response.body().getData();
                        if (data != null) {
                            int flag = data.getFlag();
                            if (flag == 0) {//支付
                                if (ytDialog != null && !ytDialog.isShowing()) {
                                    dialogStatus = 3;
                                    ytDialog.setContent("提示", "您已成功进入预约队列中，请尽快付款" + payAmount + "元，以完成报到", "确定");
                                }
                            } else if (flag == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putString("reserveId", reserveId);
                                startActivity(PayLoadingActivity.class, bundle);
                            } else if (flag == 2) {
                                showToast(desc);
                                getByReserveList();
                            }
                        }
                    }
                });
    }


    /**
     * 根据手机号查询是否在黑名单
     */
    public void getBydriverPhone() {
        HttpParams params = new HttpParams();
        params.put("driverPhone", ConfigUtils.getUserMoble());
        OkGo.<QueryVoLzyResponse<String>>post(BaseUrl.YT_Base + BaseUrl.getBydriverPhone)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(getActivity(), true) {
                    @Override
                    public void onFail(int code, String desc) {
                        showToast(desc);
                        super.onFail(code, desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        String data = response.body().getData();
                        startActivityForResult(BuildReservationActivity.class, 0x11);
                    }
                });
    }


    /**
     * @param reserveId
     */
    public void countDownQueueNum(String reserveId) {
        HttpParams params = new HttpParams();
        params.put("reserveId", reserveId);
        OkGo.<QueryVoLzyResponse<DriverReportBean>>get(BaseUrl.YT_Base + BaseUrl.countDownQueueNum)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<DriverReportBean>>(getActivity(), false) {
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

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<DriverReportBean>> response, String desc) {
                        DriverReportBean data = response.body().getData();
                        if (data != null) {
                            String itemDesc = data.getItemDesc();
                            if (StringUtils.isNotBlank(itemDesc)) {
                                tv_tips.setText(itemDesc);
                            }
                            if (StringUtils.isNotBlank(data.getTime())) {
                                tv_reportTime.setText(data.getTime());
                                String queueNum = data.getQueueNum();
                                tv_lineNum.setText(queueNum != null ? queueNum : "1");
                                if (StringUtils.isNotBlank(queueNum)) {
                                    int queue = Integer.parseInt(queueNum);
                                    String platformNumberCar = data.getPlatformNumberCar();
                                    if (StringUtils.isNotBlank(platformNumberCar)){
                                        int platformNumber = Integer.parseInt(platformNumberCar);
                                        if (queue <= platformNumber) {
                                            iv_reservationStatus.setImageResource(R.drawable.yt_krc);
                                            if (isSpeak) {
                                                SystemTTS instance = SystemTTS.getInstance(getActivity());
                                                instance.playText("可以入场了，请尽快入场，谢谢. 可以入场了，请尽快入场，谢谢. 可以入场了，请尽快入场，谢谢. ");
                                                isSpeak = false;
                                                //弹出倒计时弹窗
                                                if (data.getEntranceRemind() == 1) {//1为提示
                                                    String second = data.getSecond();
                                                    if (StringUtils.isNotBlank(second)) {
                                                        long longTime = (Long.parseLong(second)) * 1000;
                                                        if (longTime > 0) {
                                                            countDown(longTime);
                                                        }
                                                    }
                                                }
                                            }
                                            if (data.getWorkRuleStatus() > 0) {//作业规则，司机可以扫码作业
                                                ll_scanWork.setVisibility(View.VISIBLE);
                                            } else {
                                                ll_scanWork.setVisibility(View.INVISIBLE);
                                            }
                                        }else {
                                            ll_scanWork.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                } else {
                                    if (data.getWorkRuleStatus() > 0) {//作业规则，司机可以扫码作业
                                        if (reserveStatus == 2 || reserveStatus == 3) {
                                            ll_working_scan.setVisibility(View.VISIBLE);
                                        } else {
                                            ll_working_scan.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private void countDown(long entranceRemindMinute) {
        countDownDialog.show();
        countDownTimer = new CountDownTimer(entranceRemindMinute, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (countDownDialog != null && countDownDialog.isShowing()) {
                    countDownDialog.setTv_count_down(millisUntilFinished);
                }
                if (tv_countDown_text.getVisibility() == View.VISIBLE) {
                    tv_countDown_text.setText("请在指定时间内及时开始进行作业：" + TimeUtil.secondFormat(millisUntilFinished));
                }
            }

            @Override
            public void onFinish() {
                tv_countDown_text.setVisibility(View.GONE);
                if (countDownDialog != null && countDownDialog.isShowing()) {
                    countDownDialog.dismiss();
                }
            }
        };

        countDownTimer.start();
    }

    private boolean isSpeak = true;//播报

    /**
     * 定时任务倒计时
     *
     * @param reserveId
     */
    public void tasksCountDownQueueNum(String reserveId) {
        HttpParams params = new HttpParams();
        params.put("reserveId", reserveId);
        OkGo.<QueryVoLzyResponse<DriverReportBean>>get(BaseUrl.YT_Base + BaseUrl.countDownQueueNum)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<DriverReportBean>>(getActivity(), false) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<DriverReportBean>> response, String desc) {
                        DriverReportBean data = response.body().getData();
                        if (data != null) {
                            String itemDesc = data.getItemDesc();
                            if (StringUtils.isNotBlank(itemDesc)) {
                                tv_tips.setText(itemDesc);
                            }
                            if (StringUtils.isNotBlank(data.getTime())) {
                                tv_reportTime.setText(data.getTime());
                                String queueNum = data.getQueueNum();
                                tv_lineNum.setText(queueNum != null ? queueNum : "1");
                                if (StringUtils.isNotBlank(queueNum)) {
                                    if ("1".equals(queueNum)) {
                                        if (isSpeak) {
                                            SystemTTS instance = SystemTTS.getInstance(getActivity());
                                            instance.playText("可以入场了，请尽快入场，谢谢. 可以入场了，请尽快入场，谢谢. 可以入场了，请尽快入场，谢谢. ");
                                            isSpeak = false;
                                        }
                                        iv_reservationStatus.setImageResource(R.drawable.yt_krc);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 取消预约
     */
    public void cancelReservation() {
        HttpParams params = new HttpParams();
        params.put("reserveId", reserveId);
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
                        getByReserveList();
                        isSpeak = true;
                        tv_countDown_text.setVisibility(View.GONE);
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        getByReserveList();
                        isSpeak = true;
                        tv_countDown_text.setVisibility(View.GONE);
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                });
    }

    /**
     * 无法按时到场
     */
    public void noOnTimeArrive() {
        HttpParams params = new HttpParams();
        params.put("reserveId", reserveId);
        OkGo.<QueryVoLzyResponse<Object>>get(BaseUrl.YT_Base + BaseUrl.noOnTimeArrive)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<Object>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<Object>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<Object>> response, String desc) {
                        getByReserveList();
                    }
                });
    }

    /**
     * 新建多仓预约列表
     */
    private void addMuchWareHouse(String groupNum) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupNum", groupNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<QueryVoLzyResponse<QiyeXinXiReportBean>>post(BaseUrl.YT_Base + BaseUrl.ADDMUCHWAREHOUSERESERVE)
                .upJson(jsonObject)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<QiyeXinXiReportBean>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<QiyeXinXiReportBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);

                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<QiyeXinXiReportBean>> response, String desc) {
                        startActivity(PlatformListActivity.class);
                    }
                });

    }

    /**
     * 开启实时定位
     */
    private Intent intent;
    LocationServiceNew.Binder binder;
    private RequestDialog dialog = null;
    private boolean mIsBound = false;

    public void startLocation() {
        if (dialog == null) {
            dialog = new RequestDialog(getActivity());
        }
        dialog.show();
        intent = new Intent(getActivity(), LocationServiceNew.class);
        getActivity().bindService(intent, this, BIND_AUTO_CREATE);
        mIsBound = true;
    }

    /**
     * 关闭实时定位
     */
    public void stopLocation() {
        try {
            if (intent != null && mIsBound) {
                mIsBound = false;
                getActivity().unbindService(this);
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 不再提示权限时的展示对话框
     */
    Dialog mPermissionDialog;

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(getActivity())
                    .setMessage("请您授权开通APP位置权限，再进行提货送货")
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPermissionDialog.cancel();
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPermissionDialog.cancel();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    /**
     * 手机定位
     */
    private void applyPermission() {
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        String permissionDes = "手机定位";
        CreditPermissionUtil.applyPermission((MainActivity)getActivity(), FactoryApplication.getInstances(), permissions, permissionDes,
                new CreditPermissionUtil.PermissionCallback() {
                    @Override
                    public void onGrant() {
                        if (OnMultiClickUtils.isMultiClickClick(getActivity())) {
                            isBaodao=true;
                            startLocation();
                        }

                    }
                }
        );
    }

    /**
     * 申请相机权限开始ocr扫描
     */
    private void applyCameraPermission() {
        String[] permissions = new String[]{PermissionsUtils.CAMERA,PermissionsUtils.WRITE_EXTERNAL_STORAGE,PermissionsUtils.READ_EXTERNAL_STORAGE};

        String permissionDes = "手机相机";
        CreditPermissionUtil.applyPermission((MainActivity)getActivity(), FactoryApplication.getInstances(), permissions, permissionDes,
                new CreditPermissionUtil.PermissionCallback() {
                    @Override
                    public void onGrant() {
                        Bundle bundleCar = new Bundle();
                        bundleCar.putString("title", "扫描二维码");
                        startActivityForResult(CaptureActivity.class, bundleCar, 0x12);
                    }
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (1 == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                showPermissionDialog();//跳转到系统设置权限页面
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        binder = (LocationServiceNew.Binder) iBinder;
        LocationServiceNew locationService = binder.getService();
        locationService.setCallback(new LocationServiceNew.Callback() {
            @Override
            public void onAddressChange(AddressMessageBean bean) {
                if (bean.getUser_address() != null && isBaodao) {
                    isBaodao = false;
                    locationService.locationStop();
                    stopLocation();
                    driverReport(bean.getUser_lon(), bean.getUser_lat());
                }
            }

            @Override
            public void addressFail(String des) {
                showToast(des);
                locationService.locationStop();
                stopLocation();
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        binder = null;
    }

    @Override
    public void submit() {
        if (dialogStatus == 1) {
            cancelReservation();
        } else if (dialogStatus == 2) {
            Bundle bundle1 = new Bundle();
            bundle1.putString("userType", "2");
            startActivity(InfoAuthActivity.class, bundle1);
        } else if (dialogStatus == 3) {
            Bundle bundle = new Bundle();
            bundle.putString("reserveId", reserveId);
            bundle.putString("warehouseGroupId", warehouseGroupId);
            startActivityForResult(PayOrderActivity.class, bundle, 0x11);
        } else if (dialogStatus == 4) {
            Bundle bundle = new Bundle();
            bundle.putString("reserveId", reserveId);
            startActivity(ParkingPayLoadingActivity.class, bundle);
        }

        dialogStatus = -1;
    }

    // 接收普通事件的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(BaseEventMessage message) {
        if (message.isRefresh()) {
            startLocation();
        }
        if ("payParking".equals(message.getStr())) {
            getByReserveList();
            if (parkingPayDialog != null && parkingPayDialog.isShowing()) {
                parkingPayDialog.dismiss();
            }
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
