package com.commonlibrary.net;


import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public abstract class OkGenericsCallback<T> extends OkCallback<T> {
    IGenericsSerializator iGenericsSerializator;

    public OkGenericsCallback(IGenericsSerializator iGenericsSerializator) {
        this.iGenericsSerializator = iGenericsSerializator;
    }

    public OkGenericsCallback() {

    }

    @Override
    protected T onAsyncResponse(final Response response) throws IOException {
        String resp = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) resp;
        }
        if (iGenericsSerializator != null) {
            T bean = iGenericsSerializator.transform(resp, entityClass);
            return bean;
        }
        return new Gson().fromJson(resp, entityClass);
    }
}
