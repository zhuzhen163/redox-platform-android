package com.redoxyt.platform.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseEventMessage;
import com.redoxyt.platform.uitl.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import static com.redoxyt.platform.wxapi.WeiXinConstants.APP_ID;

/**
 * Created by zz.
 * description:
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        //最好依赖于商户后台的查询结果
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {//0成功，-1配置信息错误，-2支付取消
            if (resp.errCode == 0){
                EventBus.getDefault().post(new BaseEventMessage("pay"));
            }
            finish();
        }
    }
}
