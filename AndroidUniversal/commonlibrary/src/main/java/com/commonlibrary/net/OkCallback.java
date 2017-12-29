package com.commonlibrary.net;


import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class OkCallback<T> {
    public abstract void onFailure(Request request, IOException e);

    public abstract void onSuccess(T response);

    abstract T onAsyncResponse(final Response response) throws IOException;

    protected void onProgress(int progress) {

    }

    protected void onPre() {

    }
}
