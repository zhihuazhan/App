package com.app.business.launch;

import android.content.Context;
import android.content.Intent;

import com.app.business.launch.app.App;
import com.app.business.main.MainActivity;
import com.app.frame.concurrent.multithread.Action;
import com.app.frame.concurrent.multithread.Concurrent;
import com.app.frame.util.VLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用启动逻辑
 */

public class Launch {

    private static final long DELAY = 1000;

    public static void launch() {
        //闪屏开始时间
        final long start = System.currentTimeMillis();

        //初始化app级数据
        List<Action> list = new ArrayList<>();
        Action a1 = new Action() {
            @Override
            public void act() {

            }
        };
        a1.setPriority(1);
        list.add(a1);

        //初始化百度统计

        //初始化数据库

        //初始化SP

        //...

        //初始化主页数据

        //进入引导或主页
        Action e = new Action() {
            @Override
            public void act() {
                nextActivity(start);
            }
        };
        e.setPriority(5);
        list.add(e);

        new Concurrent(list).start();
    }


    /**
     * 进入引导或主页
     */
    private static void nextActivity(long start) {
        long current = System.currentTimeMillis();
        final long off = current - start;
        final long fixedOff = off > 0 ? off : -off;
        long delay = DELAY - fixedOff;

        VLog.i("[启动加载]耗时:" + fixedOff);
        delay = delay > 0 ? delay : 0;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                boolean showNavigation = false;
                if (showNavigation) {
                    //to Navigation
                } else {
                    Context context = App.app;
                    Intent intent = new Intent(App.app, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                SplashActivity.finishSplash();
            }
        };
        App.handler.postDelayed(runnable, delay);
    }

}
