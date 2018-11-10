package com.sqlite.javalib;

import java.util.Random;

public class myClass {
    public static void main(String[] args) {
//        String a = "/storage/emulated/0/cache/SystemUpdater/LogCache/1.17.8/style/sy";
//        System.out.print(getPathDeep(a));
        Random random = new Random();
        int f = random.nextInt(3) + 3;
        int d = random.nextInt(10);
        int a = f * 10 + d;
        System.out.print(a);

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
