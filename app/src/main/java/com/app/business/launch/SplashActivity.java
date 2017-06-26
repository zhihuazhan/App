package com.app.business.launch;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.app.R;
import com.app.business.launch.app.App;
import com.app.frame.activity.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * 闪屏
 */

public class SplashActivity extends BaseActivity {

    private static WeakReference<SplashActivity> sReference;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_splash);
        sReference = new WeakReference(this);
        FragmentManager m = getFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        launch();
    }

    private void launch() {
        App.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Launch.launch();
            }
        }, 10);
    }

    public static void finishSplash() {
        WeakReference<SplashActivity> reference = sReference;
        if (reference == null) {
            return;
        }
        SplashActivity activity = reference.get();
        if (activity == null) {
            return;
        }
        if (activity.isFinishing()) {
            return;
        }
        if (activity.isDestroyed()) {
            return;
        }
        sReference = null;
        activity.finish();
    }


}
