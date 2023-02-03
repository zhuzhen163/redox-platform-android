package com.redoxyt.platform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redoxyt.platform.R;
import com.redoxyt.platform.adapter.AppMoreOrderFraPagerAdapter;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.fragment.OrderWareHouseByCangKuMaFragment;
import com.redoxyt.platform.fragment.OrderWareHouseByQiyehaoFragment;
import com.redoxyt.platform.qr.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.PermissionsUtils;
import widget.CommonToolbar;

public class MoreWareHouseOrderActivity extends BaseActivity {


    @BindView(R.id.tv_qiyehao)
    TextView tvQiyehao;
    @BindView(R.id.tv_cangkudaima)
    TextView tvCangkudaima;
    @BindView(R.id.vp_more_warehouse_order)
    ViewPager vpMoreWarehouseOrder;
    @BindView(R.id.ctv_title_more_order)
    CommonToolbar ctvTitleMoreOrder;
    private List<Fragment> mPagerViews = new ArrayList<>();
    private AppMoreOrderFraPagerAdapter appMoreOrderFraPagerAdapter;
    private OrderWareHouseByQiyehaoFragment orderWareHouseByQiyehaoFragment;
    private OrderWareHouseByCangKuMaFragment orderWareHouseByCangKuMaFragment;

    @Override
    public int setView() {
        return R.layout.activity_more_ware_house_order;
    }

    @Override
    public void initData() {
        initFragment();
        vpMoreWarehouseOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        selectTab(R.id.tv_qiyehao);
                        break;
                    case 1:
                        selectTab(R.id.tv_cangkudaima);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        orderWareHouseByQiyehaoFragment = new OrderWareHouseByQiyehaoFragment();
        orderWareHouseByCangKuMaFragment = new OrderWareHouseByCangKuMaFragment();
        mPagerViews.add(orderWareHouseByQiyehaoFragment);
        mPagerViews.add(orderWareHouseByCangKuMaFragment);
        appMoreOrderFraPagerAdapter = new AppMoreOrderFraPagerAdapter(getSupportFragmentManager(), mPagerViews);
        vpMoreWarehouseOrder.setAdapter(appMoreOrderFraPagerAdapter);
    }

    @OnClick({R.id.tv_qiyehao, R.id.tv_cangkudaima, R.id.ctv_title_more_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qiyehao:
                selectTab(view.getId());
                break;
            case R.id.tv_cangkudaima:
                selectTab(view.getId());
                break;
            case R.id.ctv_title_more_order:
                starQrCoddeScan();
                break;
        }
    }

    private void starQrCoddeScan() {

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("android.permission.WRITE_EXTERNAL_STORAGE");
        list1.add("android.permission.CAMERA");
        if (PermissionsUtils.requestPermission(this, list1)) {
            Bundle bundleCar = new Bundle();
            bundleCar.putString("title", "二维码扫描");
            startActivityForResult(CaptureActivity.class, bundleCar, 10008);
        } else {
            showToast("需要照相机权限");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10008) {
                String result = data.getStringExtra("uuid");
                HashMap<String, String> resultJson = new Gson().fromJson(result, new TypeToken<HashMap<String, String>>() {
                }.getType());
                switch (resultJson.get("codeType")) {
                    case "1":
                        vpMoreWarehouseOrder.setCurrentItem(0);
                        orderWareHouseByQiyehaoFragment.setQrView(resultJson.get("groupNum"));
                        break;
                    case "2":
                        vpMoreWarehouseOrder.setCurrentItem(1);
                        orderWareHouseByCangKuMaFragment.setQrView(resultJson.get("warehouseCode"), true);
                        break;
                    case "3":
                        orderWareHouseByCangKuMaFragment.setplatformId(resultJson.get("warehouseCode"),resultJson.get("platformId"),true);
                        vpMoreWarehouseOrder.setCurrentItem(1);
                        break;
                }
            }
        }
    }

    /**
     * 根绝选中状态调整布局
     *
     * @param id
     */
    private void selectTab(int id) {
        switch (id) {
            case R.id.tv_qiyehao:
                tvQiyehao.setBackground(getResources().getDrawable(R.drawable.shape_left_bg_stroke_yellow_selected));
                tvCangkudaima.setBackground(getResources().getDrawable(R.drawable.shape_right_bg_stroke_yellow_unselected));
                tvQiyehao.setTextColor(getResources().getColor(R.color.white));
                tvCangkudaima.setTextColor(getResources().getColor(R.color.colorText));
                vpMoreWarehouseOrder.setCurrentItem(0);
                break;
            case R.id.tv_cangkudaima:
                tvQiyehao.setBackground(getResources().getDrawable(R.drawable.shape_left_bg_stroke_yellow_unselected));
                tvCangkudaima.setBackground(getResources().getDrawable(R.drawable.shape_right_bg_stroke_yellow_selected));
                tvQiyehao.setTextColor(getResources().getColor(R.color.colorText));
                tvCangkudaima.setTextColor(getResources().getColor(R.color.white));
                vpMoreWarehouseOrder.setCurrentItem(1);
                break;

        }

    }

}