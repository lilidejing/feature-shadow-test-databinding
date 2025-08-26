package com.tencent.shadow.sample.constant;

import android.app.ActivityManager;
import android.content.Context;

/**
 * @author LCF
 * Description:
 * @date : 2025/4/12
 */
public class ProcessUtils {
    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
