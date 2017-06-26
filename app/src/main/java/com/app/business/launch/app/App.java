package com.app.business.launch.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Handler;

/**
 * App数据
 */

public class App {

    public static final Application app = Application.application;

    public static final String processName = ContextBusiness.getProcessName();

    public static final Thread mainThread = Thread.currentThread();

    public static final Handler handler = new Handler();

    public static final PackageInfo packageInfo = ContextBusiness.getPackageInfo();

    public static final ApplicationInfo applicationInfo = packageInfo.applicationInfo;

    public static final String packageName = packageInfo.packageName;

    public static final String versionName = packageInfo.versionName;

    public static final int versionCode = packageInfo.versionCode;

    public static final int uid = applicationInfo.uid;

//    public static final int minSdkVersion = applicationInfo.minSdkVersion;
//
//    public static final int targetSdkVersion = applicationInfo.targetSdkVersion;

    public static final long startTime = System.currentTimeMillis();

    public static void loadClass() {
    }

}
