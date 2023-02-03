package http.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import util.PreManagerCustom;

/**
 * Created by Administrator on 2018/4/21 0021.
 */

public class Constant {

    public static Context mContext;//全局context变量
    public static PreManagerCustom preManagerCustom;

    // 项目文件存放地址
    public static String CCB_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/ccb";


}
