package com.redoxyt.platform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.activity.ComplaintActivity;
import com.redoxyt.platform.activity.PayLoadingActivity;
import com.redoxyt.platform.activity.PlatformListActivity;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.DriverReportBean;
import com.redoxyt.platform.bean.QiyeXinXiReportBean;
import com.redoxyt.platform.qr.CaptureActivity;
import com.redoxyt.platform.uitl.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.PermissionsUtils;
import utils.ConfigUtils;

public class OrderWareHouseByQiyehaoFragment extends BaseFragment {
    @BindView(R.id.et_qiyema)
    EditText etQiyema;
    @BindView(R.id.tv_yonghuming)
    TextView tvYonghuming;
    @BindView(R.id.bt_qiyema_chaxun)
    Button btQiyemaChaxun;
    private boolean isQrScan;

    public OrderWareHouseByQiyehaoFragment() {
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_orderwarehousebyqiyehao;
    }

    @Override
    protected void initData() {
        etQiyema.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etQiyema.getText().toString().length() == 6) {
                    getCompanyByCode();
                }
            }
        });

    }


    @OnClick(R.id.bt_qiyema_chaxun)
    public void onClick() {
        addMuchWareHouse();
    }

    public void setQrView(String groupNum) {
        etQiyema.setText(groupNum);
        addMuchWareHouse();
    }


    /**
     * 新建多仓预约列表
     */
    private void addMuchWareHouse() {
        if (etQiyema.getText().toString().trim() == null || etQiyema.getText().toString().trim().equals("")) {
            showToast("请输入企业号或扫描企业二维码");
            return;
        }
      /*  HttpParams params = new HttpParams();
        params.put("groupNum", etQiyema.getText().toString().trim());*/
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("groupNum",etQiyema.getText().toString().trim());
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
     * 根据企业号查询企业信息
     */
    private void getCompanyByCode() {
        HttpParams params = new HttpParams();
        params.put("groupNum", etQiyema.getText().toString().trim());
        OkGo.<QueryVoLzyResponse<QiyeXinXiReportBean>>get(BaseUrl.YT_Base + BaseUrl.GETGROUPBYGROUPNUM)
                .params(params)
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
                        QiyeXinXiReportBean data = response.body().getData();
                        tvYonghuming.setText(data.getGroupCompany());
                    }
                });

    }
}
