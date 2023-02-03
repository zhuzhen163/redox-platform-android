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
import android.widget.TextView;

import com.redoxyt.app.common.R;


/**
 * Created by Administrator on 2018/5/4 0004.
 * 自定筛选样式控件
 */

public class CommonScreenView extends LinearLayout {

    public CommonScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonScreenView);
        initView(context, typedArray);
    }

    TextView tvTitleName;

    public TextView getTvTitleName() {
        return tvTitleName;
    }

    ImageView imgTitleIcon;

    public ImageView getImgTitleIcon() {
        return imgTitleIcon;
    }


    private void initView(Context context, TypedArray typedArray) {
        View screenView = LayoutInflater.from(context).inflate(R.layout.view_common_screen, this);


        tvTitleName = (TextView) screenView.findViewById(R.id.tv_title_name);
        imgTitleIcon = (ImageView) screenView.findViewById(R.id.img_title_icon);
        String mTitleName = typedArray.getString(R.styleable.CommonScreenView_title);
        if (!TextUtils.isEmpty(mTitleName)) {
            tvTitleName.setText(mTitleName);
        }
        int mTitleIcon = typedArray.getResourceId(R.styleable.CommonScreenView_title_icon, -1);
        if (mTitleIcon != -1) {
            imgTitleIcon.setImageResource(mTitleIcon);
        } else {
            imgTitleIcon.setImageResource(R.mipmap.pf_card_down);
        }
        typedArray.recycle();
    }
}
