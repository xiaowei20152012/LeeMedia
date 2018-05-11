package com.umedia.ad;


import android.annotation.SuppressLint;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class App extends Application {
    private boolean init = false;
    @SuppressLint("StaticFieldLeak")
    public static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!init) {
            init = true;
            init();
        }
    }

    private void init() {
        context = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        billingProcessor.release();//release something
    }
}
