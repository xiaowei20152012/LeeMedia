package com.commonlibrary.net;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;


public class OkApiClient {
    private static volatile OkHttpClient okHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Handler handler;

    private String url;
    private Headers headers;
    private ArrayList<String> disHeaders;

    private RequestBody requestBody;
    private ArrayMap<String, Object> params;

    private boolean post;
    private OkCallback okCallback;
    private Runnable successAction;
    private Runnable failedAction;

    private OkApiClient(Builder builder) {
        this.headers = builder.headers;
        this.disHeaders = builder.disHeaders;
        this.requestBody = builder.requestBody;
        this.params = builder.params;
        this.post = builder.post;
        this.url = builder.uri;
        handler = new Handler(Looper.getMainLooper());
    }

    public void request(OkCallback okCallback) {
        this.okCallback = okCallback;
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Request.Builder builder = new Request.Builder();
        builder.get();
        builder.url(url);
        builder.headers(getHeaders());
        if (post) {
            builder.post(getRequestBody());
        }

        Call call = createClient().newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                OkApiClient.this.onFailure(request, e);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                OkApiClient.this.onResponse(onAsyncResponse(response));
            }
        });
    }

    private RequestBody getRequestBody() {
        if (requestBody != null) {
            return requestBody;
        }
        if (params != null) {
            StringBuilder builder = new StringBuilder();
            int length = params.size();
            for (int i = 0; i < length; i++) {
                builder.append(params.keyAt(i));
                builder.append(params.valueAt(i));
                if (i + 1 != length) {
                    builder.append("&");
                }
            }
            requestBody = RequestBody.create(JSON, builder.toString());
        }
        return requestBody;
    }

    private Headers getHeaders() {
        if (headers != null) {
            return headers;
        }

        if (disHeaders != null && disHeaders.size() > 1) {
            Headers.Builder builder = new Headers.Builder();
            for (int i = 0; i < disHeaders.size(); i += 2) {
                builder.add(disHeaders.get(i), disHeaders.get(i + 1));
            }
            return builder.build();
        }
        return new Headers.Builder().build();
    }

    private OkHttpClient createClient() {
        /*创建okHttpClient对象*/
        if (okHttpClient == null) {
            synchronized (OkApiClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }


    private void onFailure(final Request request, final IOException e) {
        if (okCallback != null) {
            handler.post(getFailRunnable(request, e));
        }
    }

    @NonNull
    private Runnable getFailRunnable(final Request request, final IOException e) {
        if (failedAction == null) {
            failedAction = new Runnable() {
                @Override
                public void run() {
                    okCallback.onFailure(request, e);
                }
            };
        }
        return failedAction;
    }

    private Object onAsyncResponse(final Response response) {
        if (okCallback != null) {
            try {
                return okCallback.onAsyncResponse(response);
            } catch (final IOException e) {
                if (okCallback != null) {
                    handler.post(getFailRunnable(null, e));
                }
            }
        }
        return null;
    }

    private void onResponse(final Object response) {
        if (okCallback != null && response != null) {
            handler.post(getSuccessRunnable(response));
        } else if (okCallback != null) {
            handler.post(getFailRunnable(null, null));
        }

    }

    @NonNull
    private Runnable getSuccessRunnable(final Object response) {
        if (successAction == null) {
            successAction = new Runnable() {
                @Override
                public void run() {
                    okCallback.onSuccess(response);
                }
            };
        }
        return successAction;
    }


    public void release() {
        if (handler != null) {
            handler.removeCallbacks(successAction);
            handler.removeCallbacks(failedAction);
            handler = null;
        }
        if (okCallback != null) {
            okCallback = null;
        }
    }

    public static class Builder {
        private String uri;
        private Headers headers;
        private RequestBody requestBody;
        private ArrayList<String> disHeaders;
        private ArrayMap<String, Object> params;
        private boolean post;

        public Builder url(String url) {
            this.uri = url;
            return this;
        }

        public Builder post() {
            post = true;
            return this;
        }

        public Builder addHeaders(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Builder addRequestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder addHeader(String key, String value) {
            updateHeader(key, value);
            return this;
        }

        public Builder addParam(String key, Object value) {
            updateParams(key, value);
            return this;
        }

        private void updateParams(String key, Object value) {
            if (params == null) {
                params = new ArrayMap<>();
            }
            params.put(key, value);
        }

        private void updateHeader(String key, String value) {
            if (disHeaders == null) {
                disHeaders = new ArrayList<>();
            }
            disHeaders.add(key);
            disHeaders.add(value);
        }

        public OkApiClient build() {
            return new OkApiClient(this);
        }
    }

}
