package utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;


/**
 * @author Sxw
 * @date 2019/12/6.
 * description：密码键盘 适配工具
 */
public class ScreenUtil {
    private static int WIDTH = 0;

    //获取屏幕尺寸的宽
    public static int getScreenWidth(Context context) {
        if (WIDTH == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            //把屏幕尺寸信息赋值给DisplayMetrics dm
            wm.getDefaultDisplay().getMetrics(dm);
            WIDTH = dm.widthPixels;
        }
        return WIDTH;
    }

    public static int dip2px(Context context, int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size,
                context.getResources().getDisplayMetrics());
    }
}
