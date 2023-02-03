package com.redoxyt.platform.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.FactoryApplication;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.MainActivity;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.qr.CaptureActivity;
import com.redoxyt.platform.uitl.CreditPermissionUtil;
import com.redoxyt.platform.uitl.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.PermissionsUtils;
import widget.CommonToolbar;

/**
 * 库管员--预约执行
 */

public class ReservationCarryFragment extends BaseFragment {

    @BindView(R.id.ctb_title)
    CommonToolbar ctb_title;
    @BindView(R.id.ll_carry)
    LinearLayout ll_carry;
    @BindView(R.id.iv_scan)
    ImageView iv_scan;
    @BindView(R.id.et_input_reservationCode)
    EditText et_input_reservationCode;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.cv_carry)
    CardView cv_carry;
    @BindView(R.id.tv_carCode)
    TextView tv_carCode;
    @BindView(R.id.tv_reserveCode)
    TextView tv_reserveCode;
    @BindView(R.id.tv_transportCode)
    TextView tv_transportCode;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.tv_platformId)
    TextView tv_platformId;
    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_start_complete)
    TextView tv_start_complete;

    private String reserveId;
    private int reserveStatus;


    @Override
    protected void initData() {
        ctb_title.getGoBack().setVisibility(View.INVISIBLE);
        ctb_title.getGoBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_carry.setVisibility(View.VISIBLE);
                cv_carry.setVisibility(View.GONE);
                ctb_title.getGoBack().setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_reservation_carry;
    }

    @OnClick({R.id.tv_submit,R.id.tv_start_complete,R.id.iv_scan})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_submit:
                String code = et_input_reservationCode.getText().toString().trim();
                if (StringUtils.isNotBlank(code)){
                    getByReserveList(code);
                }else {
                    showToast("请输入预约码");
                }
                break;
            case R.id.tv_start_complete:
                executeJob(reserveId);
                break;
            case R.id.iv_scan:
                applyCameraPermission();
                break;
        }
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
                        bundleCar.putString("title", "扫描司机预约码");
                        startActivityForResult(CaptureActivity.class, bundleCar, 10001);
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 10001) {
                String scan = data.getExtras().getString("uuid").toString();
                try {
                    HashMap<String, String> car = new Gson().fromJson(scan, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    if (StringUtils.isNotBlank(car.get("code"))){
                        String code = car.get("code");
                        et_input_reservationCode.setText(code);
                        getByReserveList(code);
                    }else {
                        showToast("此二维码无效");
                    }
                }catch (Exception e){
                    showToast("此二维码无效");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询预约信息
     * @param reserveCode
     * @param
     */
    public void getByReserveList(String reserveCode) {
        HttpParams params = new HttpParams();
        params.put("reserveCode",reserveCode);
        OkGo.<QueryVoLzyResponse<ReservationBean>>get(BaseUrl.YT_Base + BaseUrl.getByReserveCode)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationBean>>(getActivity(), true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        et_input_reservationCode.setText("");
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
                        ReservationBean reservationBean = response.body().getData();
                        if (reservationBean != null){
                            ll_carry.setVisibility(View.GONE);
                            cv_carry.setVisibility(View.VISIBLE);
                            ctb_title.getGoBack().setVisibility(View.VISIBLE);
                            tv_carCode.setText(reservationBean.getCarCode());
                            reserveId = reservationBean.getReserveId();
                            tv_reserveCode.setText("预约码："+reservationBean.getReserveCode());
                            tv_transportCode.setText("运单号："+reservationBean.getTransportCode());
                            tv_warehouseName.setText("货主："+reservationBean.getWarehouseName());
                            tv_platformId.setText("月台名称："+reservationBean.getPlatformName());
                            reserveStatus = reservationBean.getReserveStatus();
                            if (reserveStatus == 3){
                                tv_start_complete.setText("完成作业");
                                tv_startTime.setVisibility(View.VISIBLE);
                                tv_startTime.setText("开始时间："+reservationBean.getWorkStartDatetime());
                            }else if (reserveStatus == 2){
                                tv_startTime.setVisibility(View.GONE);
                                tv_start_complete.setText("开始作业");
                            }else {
                                et_input_reservationCode.setText("");
                                ll_carry.setVisibility(View.VISIBLE);
                                cv_carry.setVisibility(View.GONE);
                                ctb_title.getGoBack().setVisibility(View.INVISIBLE);
                            }
                        }else {
                            et_input_reservationCode.setText("");
                            ll_carry.setVisibility(View.VISIBLE);
                            cv_carry.setVisibility(View.GONE);
                            ctb_title.getGoBack().setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    /**
     * 开始作业
     * @param reserveId
     */
    public void executeJob(String reserveId) {
        HttpParams params = new HttpParams();
        params.put("reserveId",reserveId);
        OkGo.<QueryVoLzyResponse<ReservationBean>>post(BaseUrl.YT_Base + BaseUrl.executeJob)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ReservationBean>>(getActivity(), true) {
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
                        ReservationBean data = response.body().getData();
                        if (data != null){
                            if (reserveStatus == 3){//完成作业返回扫码状态
                                et_input_reservationCode.setText("");
                                ll_carry.setVisibility(View.VISIBLE);
                                cv_carry.setVisibility(View.GONE);
                                ctb_title.getGoBack().setVisibility(View.INVISIBLE);
                            }else {
                                getByReserveList(et_input_reservationCode.getText().toString().trim());
                            }
                        }
                    }
                });
    }
}
