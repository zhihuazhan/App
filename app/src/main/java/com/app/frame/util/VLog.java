package com.app.frame.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.app.business.launch.app.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log
 */

public class VLog {

    private static final boolean ENABLE = true;

    private static final boolean DUBUG_TAG_ENABLE = true;

    private static final String PROJECT_TAG = "LEOVIDEO";

    private static final String DEVELOPER_TAG = "ZZH";

    private static boolean EXCEPTION_HANDLER_ENABLE = true;

    public static void i(String message) {
        print(null, message, false);
    }

    public static void i(String tag, String message) {
        print(tag, message, false);
    }

    public static void e(String message) {
        print(null, message, true);
    }

    public static void e(String tag, String message) {
        print(tag, message, true);
    }

    private static void print(String tag, String message, boolean e) {
        if (!ENABLE) {
            return;
        }
        String t = getTag(tag);
        if (t != null && t.length() > 100) {
            t = t.substring(0, 100);
        }
        if (message != null && message.length() > 3000) {
            message = message.substring(0, 3000);
        }
        if (e) {
            Log.e(t, message);
        } else {
            Log.i(t, message);
        }
    }

    private static String getTag(String tag) {
        StringBuilder builder = new StringBuilder(64);
        builder.append("[");
        if (DUBUG_TAG_ENABLE) {
            builder.append(Thread.currentThread());
            builder.append(":");
        }
        if (PROJECT_TAG != null) {
            builder.append(PROJECT_TAG);
            builder.append(":");
        }
        if (DEVELOPER_TAG != null) {
            builder.append(DEVELOPER_TAG);
            builder.append(":");
        }
        if (tag != null) {
            builder.append(tag);
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Define an default error handler, collect the error if happens.
     */
    private static void collectApplicationCrash() {
        final Thread.UncaughtExceptionHandler originalHandler = Thread
                .getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                String appInfo = getAppInfo();
                StringBuilder sStringBuilder = new StringBuilder();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                sStringBuilder.append(dateFormat.format(new Date()));
                sStringBuilder.append("\t");
                if (appInfo != null) {
                    sStringBuilder.append(appInfo);
                }
                if (ex != null) {
                    sStringBuilder.append("\t").append(
                            Log.getStackTraceString(ex));
                }
                writeToFile(sStringBuilder.toString());

                if (originalHandler != null) {
                    originalHandler.uncaughtException(thread, ex);
                }
            }
        });
    }

    /**
     * in order to improve performance, write LogEntry to file by batch
     */
    private static void writeToFile(String message) {
        if (!isSDCardAvaible())
            return;
        File sLogFile = null;
        try {
            if (sLogFile == null) {
                String LOG_DIR = "";
                File dir = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + File.separator + LOG_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                sLogFile = new File(dir.getAbsolutePath() + File.separator
                        + "log.txt");
            }

            if (!sLogFile.exists()) {
                sLogFile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        BufferedWriter buf = null;
        try {
            // use BufferedWriter for performance, true to set append to file
            // flag
            buf = new BufferedWriter(new FileWriter(sLogFile, true));
            buf.append(message);
        } catch (IOException e) {
        } finally {
            try {
                buf.close();
            } catch (IOException e) {
            }
        }
    }

    private static boolean isSDCardAvaible() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    private static String getAppInfo() {
        Context ct = App.app;
        if (ct == null) {
            return null;
        }
        PackageManager pm = ct.getPackageManager();
        PackageInfo info;
        try {
            info = pm.getPackageInfo(ct.getPackageName(), 0);
            StringBuilder builder = new StringBuilder();
            builder.append(info.packageName)
                    .append("\t" + "(versionName:" + info.versionName + ")")
                    .append("\t" + "(versionCode:" + info.versionCode + ")");

            return builder.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static {
        if (EXCEPTION_HANDLER_ENABLE) {
            collectApplicationCrash();
        }
    }

}
