package com.redoxyt.platform.fragment;


import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.MainDriverActivity;
import com.redoxyt.platform.activity.OrderPlanWarnActivity;
import com.redoxyt.platform.activity.SplashActivity;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.AiBuildResetBean;
import com.redoxyt.platform.bean.ReservationBean;
import com.redoxyt.platform.bean.ReservationCode;
import com.redoxyt.platform.bean.ReservationListBean;
import com.redoxyt.platform.qr.QRCodeUtil;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

/**
 * 司机身份--预约码
 */

public class ReservationCodeFragment extends BaseFragment {

    @BindView(R.id.ll_qrNull)
    LinearLayout ll_qrNull;
    @BindView(R.id.ivCusQr)
    ImageView ivCusQr;
    @BindView(R.id.cv_qrCode)
    CardView cv_qrCode;
    @BindView(R.id.tv_carCode)
    TextView tv_carCode;
    @BindView(R.id.tv_reserveCode)
    TextView tv_reserveCode;
    private ReservationCode reservationCode = new ReservationCode();

    private Bitmap bitmap;

    @Override
    public void initData() {
        getByReservationList();
        getByReserveList();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_reservation_code;
    }

    /**
     * 查询预约信息
     */
    public void getByReservationList() {
        HttpParams params = new HttpParams();
        params.put("driverId", ConfigUtils.getUserId());
        params.put("sortType", 3);
        params.put("lastFinished", 1);
        params.put("finished", 0);
        params.put("reserveStatusList", "2,3,4");
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
                            if (data.getReservation() != null) {
                                setImage(data.getReservation(), true);
                            } else {
                                List<ReservationBean> reservationList = data.getReservationList();
                                if (reservationList.size() > 0) {
                                    ReservationBean bean = reservationList.get(0);
                                    setImage(bean, false);
                                } else {
                                    ll_qrNull.setVisibility(View.VISIBLE);
                                    cv_qrCode.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
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
                            if (reservationList != null && reservationList.size() > 0) {
                            }else {
                                boolean isAiBuid = getActivity().getIntent().getBooleanExtra("isAiBuid", false);
                                if (!isAiBuid) {
                                    ((MainDriverActivity) getActivity()).remainMuchWarehouse();
                                }
                            }
                        }
                    }
                });
    }

    private void setImage(ReservationBean bean, boolean isGreen) {
        ll_qrNull.setVisibility(View.GONE);
        cv_qrCode.setVisibility(View.VISIBLE);
        String reserveCode = bean.getReserveCode();
        tv_carCode.setText("车牌号：" + bean.getCarCode());
        tv_reserveCode.setText("预约码：" + reserveCode);
        ivCusQr.post(() -> {
            try {
                reservationCode.setCode(reserveCode);
                String s = new Gson().toJson(reservationCode);
                if (isGreen) {
                    bitmap = QRCodeUtil.qr_code(s, 0xff1ac47c);//绿码
                } else {
                    bitmap = QRCodeUtil.qr_code(s, 0xff000000);
                }
                ivCusQr.setImageBitmap(bitmap);
            } catch (Exception e) {
                startActivity(SplashActivity.class);
                e.printStackTrace();
            }
        });
    }
}
