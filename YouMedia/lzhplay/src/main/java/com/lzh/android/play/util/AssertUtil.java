package com.lzh.android.play.util;


import android.text.TextUtils;

public class AssertUtil {
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || str.toLowerCase().equals("null");
    }

    public static boolean isNull(Object object) {
        return object == null;
    }
}
