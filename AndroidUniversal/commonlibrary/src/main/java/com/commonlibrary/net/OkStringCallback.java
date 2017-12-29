package com.commonlibrary.net;


import android.text.TextUtils;

import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class OkStringCallback extends OkCallback<String> {
    @Override
    public String onAsyncResponse(final Response response) throws IOException {
        String resp = response.body().string();
        if (TextUtils.isEmpty(resp)) {
            return "";
        }
        return resp;
    }
}
