package com.redoxyt.platform.fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.ParkingPayLoadingActivity;
import com.redoxyt.platform.activity.ParkingPayOrderActivity;
import com.redoxyt.platform.activity.QuestionnaireActivity;
import com.redoxyt.platform.activity.WebViewActivity;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationListBean;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.ParkingPayDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * 司机身份--入场须知
 */

public class AdmissionNoticeFragment extends BaseFragment {

    @BindView(R.id.ll_text)
    LinearLayout ll_text;
    @BindView(R.id.ll_video)
    LinearLayout ll_video;
    @BindView(R.id.ll_questionnaire)
    LinearLayout ll_questionnaire;
    private ParkingPayDialog parkingPayDialog;
    private String reserveId;

    @Override
    protected void initData() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());
//            datePickerDialog.show();
//        }
        parkingPayDialog = new ParkingPayDialog(getActivity());
        parkingPayDialog.setCallBack(() -> {
            Bundle bundle = new Bundle();
            bundle.putString("reserveId",reserveId);
            startActivityForResult(ParkingPayOrderActivity.class,bundle,0x11);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getByReserveList();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_admission_notice;
    }

    @OnClick({R.id.ll_text,R.id.ll_video,R.id.ll_questionnaire})
    public void onViewClicked(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.ll_text:
                if (StringUtils.isNotBlank(ConfigUtils.getWarehouseId())){
                    bundle.putString("mUrl", BaseUrl.YT_BaseH5+"entranceNotice?warehouseId="+ConfigUtils.getWarehouseId());
                    bundle.putString("mTitle","入场须知图文");
                    startActivity(WebViewActivity.class,bundle);
                }else {
                    showToast("请先创建预约");
                }
                break;
            case R.id.ll_video:
                if (StringUtils.isNotBlank(ConfigUtils.getWarehouseId())){
                    bundle.putString("mUrl", BaseUrl.YT_BaseH5+"entranceVideo?warehouseId="+ConfigUtils.getWarehouseId());
                    bundle.putString("mTitle","入场须知视频");
                    startActivity(WebViewActivity.class,bundle);
                }else {
                    showToast("请先创建预约");
                }
                break;
            case R.id.ll_questionnaire:
                if (StringUtils.isNotBlank(ConfigUtils.getReserveCode())){
                    getGroupPlatformOwn();
                }else {
                    showToast("请先创建预约");
                }
                break;
        }
    }

    public void getGroupPlatformOwn() {
        OkGo.<QueryVoLzyResponse<Integer>>get(BaseUrl.YT_Base + BaseUrl.getGroupPlatformOwn)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<Integer>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<Integer>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<Integer>> response, String desc) {
                        int data = response.body().getData();
                        if (data == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putString("reserveCode",ConfigUtils.getReserveCode());
                            startActivity(QuestionnaireActivity.class,bundle);
                        }else {
                            showToast("入场检查未开启");
                        }
                    }
                });
    }

    /**
     * 查询预约信息
     */
    public void getByReserveList() {
        HttpParams params = new HttpParams();
        params.put("driverId",ConfigUtils.getUserId());
        params.put("finished",0);
        params.put("sortType",1);
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
                            if (reservationList.size()>0){
                                ReservationBean bean = reservationList.get(0);
                                ConfigUtils.saveReserveCode(bean.getReserveCode());
                                ConfigUtils.saveWarehouseId(bean.getWarehouseId());
                                int reserveStatus = bean.getReserveStatus();
                                reserveId = bean.getReserveId();
                                if (reserveStatus == 4) {//已完成
                                    if (bean.getParkPayStatus() == 1) {//停车费待支付
                                        if (parkingPayDialog != null && !parkingPayDialog.isShowing()) {
                                            parkingPayDialog.setContent("您已完成" + bean.getReserveTypeName() + "操作，您停车时长为" + bean.getParkingMinutes() + "分钟,请缴纳停车费", bean.getParkPayAmount() + "");
                                        }
                                    }else {
                                        if (parkingPayDialog != null && parkingPayDialog.isShowing()){
                                            parkingPayDialog.dismiss();
                                        }
                                    }
                                }
                            }else {
                                if (parkingPayDialog != null && parkingPayDialog.isShowing()){
                                    parkingPayDialog.dismiss();
                                }
                            }
                        }
                    }
                });
    }
}
