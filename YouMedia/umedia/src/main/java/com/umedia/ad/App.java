package com.umedia.ad;


import android.annotation.SuppressLint;
import android.app.Application;

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
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        billingProcessor.release();//release something
    }
}
