package com.redoxyt.platform.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.base.BaseEventMessage;
import com.redoxyt.platform.bean.PayBean;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;

/**
 * Created by zz.
 * description:
 */

public class PayLoadingActivity extends BaseActivity{

    @BindView(R.id.iv_loading)
    ImageView iv_loading;
    private String reserveId;

    private BuildHandle handler = new BuildHandle(this);

    private static class BuildHandle extends Handler {
        private WeakReference<PayLoadingActivity> reference;//弱引用
        public BuildHandle(PayLoadingActivity context) {
            reference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayLoadingActivity activity = reference.get();
            activity.wechatPayRecordPolling();
        }
    }

    @Override
    public int setView() {
        return R.layout.acitivty_loading;
    }

    @Override
    public void initData() {
        reserveId = getIntent().getStringExtra("reserveId");
        Glide.with(PayLoadingActivity.this).asGif().load(R.drawable.yt_pay_loading).into(iv_loading);
        handler.sendEmptyMessageDelayed(0,3000);
    }

    public void wechatPayRecordPolling() {
        HttpParams params = new HttpParams();
        params.put("businessId",reserveId);
        params.put("attachType","platform_pay");
        OkGo.<QueryVoLzyResponse<PayBean>>get(BaseUrl.YT_Base + BaseUrl.wechatPayRecordPolling)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<PayBean>>(PayLoadingActivity.this, false) {
                    @Override
                    public void onFail(int code, String desc) {
                        super.onFail(code, desc);
                        finish();
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        finish();
                    }

                    @Override
                    public void onSuccessNotData(com.lzy.okgo.model.Response<QueryVoLzyResponse<PayBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        finish();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<QueryVoLzyResponse<PayBean>> response, String desc) {
                        PayBean data = response.body().getData();
                        if (data.getPayStatus() == 2){
                            EventBus.getDefault().post(new BaseEventMessage(true));
                        }
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
    }
}
