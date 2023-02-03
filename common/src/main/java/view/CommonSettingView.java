package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.redoxyt.app.common.R;

/**
 * 创建日期：2019/3/8 on 16:54
 * 描述:
 * 作者:Sxw
 */
public class CommonSettingView extends RelativeLayout {
    LinearLayout llsetting;
    ImageView ivSetting;

    public LinearLayout getLlsetting() {
        return llsetting;
    }

    public void setLlsetting(LinearLayout llsetting) {
        this.llsetting = llsetting;
    }

    public ImageView getIvSetting() {
        return ivSetting;
    }

    public void setIvSetting(ImageView ivSetting) {
        this.ivSetting = ivSetting;
    }

    public ImageView getIvNext() {
        return ivNext;
    }

    public void setIvNext(ImageView ivNext) {
        this.ivNext = ivNext;
    }

    public TextView getTxtName() {
        return txtName;
    }

    public void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    ImageView ivNext;
    TextView txtName;

    public TextView getRightName() {
        return rightName;
    }

    public void setRightName(TextView rightName) {
        this.rightName = rightName;
    }

    TextView rightName;

    public CommonSettingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingLayout);
        String layout = typedArray.getString(R.styleable.SettingLayout_setting_layout);
        View view = null;
        if (TextUtils.isEmpty(layout)) {
            view = LayoutInflater.from(context).inflate(R.layout.finance_common_layout, this);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.finance_common_layouts, this);
        }

        llsetting = view.findViewById(R.id.ll_setting);
        ivSetting = view.findViewById(R.id.setting_img);
        ivNext = view.findViewById(R.id.setting_back);
        txtName = view.findViewById(R.id.setting_name);
        rightName = view.findViewById(R.id.setting_right);

        int imgStting = typedArray.getResourceId(R.styleable.SettingLayout_setting_img, -1);
        if (imgStting != -1) {
            ivSetting.setImageResource(imgStting);
        } else {
            ivSetting.setVisibility(GONE);
        }
        int imgBack = typedArray.getResourceId(R.styleable.SettingLayout_setting_next, -1);
        if (imgBack != -1) {
            ivNext.setImageResource(imgBack);
        } else {
            ivNext.setVisibility(INVISIBLE);
        }
        String mName = typedArray.getString(R.styleable.SettingLayout_setting_name);
        if (!TextUtils.isEmpty(mName)) {
            txtName.setText(mName);
        } else {
            txtName.setVisibility(GONE);
        }
        String mRightName = typedArray.getString(R.styleable.SettingLayout_setting_right_name);
        if (!TextUtils.isEmpty(mRightName)) {
            rightName.setText(mRightName);
        } else {
            rightName.setVisibility(GONE);
        }
        typedArray.recycle();
    }
}
