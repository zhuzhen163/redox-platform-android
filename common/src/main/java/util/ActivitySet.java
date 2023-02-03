package util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * BaseActivity 管理类
 *
 * @param <T>
 */
public class ActivitySet<T extends Activity> extends HashSet<T> {

    /**
     * 存放activity的列表
     */
    public static HashMap<Class<?>, Activity> activities = new LinkedHashMap<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity, Class<?> clz) {
        activities.put(clz, activity);
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T extends Activity> boolean isActivityExist(Class<T> clz) {
        boolean res;
        Activity activity = getActivity(clz);
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }

        return res;
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    public static <T extends Activity> T getActivity(Class<T> clazz) {
        return (T) activities.get(clazz);
    }


    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.getClass());
        }
    }

    /**
     * 移除所有的Activity
     */
    public static void removeAllActivity() {
        if (activities != null && activities.size() > 0) {
            Set<Map.Entry<Class<?>, Activity>> sets = activities.entrySet();
            for (Map.Entry<Class<?>, Activity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
        }
        activities.clear();
    }


//    /**
//     *
//     */
//    private static final long serialVersionUID = -9211054184477813134L;
//
//    private ActivitySet() {
//
//    }
//
//    public static ActivitySet<Activity> getInstance() {
//        if (activities == null) {
//            synchronized (ActivitySet.class) {
//                if (activities == null)
//                    activities = new ActivitySet<>();
//            }
//
//        }
//        return activities;
//
//    }
//
//    private static ActivitySet<Activity> activities;
//
//    public void finishAll() {
//        for (Activity a : this) {
//            if (a == null) continue;
//            a.finish();
//        }
//    }
//
//    public void finishActiviy(Class cla) {
//        for (Activity a : this) {
//            if (a == null) continue;
//            if (a.getClass().equals(cla)) {
//                a.finish();
//            }
//        }
//    }
//
//    public void setResult(int result, Class... cla) {
//        for (Activity a : this) {
//            if (a == null) continue;
//            for (int i = 0; i < cla.length; i++) {
//                if (a.getClass() == cla[i]) {
//                    a.setResult(result);
//                    break;
//                }
//            }
//        }
//    }
//
//    public void finishOther(Class cla) {
//        for (Activity a : this) {
//            if (a == null) continue;
//            if (a.getClass() != cla) {
//                a.finish();
//            }
//        }
//    }
//
//    public void finishOther(Set<Class> classes, int resultCode) {
//        for (Activity a : this) {
//            if (a == null) continue;
//            if (!classes.contains(a.getClass())) {
//                a.finish();
//                a.setResult(resultCode);
//            }
//        }
//    }
//
//    public void finishOther(int resultCode, Class... cls) {
//        for (Activity a : this) {
//            if (a == null) continue;
//            boolean isContains = false;
//            for (int i = 0; i < cls.length; i++) {
//                if (cls[i] == a.getClass()) {
//                    isContains = true;
//                    break;
//                }
//            }
//            if (!isContains) {
//                a.setResult(resultCode);
//                a.finish();
//            }
//        }
//    }
}
