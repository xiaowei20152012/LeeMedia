package com.sqlite.javalib;

public class myClass {
    public static void main(String[] args) {
        String a = "/storage/emulated/0/cache/SystemUpdater/LogCache/1.17.8/style/sy";
        System.out.print(getPathDeep(a));

    }

    private static int getPathDeep(String path) {
        int deep = 0;
        if (path == null || path == "") {
            return deep;
        }
        if (path.indexOf("/") != -1) {
            String[] count = path.split("/");
            return count.length - 1;
        } else {
            return deep;
        }
    }
}
