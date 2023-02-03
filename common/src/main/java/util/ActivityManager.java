package util;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * 创建日期：2019/3/1 on 11:30
 * 描述:
 * 作者:Sxw
 */
public class ActivityManager {

        private static ActivityManager sInstance = new ActivityManager();
        private WeakReference<Activity> sCurrentActivityWeakRef;


        private ActivityManager() {

        }

        public static ActivityManager getInstance() {
            return sInstance;
        }

        public Activity getCurrentActivity() {
            Activity currentActivity = null;
            if (sCurrentActivityWeakRef != null) {
                currentActivity = sCurrentActivityWeakRef.get();
            }
            return currentActivity;
        }

        public void setCurrentActivity(Activity activity) {
            sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
        }

}
