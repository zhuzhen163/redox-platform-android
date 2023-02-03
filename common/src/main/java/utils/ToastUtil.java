package utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.redoxyt.app.common.R;


/**
 * 作者：nicksong
 * 创建时间：2016/11/21
 * 功能描述:自定义toast样式、显示时长
 */

public class ToastUtil {

    private static Toast mToast;
    private TextView mTextView;
    private TimeCount timeCount;
    private String message;
    private Handler mHandler = new Handler();
    private boolean canceled = true;

    public ToastUtil(Context context, int layoutId, String msg, int viewid) {
//        message = msg;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        View view = inflater.inflate(layoutId, null);
        //自定义toast文本
        mTextView = (TextView) view.findViewById(viewid);
        mTextView.setText(msg);
        Log.i("ToastUtil", "Toast start...");
        if (mToast == null) {
            mToast = new Toast(context);
            Log.i("ToastUtil", "Toast create...");
        }
        //设置toast居中显示
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(view);
    }

    /**
     * 自定义居中显示toast
     */
    public void show() {
        mToast.show();
        Log.i("ToastUtil", "Toast show...");
    }

    /**
     * 自定义时长、居中显示toast
     *
     * @param duration 单位毫秒ms
     */
    public void show(int duration) {
        timeCount = new TimeCount(duration, 1000);
        Log.i("ToastUtil", "Toast show...");
        if (canceled) {
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏toast
     */
    private void hide() {
        if (mToast != null) {
            mToast.cancel();
        }

        canceled = true;
        Log.i("ToastUtil", "Toast that customed duration hide...");
    }

    private void showUntilCancel() {
        if (canceled) { //如果已经取消显示，就直接return
            return;
        }
        mToast.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("ToastUtil", "Toast showUntilCancel...");
                showUntilCancel();
            }
        }, Toast.LENGTH_LONG);
    }

    /**
     * 自定义计时器
     */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(message + ": " + millisUntilFinished / 1000 + "s后消失");
        }

        @Override
        public void onFinish() {
            hide();
        }
    }


    /**
     * 69      * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     * 70
     */
    public static void ToastMessage(Context context, String titles, String messages, int img) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        View view = inflater.inflate(R.layout.toast_common, null); //加載layout下的布局
        ImageView iv = view.findViewById(R.id.toast_img);
        if (img == -1) {
            iv.setVisibility(View.GONE);

        } else if (img != 1) {
            iv.setImageResource(img);//显示的图片

        } else if (img == 1) {
            iv.setImageResource(R.mipmap.icon_toast_success);//显示的图片

        }
        TextView title = view.findViewById(R.id.toast_title);
        if (TextUtils.isEmpty(titles)) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(titles); //toast的标题

        }
        TextView text = view.findViewById(R.id.toast_content);
        if (TextUtils.isEmpty(messages)) {
            text.setVisibility(View.GONE);
        } else {
            text.setText(messages); //toast内容

        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }


    /**
     * 69      * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     * 70
     */
    public static void ToastMessage(Context context, String titles, int ss) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        View view = inflater.inflate(R.layout.toast_common_im_news, null); //加載layout下的布局
        ImageView iv = view.findViewById(R.id.toast_img);
        ((AnimationDrawable) iv.getBackground()).start();
        TextView title = view.findViewById(R.id.toast_title);
        if (TextUtils.isEmpty(titles)) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(titles); //toast的标题

        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(ss);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }

    public static void showLongToast(Activity context, String content){
        if(mToast==null){
            mToast=Toast.makeText(context,content,Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

}
