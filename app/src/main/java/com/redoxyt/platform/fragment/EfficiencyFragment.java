package com.redoxyt.platform.fragment;


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.BillboardCakeBean;
import com.redoxyt.platform.bean.EfficiencyBean;
import com.redoxyt.platform.bean.ManagementBillboardBean;
import com.redoxyt.platform.bean.WarehouseBean;
import com.redoxyt.platform.bean.YTListBean;
import com.redoxyt.platform.uitl.X5WebView;
import com.redoxyt.platform.widget.SwipeRefreshLayouts;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import util.StatusBarUtil;

/**
 * 效率看板
 */
public class EfficiencyFragment extends BaseFragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayouts swipeRefreshLayout;
    @BindView(R.id.tv_week)
    TextView tv_week;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_loadDay)
    TextView tv_loadDay;
    @BindView(R.id.tv_loadAverage)
    TextView tv_loadAverage;
    @BindView(R.id.tv_loadRate)
    TextView tv_loadRate;
    @BindView(R.id.tv_unLoadDay)
    TextView tv_unLoadDay;
    @BindView(R.id.tv_unLoadAverage)
    TextView tv_unLoadAverage;
    @BindView(R.id.tv_unLoadRate)
    TextView tv_unLoadRate;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.ll_warehouse)
    LinearLayout ll_warehouse;
    @BindView(R.id.ll_platform)
    LinearLayout ll_platform;
    @BindView(R.id.tv_platformName)
    TextView tv_platformName;
    @BindView(R.id.fl_operationTimeLine)
    FrameLayout fl_operationTimeLine;
    private X5WebView web_load;
    @BindView(R.id.fl_cargoWorkLine)
    FrameLayout fl_cargoWorkLine;
    private X5WebView web_cargo;
    private int dateType = 1;//日期类型，1:本周;2:本月;3:本年
    private String warehouseId,platformId;
    private String warehouseName,platformName;
    private String billBoard;

    private List<WarehouseBean> warehouseData = new ArrayList<>();
    private List<YTListBean> platformData = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    protected void initData() {
        web_load = new X5WebView(getActivity(),null);
        fl_operationTimeLine.addView(web_load, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        web_load.setWebViewClient(new LineWebViewClient());

        web_cargo = new X5WebView(getActivity(),null);
        fl_cargoWorkLine.addView(web_cargo, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        web_cargo.setWebViewClient(new CargoWebViewClient());

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            managementEfficiencyboard(dateType,false);
            swipeRefreshLayout.setRefreshing(false);
        });
        listWarehouse(true);

    }

    @OnClick({R.id.tv_week,R.id.tv_month,R.id.tv_year,R.id.ll_warehouse,R.id.ll_platform})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.tv_week:
                setTabLayout(R.id.tv_week);
                break;
            case R.id.tv_month:
                setTabLayout(R.id.tv_month);
                break;
            case R.id.tv_year:
                setTabLayout(R.id.tv_year);
                break;
            case R.id.ll_warehouse:
                listWarehouse(false);
                break;
            case R.id.ll_platform:
                getPlatformLists(warehouseId);
                break;
        }
    }

    public class LineWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String s1 = "operationTimeLine";
            web_load.loadUrl("javascript:renderFun('" + s1 + "','" + billBoard + "','"+ dateType +"')");
        }

    }

    public class CargoWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String s1 = "cargoWorkLine";
            web_cargo.loadUrl("javascript:renderFun('" + s1 + "','" + billBoard + "','"+ dateType +"')");
        }
    }

    /**
     * 查询仓库
     * @param isFirst
     */
    public void listWarehouse(boolean isFirst) {
        OkGo.<QueryVoLzyResponse<List<WarehouseBean>>>get(BaseUrl.YT_Base + BaseUrl.listWarehouse)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<WarehouseBean>>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<WarehouseBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<WarehouseBean>>> response, String desc) {
                        warehouseData = response.body().getData();
                        if (warehouseData != null && warehouseData.size()>0) {
                            warehouseList.clear();
                            for (int i = 0; i < warehouseData.size(); i++) {
                                WarehouseBean warehouseBean = warehouseData.get(i);
                                warehouseList.add(warehouseBean.getPlatformWarehouseName());
                            }
                            if (isFirst){
                                WarehouseBean warehouseBean = warehouseData.get(0);
                                warehouseName = warehouseBean.getPlatformWarehouseName();
                                tv_warehouseName.setText(warehouseName);
                                warehouseId = warehouseBean.getWarehouseId();
                                managementEfficiencyboard(dateType,true);
                            }else {
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWarehousePicker();
                                    }
                                },200);
                            }
                        }
                    }
                });
    }

    private List<String> warehouseList = new ArrayList<>();
    private OptionsPickerView typeOptions;

    private void initWarehousePicker() {
        if (typeOptions == null){
            typeOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    warehouseName = warehouseList.get(options1);
                    tv_warehouseName.setText(warehouseName);
                    warehouseId = warehouseData.get(options1).getWarehouseId();
                    tv_platformName.setText("请选择");
                    platformId = "";
                    managementEfficiencyboard(dateType,false);
                }
            })
                    .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                        @Override
                        public void customLayout(View v) {
                            final TextView tvSubmit = v.findViewById(R.id.tv_confirm);
                            TextView ivCancel = v.findViewById(R.id.tv_cancel);
                            tvSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeOptions.returnData();
                                    typeOptions.dismiss();
                                }
                            });

                            ivCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeOptions.dismiss();
                                }
                            });
                        }
                    })
                    .isDialog(false)
                    .setOutSideCancelable(false)
                    .build();
        }

        typeOptions.setPicker(warehouseList);
        typeOptions.show();
    }

    /**
     * 查询月台
     */
    public void getPlatformLists(String warehouseId) {
        HttpParams params = new HttpParams();
        params.put("warehouseId",warehouseId);
        params.put("ruleTakeState","3");
        OkGo.<QueryVoLzyResponse<List<YTListBean>>>get(BaseUrl.YT_Base + BaseUrl.getPlatformLists)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<YTListBean>>>(getActivity(), true) {
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
                        platformData = response.body().getData();
                        if (platformData != null && platformData.size()>0) {
                            platformList.clear();
                            for (int i = 0; i < platformData.size(); i++) {
                                YTListBean bean = platformData.get(i);
                                platformList.add(bean.getPlatformName());
                            }
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initPlatformPicker();
                                }
                            },200);
                        }
                    }
                });
    }

    private List<String> platformList = new ArrayList<>();
    private OptionsPickerView platformOptions;

    private void initPlatformPicker() {
        if (platformOptions == null){
            platformOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    platformName = platformList.get(options1);
                    tv_platformName.setText(platformName);
                    platformId = platformData.get(options1).getPlatformId();
                    managementEfficiencyboard(dateType,false);
                }
            })
                    .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                        @Override
                        public void customLayout(View v) {
                            final TextView tvSubmit = v.findViewById(R.id.tv_confirm);
                            TextView ivCancel = v.findViewById(R.id.tv_cancel);
                            tvSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    platformOptions.returnData();
                                    platformOptions.dismiss();
                                }
                            });

                            ivCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    platformOptions.dismiss();
                                }
                            });
                        }
                    })
                    .isDialog(false)
                    .setOutSideCancelable(false)
                    .build();
        }
        if (platformList.size()>0){
            platformOptions.setPicker(platformList);
            platformOptions.show();
        }
    }

    /**
     * 效率看板
     */
    public void managementEfficiencyboard(int dateType,boolean isFirst) {
        HttpParams params = new HttpParams();
        params.put("warehouseId",warehouseId);
        params.put("platformId",platformId);
        params.put("dateType",dateType);
        OkGo.<QueryVoLzyResponse<EfficiencyBean>>get(BaseUrl.YT_Base + BaseUrl.managementEfficiencyboard)
                .params(params)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<EfficiencyBean>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<EfficiencyBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<EfficiencyBean>> response, String desc) {
                        EfficiencyBean bean = response.body().getData();
                        if (bean != null){
                            tv_loadDay.setText(bean.getStowageVelocity()+"分钟");
                            tv_loadAverage.setText(bean.getAccumulateStowageVelocity()+"分钟");
                            tv_loadRate.setText(bean.getStowageVelocityRate());

                            tv_unLoadDay.setText(bean.getUnloadVelocity()+"分钟");
                            tv_unLoadAverage.setText(bean.getAccumulateUnloadVelocity()+"分钟");
                            tv_unLoadRate.setText(bean.getUnloadVelocityRate());

                            billBoard = JSONObject.toJSONString(bean);
                            if (isFirst){
                                web_load.loadUrl(BaseUrl.YT_CHART+"?type=operationTimeLine&width=100%25&height=220px");
                                web_cargo.loadUrl(BaseUrl.YT_CHART+"?type=cargoWorkLine&width=100%25&height=220px");
                            }else {
                                web_load.loadUrl("javascript:renderFun('" + "operationTimeLine" + "','" + billBoard + "','"+ dateType +"')");
                                web_cargo.loadUrl("javascript:renderFun('" + "cargoWorkLine" + "','" + billBoard + "','"+ dateType +"')");
                            }
                        }
                    }
                });
    }

    @Override
    protected void setStatusBar() {
       StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 100, null);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_efficiency;
    }

    private void setTabLayout(int view){
        tv_week.setTextColor(getResources().getColor(R.color.black_666));
        tv_week.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tv_month.setTextColor(getResources().getColor(R.color.black_666));
        tv_month.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tv_year.setTextColor(getResources().getColor(R.color.black_666));
        tv_year.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        Drawable bottomDrawable = getResources().getDrawable(R.drawable.yt_tab_zsx);
        bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
        tv_week.setCompoundDrawables(null, null, null, null);
        tv_month.setCompoundDrawables(null, null, null, null);
        tv_year.setCompoundDrawables(null, null, null, null);
        if (view == R.id.tv_week){
            dateType = 1;
            tv_week.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_week.setTextColor(getResources().getColor(R.color.black_333));
            tv_week.setCompoundDrawables(null, null, null, bottomDrawable);
        }else if (view == R.id.tv_month){
            dateType = 2;
            tv_month.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_month.setTextColor(getResources().getColor(R.color.black_333));
            tv_month.setCompoundDrawables(null, null, null, bottomDrawable);
        }else if (view == R.id.tv_year){
            dateType = 3;
            tv_year.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_year.setTextColor(getResources().getColor(R.color.black_333));
            tv_year.setCompoundDrawables(null, null, null, bottomDrawable);
        }
        managementEfficiencyboard(dateType,false);
    }
}
