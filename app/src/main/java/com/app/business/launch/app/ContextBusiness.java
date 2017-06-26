package com.app.business.launch.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.List;

/**
 * ContextBusiness
 */

public class ContextBusiness {

    static PackageInfo packageInfo;

    static synchronized PackageInfo getPackageInfo() {
        if (packageInfo != null) {
            return packageInfo;
        }
        PackageManager packageManager = App.app.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(App.app.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static String getProcessName() {
        ActivityManager.RunningAppProcessInfo processInfo = getProcessInfo();
        return processInfo.processName;
    }

    public static ActivityManager.RunningAppProcessInfo getProcessInfo() {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) App.app.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : list) {
            if (process.pid == pid) {
                return process;
            }
        }
        return null;
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = activity.getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

}
