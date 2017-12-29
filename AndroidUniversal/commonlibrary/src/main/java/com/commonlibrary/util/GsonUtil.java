package com.commonlibrary.util;


import com.google.gson.Gson;

public class GsonUtil {
    private static final Gson gson = new Gson();

    public static Gson gson() {
        return gson;
    }
}
