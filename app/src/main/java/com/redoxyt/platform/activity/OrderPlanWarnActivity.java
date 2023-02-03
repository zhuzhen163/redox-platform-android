package com.redoxyt.platform.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.base.CommonAdapter;
import com.redoxyt.platform.base.ViewHolder;
import com.redoxyt.platform.bean.AiBuildResetBean;
import com.redoxyt.platform.bean.YTListBean;
import com.redoxyt.platform.widget.PutinMaDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;
import utils.ConfigUtils;

public class OrderPlanWarnActivity extends BaseActivity {


    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.go_back)
    LinearLayout goBack;
    @BindView(R.id.tv_tipsMessage)
    TextView tvTipsMessage;
    @BindView(R.id.rv_remain_list)
    RecyclerView rvRemainList;
    @BindView(R.id.btn_tipsOne_continue)
    Button btnTipsOneContinue;
    @BindView(R.id.btn_tipsOne_cancel)
    Button btnTipsOneCancel;
    private CommonAdapter<AiBuildResetBean> mAdapter;
    private List<AiBuildResetBean> data = new ArrayList<>();
    private PutinMaDialog putinMaDialog;

    @Override
    public int setView() {
        return R.layout.activity_orderplan_warn;
    }

    @Override
    public void initData() {
        rvRemainList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = initRecyclerViewAdapter();
        this.rvRemainList.setAdapter(mAdapter);
        remainMuchWarehouse();
    }

    @OnClick({R.id.btn_tipsOne_continue, R.id.btn_tipsOne_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tipsOne_continue:
                gotoAiBuilPage();
                break;
            case R.id.btn_tipsOne_cancel:
                showDeleteConfirmDialog();
                break;
        }
    }

    /**
     * 跳转AI预约页面
     */
    private void gotoAiBuilPage() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("AiBuildResetBean", data.get(0));
        startActivity(AIBuildReservationActivity.class, bundle);
    }

    private void showDeleteConfirmDialog() {
        putinMaDialog = new PutinMaDialog(this, R.layout.ai_resrevation_tips_two, 0.85, 0);
        View view = putinMaDialog.getView();
        TextView tv_tipsTwoMessage = (TextView) view.findViewById(R.id.tv_tipsTwoMessage);
        Button btn_tipsTwo_continue = (Button) view.findViewById(R.id.btn_tipsTwo_continue);
        Button btn_tipsTwo_cancel = (Button) view.findViewById(R.id.btn_tipsTwo_cancel);
        tv_tipsTwoMessage.setText("您还有" + data.size() + "个仓库未进行装卸货， 您是否确定取消");
        putinMaDialog.show();
        btn_tipsTwo_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAiBuilPage();
                putinMaDialog.dismiss();
            }
        });
        btn_tipsTwo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlan();
            }
        });

    }

    /**
     * 取消预约计划
     */
    private void deletePlan() {
        OkGo.<QueryVoLzyResponse<List<YTListBean>>>get(BaseUrl.YT_Base + BaseUrl.DELETEMUCHWAREHOUSERESERVE)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<YTListBean>>>(this, true) {
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
                        putinMaDialog.dismiss();
                        finish();
                    }
                });

    }

    /**
     * 多仓完成订单,操作剩余仓库
     */
    public void remainMuchWarehouse() {
        OkGo.<QueryVoLzyResponse<List<AiBuildResetBean>>>get(BaseUrl.YT_Base + BaseUrl.remainMuchWarehouse)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<AiBuildResetBean>>>(this, true) {
                    @Override
                    public void onFail(int code, String desc) {
                        showToast(desc);
                        super.onFail(code, desc);
                    }

                    @Override
                    public void onError(String desc) {
                        super.onError(desc);
                        showToast(desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<AiBuildResetBean>>> response, String desc) {
                        data = response.body().getData();
                        if (data != null && data.size() > 0) {
                            tvTipsMessage.setText(desc);
                            mAdapter.setData(data);
                        }
                    }
                });
    }

    protected CommonAdapter<AiBuildResetBean> initRecyclerViewAdapter() {
        CommonAdapter<AiBuildResetBean> adapter = new CommonAdapter<AiBuildResetBean>(this,
                R.layout.item_tips_warehouse_list, data) {
            @Override
            public void convert(ViewHolder itemViewHolder, AiBuildResetBean bean, int position) {
                itemViewHolder.setText(R.id.tv_position, Integer.toString(position + 1));
                itemViewHolder.setText(R.id.tv_tipsWarehouseName, bean.getWarehouseName());
                itemViewHolder.setText(R.id.tv_tipsWarehouseCode, bean.getWarehouseCode());
                itemViewHolder.setText(R.id.tv_tipsPlatformName, bean.getPlatformName());
            }
        };
        return adapter;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}