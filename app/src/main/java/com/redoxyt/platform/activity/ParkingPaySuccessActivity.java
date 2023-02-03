package com.redoxyt.platform.activity;

import android.view.View;
import android.widget.TextView;

import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by zz.
 * description:
 */

public class ParkingPaySuccessActivity extends BaseActivity {

    @BindView(R.id.tv_parkPayTime)
    TextView tv_parkPayTime;
    @BindView(R.id.tv_warehouseName)
    TextView tv_warehouseName;
    @BindView(R.id.tv_payNum)
    TextView tv_payNum;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    @Override
    public int setView() {
        return R.layout.acitivty_parking_pay_success;
    }

    @Override
    public void initData() {
        String warehouseName = getIntent().getStringExtra("warehouseName");
        String parkPayTime = getIntent().getStringExtra("parkPayTime");
        String parkPayAmount = getIntent().getStringExtra("parkPayAmount");
        tv_warehouseName.setText(warehouseName);
        tv_parkPayTime.setText(parkPayTime);
        tv_payNum.setText(parkPayAmount);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
