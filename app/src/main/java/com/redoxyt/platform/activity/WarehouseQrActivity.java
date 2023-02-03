package com.redoxyt.platform.activity;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.redoxyt.platform.R;
import com.redoxyt.platform.base.BaseActivity;
import com.redoxyt.platform.bean.WarehouseQrBean;
import com.redoxyt.platform.qr.QRCodeUtil;

import butterknife.BindView;

public class WarehouseQrActivity extends BaseActivity {

    @BindView(R.id.tv_qr_warehouseName)
    TextView tv_qr_warehouseName;
    @BindView(R.id.tv_qr_warehouseCode)
    TextView tv_qr_warehouseCode;
    @BindView(R.id.tv_qr_warehouseAddress)
    TextView tv_qr_warehouseAddress;
    @BindView(R.id.iv_qr_code)
    ImageView iv_qr_code;

    private Handler handler = new Handler();

    @Override
    public int setView() {
        return R.layout.acitivty_warehouse_qr;
    }

    @Override
    public void initData() {
        String warehouseName = getIntent().getStringExtra("warehouseName");
        tv_qr_warehouseName.setText(warehouseName);
        String warehouseCode = getIntent().getStringExtra("warehouseCode");
        tv_qr_warehouseCode.setText(warehouseCode);
        String warehouseAddress = getIntent().getStringExtra("warehouseAddress");
        tv_qr_warehouseAddress.setText(warehouseAddress);
        String warehouseId = getIntent().getStringExtra("warehouseId");

        WarehouseQrBean bean = new WarehouseQrBean();
        bean.setCodeType("2");
        bean.setWarehouseId(warehouseId);
        bean.setWarehouseCode(warehouseCode);
        String s = new Gson().toJson(bean);

        handler.post(() -> {
            try {
                iv_qr_code.setImageBitmap(QRCodeUtil.qr_code(s,0xff000000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }
}
