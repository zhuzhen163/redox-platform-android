package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.redoxyt.app.common.R;

/**
 * @author Sxw
 * @date 2019/12/10.
 * description：信息认证组合view
 */

public class InfoView extends RelativeLayout {

    TextView tv;
    ImageView ivImg;
    ImageView ivWaterMark;
    RelativeLayout rlImage;

    public ImageView getIvWaterMark() {
        return ivWaterMark;
    }

    public void setIvWaterMark(ImageView ivWaterMark) {
        this.ivWaterMark = ivWaterMark;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public ImageView getIvImg() {
        return ivImg;
    }

    public void setIvImg(ImageView ivImg) {
        this.ivImg = ivImg;
    }

    public RelativeLayout getRlImage() {
        return rlImage;
    }

    public void setRlImage(RelativeLayout rlImage) {
        this.rlImage = rlImage;
    }

    public InfoView(Context context) {
        super(context);
    }

    public InfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InfoView);
        initView(context, typedArray);
    }


    private void initView(Context context, TypedArray typedArray) {
        View screenView = LayoutInflater.from(context).inflate(R.layout.view_info_view, this);

        rlImage = screenView.findViewById(R.id.rl_image);
        tv = screenView.findViewById(R.id.tv_text);
        ivImg = screenView.findViewById(R.id.iv_img);
        ivWaterMark = screenView.findViewById(R.id.iv_watermark);
        String text = typedArray.getString(R.styleable.InfoView_info_txt);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setVisibility(GONE);
        }
        int watermark = typedArray.getResourceId(R.styleable.InfoView_info_watermark, -1);
        if (watermark != -1) {
            ivWaterMark.setBackgroundResource(watermark);
            ivWaterMark.setVisibility(VISIBLE);
        } else {
            ivWaterMark.setVisibility(GONE);
        }
        int img = typedArray.getResourceId(R.styleable.InfoView_info_img_bg, -1);
        if (img != -1) {
            ivImg.setBackgroundResource(img);
        }
        typedArray.recycle();
    }

    public InfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setWaterMark(int waterMark) {
        this.ivWaterMark.setBackgroundResource(waterMark);
        this.ivWaterMark.setVisibility(GONE);
    }
    public void setText(String string) {
        this.tv.setText(string);
        this.tv.setVisibility(VISIBLE);
    }
}
