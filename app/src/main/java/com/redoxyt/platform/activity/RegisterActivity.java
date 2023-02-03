package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.UserBean;
import com.redoxyt.platform.uitl.StringUtils;
import com.redoxyt.platform.widget.CreateFleetDialog;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;
import utils.TextUtil;

/**
 * Created by zz.
 * description:
 */

public class RegisterActivity extends BaseActivity{

    @BindView(R.id.et_inputPhone)
    EditText et_inputPhone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_getCode)
    TextView tv_getCode;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password_confirm)
    EditText et_password_confirm;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_register_fleet)
    TextView tv_register_fleet;
    @BindView(R.id.cb_confirm)
    CheckBox cb_confirm;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    private boolean checked = false;
    private boolean isVerify;

    @Override
    public int setView() {
        return R.layout.acitivty_register;
    }

    @Override
    public void initData() {
        cb_confirm.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                checked = true;
            }else {
                checked = false;
            }
        });
    }

    @OnClick({R.id.tv_getCode,R.id.tv_register,R.id.tv_confirm,R.id.tv_privacy,R.id.tv_register_fleet})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_getCode:
                if (isVerify){
                    showToast("验证码已发送");
                    return;
                }
                if (TextUtil.isEtNull(et_inputPhone,"请输入手机号码")) return;
                    String phone = et_inputPhone.getText().toString();
                    if (TextUtil.isPhone(phone)){
                        getCode(phone);
                    }
                break;
            case R.id.tv_register:
                register(0);
                break;
            case R.id.tv_confirm:
                Bundle bundle = new Bundle();
                bundle.putString("mUrl",BaseUrl.YT_BaseH5+"apptraderules");
                bundle.putString("mTitle","交易规则");
                startActivity(WebViewActivity.class,bundle);
                break;
            case R.id.tv_privacy:
                Bundle bun = new Bundle();
                bun.putString("mUrl",BaseUrl.PRIVATE_URL);
                bun.putString("mTitle","隐私政策");
                startActivity(WebViewActivity.class,bun);
                break;
            case R.id.tv_register_fleet:
                register(1);
                break;
        }
    }

    /**
     *
     * @param flag 0司机，1车队
     */
    public void register(int flag){
        String code = et_code.getText().toString().trim();
        if (StringUtils.isBlank(code)){
            showToast("请输入验证码");
            return;
        }
        String password = et_password.getText().toString().trim();
        if (StringUtils.isBlank(password)){
            showToast("请输入密码");
            return;
        }
        String passwordConfirm = et_password_confirm.getText().toString().trim();
        if (StringUtils.isBlank(passwordConfirm)){
            showToast("请确认密码");
            return;
        }
        if (!checked){
            showToast("请同意平台交易规则");
            return;
        }
        if (password.equals(passwordConfirm)){
            //提交注册
            userRegister(et_inputPhone.getText().toString(),code,passwordConfirm,flag);
        }else {
            showToast("两次密码不一致");
        }
    }

    private void userRegister(String phone, String code, String passwordConfirm, int flag){
        HttpParams httpParams = new HttpParams();
        httpParams.put("userName", "");
        httpParams.put("userMobile", phone);
        httpParams.put("userLoginPwd", passwordConfirm);
        httpParams.put("smsCode", code);

        OkGo.<QueryVoLzyResponse<UserBean>>post(BaseUrl.YT_Base+BaseUrl.userRegister)
                .params(httpParams)
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
                            ConfigUtils.saveToken(data.getAccess_token());
                            ConfigUtils.saveRefreshToken(data.getRefresh_token());
                            ConfigUtils.saveUserId(data.getUserId()+"");
                            ConfigUtils.saveGroupId(data.getGroupId()+"");
                            ConfigUtils.saveUserName(data.getRealName());
                            int userFlag = data.getUserFlag();
                            ConfigUtils.saveUserFlag(userFlag);
                            if (flag == 1){//注册为车队
                                UserBean bean = new UserBean();
                                bean.setUserMobile(et_inputPhone.getText().toString().trim());
                                bean.setSmsCode(et_code.getText().toString().trim());
                                bean.setUserLoginPwd(et_password_confirm.getText().toString().trim());
                                bean.setUserId(data.getUserId());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("userBean",bean);
                                bundle.putString("userType","2");
                                startActivity(FleetRegisterActivity.class, bundle);
                            }else {
                                Bundle bundle = new Bundle();
                                bundle.putString("userType","2");
                                startActivity(InfoAuthActivity.class,bundle);
                            }
                        }
                    }
                });
    }

    private void getCode(String phone){
        HttpParams httpParams = new HttpParams();
        httpParams.put("mobile", phone);

        OkGo.<QueryVoLzyResponse<String>>get(BaseUrl.YT_Base+BaseUrl.getCode)
                .params(httpParams)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<String>>(this, true) {
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
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<String>> response, String desc) {
                        timerRegister.start();
                    }
                });
    }

    //倒计时功能
    CountDownTimer timerRegister = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            isVerify = true;
            tv_getCode.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            isVerify = false;
            tv_getCode.setText("发送验证码");
            timerRegister.cancel();
        }
    };

    @Override
    protected void onDestroy() {
        timerRegister.cancel();
        super.onDestroy();
    }
}
