package com.redoxyt.platform.fragment;


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseFragment;
import com.redoxyt.platform.bean.BillboardCakeBean;
import com.redoxyt.platform.bean.ManagementBillboardBean;
import com.redoxyt.platform.bean.ReservationHistoryBean;
import com.redoxyt.platform.bean.WarehouseBean;
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
 * 看板
 */
public class KanBanFragment extends BaseFragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayouts swipeRefreshLayout;
    @BindView(R.id.tv_week)
    TextView tv_week;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_rushNumber)
    TextView tv_rushNumber;
    @BindView(R.id.tv_finishedPercent)
    TextView tv_finishedPercent;
    @BindView(R.id.fl_line)
    FrameLayout fl_line;
    private X5WebView web_line;
    @BindView(R.id.fl_column)
    FrameLayout fl_column;
    private X5WebView web_column;
    @BindView(R.id.fl_cake)
    FrameLayout fl_cake;
    private X5WebView web_cake;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tv_noData)
    TextView tv_noData;
    private int dateType = 1;//日期类型，1:本周;2:本月;3:本年
    private String billBoard;
    private String billBoardCage;
    private String warehouseId;

    ArrayList<String> tabList = new ArrayList<>();
    List<WarehouseBean> list = new ArrayList<>();

    @Override
    protected void initData() {
        web_line = new X5WebView(getActivity(),null);
        fl_line.addView(web_line, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        web_line.setWebViewClient(new LineWebViewClient());

        web_column = new X5WebView(getActivity(),null);
        fl_column.addView(web_column, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        web_column.setWebViewClient(new ColumnWebViewClient());

        web_cake = new X5WebView(getActivity(),null);
        fl_cake.addView(web_cake, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        web_cake.setWebViewClient(new CakeWebViewClient());

        managementBillboard(dateType,true);
        listWarehouse();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                WarehouseBean warehouseBean = list.get(tab.getPosition());
                warehouseId = warehouseBean.getWarehouseId();
                warehouseBillboard(warehouseId,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                updateTabTextView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_theme));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            managementBillboard(dateType,false);
            listWarehouse();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @OnClick({R.id.tv_week,R.id.tv_month,R.id.tv_year})
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
            String s1 = "line";
            web_line.loadUrl("javascript:renderFun('" + s1 + "','" + billBoard + "','"+ dateType +"')");
        }

    }
    public class ColumnWebViewClient extends WebViewClient {
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
            String s1 = "bar";
            web_column.loadUrl("javascript:renderFun('" + s1 + "','" + billBoard + "','"+ dateType +"')");
        }

    }
    public class CakeWebViewClient extends WebViewClient {
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
            String s1 = "pie";
            web_cake.loadUrl("javascript:renderFun('" + s1 + "','" + billBoardCage + "','"+ dateType +"')");
        }

    }

    public void refreshData(){
        managementBillboard(dateType,false);
        listWarehouse();
    }

    @Override
    protected void setStatusBar() {
       StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), 100, null);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_kanban;
    }

    //看板
    public void managementBillboard(int dateType,boolean isFirst) {
        OkGo.<QueryVoLzyResponse<ManagementBillboardBean>>get(BaseUrl.YT_Base + BaseUrl.managementBillboard)
                .params("dateType",dateType)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<ManagementBillboardBean>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<ManagementBillboardBean>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<ManagementBillboardBean>> response, String desc) {
                        ManagementBillboardBean data = response.body().getData();
                        if (data != null) {
                            tv_total.setText(data.getReservationHistoryNumber());
                            tv_rushNumber.setText(data.getUrgentReservationHistoryNumber());
                            tv_finishedPercent.setText(data.getAchievementRateHistoryNumber()+"%");
                            billBoard = JSONObject.toJSONString(data);
                            if (isFirst){
                                web_line.loadUrl(BaseUrl.YT_CHART+"?type=line&width=100%25&height=220px");
                                web_column.loadUrl(BaseUrl.YT_CHART+"?type=bar&width=100%25&height=220px");
                            }else {
                                web_line.loadUrl("javascript:renderFun('" + "line" + "','" + billBoard + "','"+ dateType +"')");
                                web_column.loadUrl("javascript:renderFun('" + "bar" + "','" + billBoard + "','"+ dateType +"')");
                            }
                        }
                    }
                });
    }
    //看板
    public void warehouseBillboard(String warehouseId,boolean isFirst) {
        OkGo.<QueryVoLzyResponse<List<BillboardCakeBean>>>get(BaseUrl.YT_Base + BaseUrl.warehouseBillboard)
                .params("dateType",dateType)
                .params("warehouseId",warehouseId)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<BillboardCakeBean>>>(getActivity(), true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<BillboardCakeBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<BillboardCakeBean>>> response, String desc) {
                        List<BillboardCakeBean> data = response.body().getData();
                        if (data != null && data.size()>0) {
                            tv_noData.setVisibility(View.GONE);
                            fl_cake.setVisibility(View.VISIBLE);
                            billBoardCage = JSONObject.toJSONString(data);
                            if (isFirst){
                                web_cake.loadUrl(BaseUrl.YT_CHART+"?type=pie&width=100%25&height=100%25");
                            }else {
                                web_cake.loadUrl("javascript:renderFun('" + "pie" + "','" + billBoardCage + "','"+ dateType +"')");
                            }
                        }else {
                            fl_cake.setVisibility(View.INVISIBLE);
                            tv_noData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 查询仓库
     */
    public void listWarehouse() {
        OkGo.<QueryVoLzyResponse<List<WarehouseBean>>>get(BaseUrl.YT_Base + BaseUrl.listWarehouse)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<WarehouseBean>>>(getActivity(), false) {
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
                        List<WarehouseBean> data = response.body().getData();
                        if (data != null){
                            list.clear();
                            tabList.clear();
                            tabLayout.removeAllTabs();

                            list = data;
                            if (list.size()>0){
                                for (int i = 0; i < list.size(); i++) {
                                    WarehouseBean bean = list.get(i);
                                    tabList.add(bean.getPlatformWarehouseName());
                                }

                                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                                for (int i = 0; i <tabList.size() ; i++) {
                                    tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
                                }
                            }
                        }
                    }
                });
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
        managementBillboard(dateType,false);
        warehouseBillboard(warehouseId,false);
    }
}
