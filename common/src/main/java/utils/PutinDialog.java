package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;


import com.redoxyt.app.common.R;

import util.Utils;

/**
 * Created by ${PutinMa} on 17-11-22.
 */

public class PutinDialog extends AlertDialog {
    private final Context context;
    private final int layoutId;
    private final double widthPecent;//对话框宽度百分比
    private final double heightPecent;//对话框高度百分比
    private View dialogView;

    public PutinDialog(Context context, int layoutId, double widthPecent, double heightPecent) {
        super(context);
        this.context = context;
        this.layoutId = layoutId;
        this.widthPecent = widthPecent;
        this.heightPecent = heightPecent;
        dialogView = LayoutInflater.from(context).inflate(layoutId, null);
    }
    public PutinDialog(Context context, int layoutId, int styleId, double widthPecent, double heightPecent) {
        super(context,styleId);
        this.context = context;
        this.layoutId = layoutId;
        this.widthPecent = widthPecent;
        this.heightPecent = heightPecent;
        dialogView = LayoutInflater.from(context).inflate(layoutId, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        setContentView(dialogView);
        initSize();
    }

    /**
     * 设置Dialog大小
     */
    private void initSize() {
        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);
        if (widthPecent != 0) {
            params.width = (int) (Utils.getScreenWidth() * widthPecent);
        }
        if (heightPecent != 0) {
            params.height = (int) (Utils.getScreenHeight() * heightPecent);
        }
        if (widthPecent != 0 || heightPecent != 0) {
            dialogWindow.setAttributes(params);
        }
    }

    /**
     * 获取View对象，便于在调用类中初始化
     *
     * @return
     */
    public View getView() {
        return dialogView;
    }

    /**
     * 创建旋转动画
     */
    public void createRotateAnimation() {
        /**
         * 构造方法如下
         *fromDegrees、toDegrees表示开始、结束的角度(0度为水平方向右侧的开始角度)，pivotXValue、pivotYValue代表旋转的中心位置，[0.0f-1.0f],
         *pivotXType、pivotYType表示旋转的类型(Animation.ABSOLUTE,、Animation.RELATIVE_TO_SELF、Animation.RELATIVE_TO_PARENT)
         *当type为Animation.ABSOLUTE时，这个个值为具体的像素值，当type为Animation.RELATIVE_TO_SELF或Animation.RELATIVE_TO_PARENT，这个个值为比例值，取值范围是[0f, 1.0f]
         *public RotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,int pivotYType, float pivotYValue) {     }
         */
        //初始化RotateAnimation
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //动画执行时间
        animation.setDuration(2000);
        //动画重复次数-1表示不停重复
        //animation.setRepeatCount(-1);
        //给控件设置动画
        dialogView.startAnimation(animation);
    }

    /**
     * 创建缩放动画
     */
    public void createScaleAnimation() {
        /**
         * 构造方法如下
         * fromX、toX 开始结束的X轴缩放比率[0.0f-1.0f]，fromY、toYtoY开始结束的Y轴缩放比率[0.0f-1.0f]，pivotXValue、pivotYValue代表旋转的中心位置，[0.0f-1.0f],
         * pivotXType、pivotYType表示旋转的类型(Animation.ABSOLUTE,、Animation.RELATIVE_TO_SELF、Animation.RELATIVE_TO_PARENT)
         * 当type为Animation.ABSOLUTE时，这个个值为具体的像素值，当type为Animation.RELATIVE_TO_SELF或Animation.RELATIVE_TO_PARENT，这个个值为比例值，取值范围是[0f, 1.0f]
         *  public ScaleAnimation(float fromX, float toX, float fromY, float toY,int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {}
         */
        //初始化ScaleAnimation
        ScaleAnimation animation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //动画执行时间
        animation.setDuration(2000);
        //动画重复次数-1表示不停重复
        //animation.setRepeatCount(-1);
        //给控件设置动画
        dialogView.startAnimation(animation);
    }

    /**
     * 创建透明度渐变动画
     */
    public void createAlphaAnimation() {
        /***
         * 构造方法如下
         * fromAlpha、toAlpha表示透明度的起始值和结束值，0.0f表示全透明，1.0f表示不透明。
         * public AlphaAnimation(float fromAlpha, float toAlpha) {}
         */
        //初始化AlphaAnimation
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        //动画执行时间    animation.setDuration(2000);
        //动画重复次数-1表示不停重复
        //animation.setRepeatCount(-1);
        //给控件设置动画
        dialogView.startAnimation(animation);
    }

    public void showAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation animation1 = new ScaleAnimation(0.25f, 1f, 0.25f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation1.setDuration(1000);
        animationSet.addAnimation(animation1);
        RotateAnimation animation2 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation2.setDuration(1000);
        animationSet.addAnimation(animation2);
        dialogView.startAnimation(animationSet);
    }

    public void changBg(PutinDialog putinDialog, float f) {
        Window window = putinDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = f;
        window.setAttributes(params);
    }
}
