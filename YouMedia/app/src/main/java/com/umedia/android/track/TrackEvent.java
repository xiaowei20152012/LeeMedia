package com.umedia.android.track;


import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.ref.WeakReference;

public class TrackEvent {

    private FirebaseAnalytics firebaseAnalytics;
    private WeakReference<Context> weakContext;

    public static TrackEvent getInstance(Context context) {
        return new TrackEvent(context);
    }

    private TrackEvent(Context context) {
        weakContext = new WeakReference<Context>(context);
        firebaseAnalytics = FirebaseAnalytics.getInstance(weakContext.get());
    }

    public void trackEvent(){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

}
