package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.base.BaseEventMessage;
import com.redoxyt.platform.bean.PayBean;
import com.redoxyt.platform.bean.PaySubmitBean;
import com.redoxyt.platform.uitl.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;

import static com.redoxyt.platform.wxapi.WeiXinConstants.APP_ID;

/**
 * Created by zz.
 * description:
 */

public class PayOrderActivity extends BaseActivity {

    @BindView(R.id.cb_wxPay)
    CheckBox cb_wxPay;
    @BindView(R.id.cb_zfbPay)
    CheckBox cb_zfbPay;
    @BindView(R.id.cb_qbPay)
    CheckBox cb_qbPay;

    @BindView(R.id.ll_wxPay)
    LinearLayout ll_wxPay;
    @BindView(R.id.ll_zfbPay)
    LinearLayout ll_zfbPay;
    @BindView(R.id.ll_qbPay)
    LinearLayout ll_qbPay;

    @BindView(R.id.tv_pay)
    TextView tv_pay;

    private int payCode = 1;//1微信，2支付宝，3钱包
    private String reserveId;

    @Override
    public int setView() {
        return R.layout.acitivty_pay_order;
    }

    @Override
    public void initData() {
        reserveId = getIntent().getStringExtra("reserveId");
        String warehouseGroupId = getIntent().getStringExtra("warehouseGroupId");
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payCode == 1){
                    dounifiedOrder(reserveId,warehouseGroupId);
                }
            }
        });
    }

    @OnClick({R.id.ll_wxPay,R.id.ll_zfbPay,R.id.ll_qbPay})
    public void onViewClicked(View view){
        cb_wxPay.setChecked(false);
        cb_zfbPay.setChecked(false);
        cb_qbPay.setChecked(false);
        switch (view.getId()){
            case R.id.ll_wxPay:
                payCode = 1;
                cb_wxPay.setChecked(true);
                break;
            case R.id.ll_zfbPay:
                payCode = 2;
                cb_zfbPay.setChecked(true);
                break;
            case R.id.ll_qbPay:
                payCode = 3;
                cb_qbPay.setChecked(true);
                break;
        }
    }

    /**
     * 支付统一下单接口
     */
    public void dounifiedOrder(String reserveId,String warehouseGroupId) {
        final IWXAPI wxapi = WXAPIFactory.createWXAPI(this, APP_ID,false);
        tv_pay.setClickable(false);

        HttpParams params = new HttpParams();
        params.put("reserveId",reserveId);
        params.put("warehouseGroupId",warehouseGroupId);
        OkGo.<QueryVoLzyResponse<PayBean>>post(BaseUrl.YT_Base + BaseUrl.platformPay)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<PayBean>>(PayOrderActivity.this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        showToast(desc);
                        tv_pay.setClickable(true);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                        tv_pay.setClickable(false);
                    }

                    @Override
                    public void onSuccessNotData(com.lzy.okgo.model.Response<QueryVoLzyResponse<PayBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        tv_pay.setClickable(false);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<QueryVoLzyResponse<PayBean>> response, String desc) {
                        PayBean data = response.body().getData();
                        if (data != null){
                            PayReq req = new PayReq();
                            req.appId = data.getAppid();
                            req.partnerId = data.getPartnerid();
                            req.prepayId = data.getPrepayid();
                            req.packageValue = data.getPackageValue();
                            req.nonceStr = data.getNoncestr();
                            req.timeStamp = data.getTimestamp();
                            req.extData = data.getExtdata();
                            req.sign = data.getSign();
                            wxapi.sendReq(req);
                        }else {
                            ToastUtil.showToast("数据出错");
                        }
                        tv_pay.setClickable(true);
                    }
                });
    }

    public void wechatPayRecord() {
        PaySubmitBean bean = new PaySubmitBean();
        bean.setBusinessId(reserveId);
        bean.setAttachType("platform_pay");
        String detail = new Gson().toJson(bean);
        OkGo.<QueryVoLzyResponse<String>>post(BaseUrl.YT_Base + BaseUrl.wechatPayRecord)
                .upJson(detail)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(PayOrderActivity.this, true) {
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
                    public void onSuccessNotData(com.lzy.okgo.model.Response<QueryVoLzyResponse<String>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        Bundle bundle = new Bundle();
                        bundle.putString("reserveId",reserveId);
                        startActivity(PayLoadingActivity.class,bundle);
                        finish();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<QueryVoLzyResponse<String>> response, String desc) {
                        Bundle bundle = new Bundle();
                        bundle.putString("reserveId",reserveId);
                        startActivity(PayLoadingActivity.class,bundle);
                        finish();
                    }
                });
    }

    // 接收普通事件的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(BaseEventMessage message) {
        if ("pay".equals(message.getStr())) {
            wechatPayRecord();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
