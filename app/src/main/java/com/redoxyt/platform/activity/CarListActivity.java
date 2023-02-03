package com.redoxyt.platform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.CarListAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.CarListBean;
import com.redoxyt.platform.bean.YTListBean;
import com.redoxyt.platform.uitl.StringUtils;

import java.util.List;

import butterknife.BindView;
import http.callback.QueryVoDialogCallback;
import http.model.QueryVoLzyResponse;
import http.utils.BaseUrl;

/**
 * Created by zz.
 * description:车辆列表
 */

public class CarListActivity extends BaseActivity implements CarListAdapter.OnItemClickListener{

    @BindView(R.id.ll_addCar)
    LinearLayout ll_addCar;
    @BindView(R.id.lv_list)
    LRecyclerView lv_list;
    @BindView(R.id.tv_changeInfo)
    TextView tv_changeInfo;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private CarListAdapter carListAdapter;
    private String type = "";//BuildReservation 创建预约换车

    @Override
    public int setView() {
        return R.layout.acitivty_car_list;
    }

    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");
        if (StringUtils.isBlank(type)) type = "";
        setListView();

        tv_changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("userType","0");
                startActivity(InfoAuthActivity.class,bundle1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCarList();
    }

    public void myCarList() {
        OkGo.<QueryVoLzyResponse<List<CarListBean>>>get(BaseUrl.YT_Base + BaseUrl.myCarList)
                .execute(new QueryVoDialogCallback<QueryVoLzyResponse<List<CarListBean>>>(this, true) {
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
                    public void onSuccessNotData(Response<QueryVoLzyResponse<List<CarListBean>>> response, String desc) {
                        super.onSuccessNotData(response, desc);
                    }

                    @Override
                    public void onSuccess(Response<QueryVoLzyResponse<List<CarListBean>>> response, String desc) {
                        List<CarListBean> data = response.body().getData();
                        if (data != null && data.size()>0){
                            carListAdapter.setDataList(data);
                            carListAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    private void setListView() {
        lv_list.setLayoutManager(new LinearLayoutManager(CarListActivity.this));
        lv_list.setHasFixedSize(true);
        lv_list.refresh();
        carListAdapter = new CarListAdapter(CarListActivity.this);
        carListAdapter.setOnItemClickListener(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(carListAdapter);
        lv_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        lv_list.setAdapter(mLRecyclerViewAdapter);
        lv_list.setLoadMoreEnabled(false);
        lv_list.setPullRefreshEnabled(false);
    }

    @Override
    public void onItemClick(int position) {
        CarListBean carListBean = carListAdapter.getDataList().get(position);
        String carCode = carListBean.getCarCode();
        String carId = carListBean.getCarId();
        if (type.equals("BuildReservation")){
            Intent intent = new Intent();
            intent.putExtra("carCode",carCode);
            intent.putExtra("carId",carId);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
