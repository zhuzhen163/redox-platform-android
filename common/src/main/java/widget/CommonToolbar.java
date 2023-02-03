package widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redoxyt.app.common.R;
import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2018/4/21 0021.
 */

public class CommonToolbar extends LinearLayout {
    public CommonToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initToolbar(context, attrs);
    }

    public LinearLayout getGoBack() {
        return goBack;
    }

    public void setGoBack(LinearLayout goBack) {
        this.goBack = goBack;
    }

    private LinearLayout goBack, tool_bar;
    private TextView btnRight;
    private ImageView imgRightOne;
    private ImageView imgRightTow;

    private void initToolbar(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_title_layout, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonToolbar);
        //是否显示左边图标 默认显示 app:left_visibility=true 隐藏
        goBack = findViewById(R.id.go_back);
        tool_bar = findViewById(R.id.tool_bar);
        ImageView leftImg = findViewById(R.id.left_img);

        int mLeftImgDrawable = typedArray.getResourceId(R.styleable.CommonToolbar_left_img, -1);
        if (mLeftImgDrawable != -1) {
            leftImg.setImageResource(mLeftImgDrawable);
        }

        boolean leftVisibility = typedArray.getBoolean(R.styleable.CommonToolbar_left_visibility, false);
        if (leftVisibility) {
            goBack.setVisibility(INVISIBLE);
        }
        goBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    Activity activity = (Activity) getContext();
                    OkGo.getInstance().cancelAll();
                    activity.finish();
                }
            }
        });
        //设置title标题
        TextView tv = (TextView) findViewById(R.id.title_name);
        String titleName = typedArray.getString(R.styleable.CommonToolbar_title_name);
        if (!TextUtils.isEmpty(titleName)) {
            tv.setText(titleName);
            tv.setVisibility(VISIBLE);
        }
        //设置右边文字
        btnRight = findViewById(R.id.btn_right);
        String rightName = typedArray.getString(R.styleable.CommonToolbar_right_name);
        if (!TextUtils.isEmpty(rightName)) {
            btnRight.setVisibility(VISIBLE);
            btnRight.setText(rightName);
        }
        int backgroundColor = typedArray.getColor(R.styleable.CommonToolbar_title_color, 0xffffffff);
        tool_bar.setBackgroundColor(backgroundColor);
        int titleColor = typedArray.getColor(R.styleable.CommonToolbar_title_color, 0xff2E2E2E);
        tv.setTextColor(titleColor);
        //设置右边图片
        imgRightOne = (ImageView) findViewById(R.id.img_right_one);
        int imgOneDrawable = typedArray.getResourceId(R.styleable.CommonToolbar_right_img_one, -1);
        if (imgOneDrawable != -1) {
            imgRightOne.setImageResource(imgOneDrawable);
            imgRightOne.setVisibility(VISIBLE);
        }

        imgRightTow = (ImageView) findViewById(R.id.img_right_tow);
        int imgTowDrawable = typedArray.getResourceId(R.styleable.CommonToolbar_right_img_two, -1);
        if (imgTowDrawable != -1) {
            imgRightTow.setImageResource(imgTowDrawable);
            imgRightTow.setVisibility(VISIBLE);
        }
        int bgColor = typedArray.getResourceId(R.styleable.CommonToolbar_bg_color, -1);
        if (bgColor != -1) {
            view.setBackgroundResource(bgColor);
            tv.setTextColor(Color.WHITE);
            btnRight.setTextColor(Color.WHITE);
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.color_white_ffffff));
        }

        typedArray.recycle();
    }

    public void setTitle(String str) {
        //设置title标题
        TextView tv = (TextView) findViewById(R.id.title_name);
        if (!TextUtils.isEmpty(str)) {
            tv.setText(str);
        }
    }

    /**
     * 返回按钮点击事件
     *
     * @return
     */
    public TextView getBtnRight() {
        return btnRight;
    }

    public ImageView getImgRightOne() {
        return imgRightOne;
    }

    public ImageView getImgRightTow() {
        return imgRightTow;
    }

}
