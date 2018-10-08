package com.umedia.android;

import android.app.Application;
import android.os.Build;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.kabouzeid.appthemehelper.ThemeStore;
import com.squareup.leakcanary.LeakCanary;
import com.umedia.android.appshortcuts.DynamicShortcutManager;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * APP Install
 */
public class App extends Application {
    public static final String TAG = App.class.getSimpleName();

    public static final String GOOGLE_PLAY_LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjMeADN5Ffnt/ml5SYxNPCn8kGcOYGpHEfNSCts99vVxqmCn6C01E94c17j7rUK2aeHur5uxphZylzopPlQ8P8l1fqty0GPUNRSo18FCJzfGH8HZAwZYOcnRFPaXdaq3InyFJhBiODh2oeAcVK/idH6QraQ4r9HIlzigAg6lgwzxl2wJKDh7X/GMdDntCyzDh8xDQ0wIawFgvgojHwqh2Ci8Gnq6EYRwPA9yHiIIksT8Q30QyM5ewl5QcnWepsls7enNqeHarhpmSibRUDgCsxHoOpny7SyuvZvUI3wuLckDR0ds9hrt614scHHqDOBp/qWCZiAgOPVAEQcURbV09qQIDAQAB";
    public static final String PRO_VERSION_PRODUCT_ID = "pro_version";

    private static App app;

    private BillingProcessor billingProcessor;

    @Override
    public void onCreate() {
        super.onCreate();
        if (app == null) {
            app = this;
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
            // default theme
            if (!ThemeStore.isConfigured(this, 1)) {
                ThemeStore.editTheme(this)
                        .activityTheme(R.style.Theme_Phonograph_Light)
                        .primaryColorRes(R.color.md_indigo_500)
                        .accentColorRes(R.color.md_pink_A400)
                        .commit();
            }
            AsyncTask.execute(new ProgressInit());
        }
    }

    private void progressInit() {
        // Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, crashlyticsKit, new Answers());
        } else {
            Fabric.with(this, crashlyticsKit, new Crashlytics(), new Crashlytics()); // crashlytics kit is disabled here
        }

        // Set up dynamic shortcuts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            new DynamicShortcutManager(this).initDynamicShortcuts();
        }

        // automatically restores purchases
        billingProcessor = new BillingProcessor(this, App.GOOGLE_PLAY_LICENSE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
            }

            @Override
            public void onPurchaseHistoryRestored() {
//                Toast.makeText(App.this, R.string.restored_previous_purchase_please_restart, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {
            }

            @Override
            public void onBillingInitialized() {
            }
        });
    }

    public static boolean isProVersion() {
        return BuildConfig.DEBUG || app.billingProcessor.isPurchased(PRO_VERSION_PRODUCT_ID);
    }

    public static App getInstance() {
        return app;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        billingProcessor.release();
    }


    private class ProgressInit implements Runnable {

        @Override
        public void run() {
            progressInit();
        }
    }
}
