package com.app.business.launch.app;

import android.support.multidex.MultiDexApplication;

/**
 * Application
 */
public class Application extends MultiDexApplication {

    static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        App.loadClass();
    }

}
