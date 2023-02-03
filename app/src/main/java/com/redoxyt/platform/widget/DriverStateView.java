package com.redoxyt.platform.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.redoxyt.platform.R;

import util.TimeUtil;


/**
 * 描述：
 * 作者：Created by zhuzhen
 */
public class DriverStateView extends FrameLayout {
    private final int IMAGE_WIDTH = 720;
    private final int IMAGE_HEIGHT = 1280;
    ImageView iv_sign;
    TextView tv_time,tv_idCardNo,tv_driverName,tv_carCode;

    public DriverStateView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        View layout = View.inflate(getContext(), R.layout.driver_state_layout, this);
        iv_sign = layout.findViewById(R.id.iv_sign);
        tv_time = layout.findViewById(R.id.tv_time);
        tv_idCardNo = layout.findViewById(R.id.tv_idCardNo);
        tv_driverName = layout.findViewById(R.id.tv_driverName);
        tv_carCode = layout.findViewById(R.id.tv_carCode);
    }

    /**
     * 设置相关信息
     *
     */
    public void setContent(Bitmap bitmap, String car,String name, String idCard) {
        iv_sign.setImageBitmap(bitmap);
        tv_time.setText("日期："+ TimeUtil.getDateID());
        tv_carCode.setText(" "+car+" ");
        tv_driverName.setText(" "+name+" ");
        tv_idCardNo.setText(" "+idCard+" ");
    }

    /**
     * 生成图片
     *
     * @return
     */
    public Bitmap createImage() {

        //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, MeasureSpec.EXACTLY);

        measure(widthMeasureSpec, heightMeasureSpec);
        layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        draw(canvas);

        return bitmap;
    }
}
