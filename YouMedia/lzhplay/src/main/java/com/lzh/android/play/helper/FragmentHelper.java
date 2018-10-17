package com.lzh.android.play.helper;


import com.lzh.android.play.MainActivity;

public class FragmentHelper {
    MainActivity activity;
    public static int MUSIC_FRAGMENT = 0x1;
    public static int VIDEO_FRAGMENT = 0x2;
    public static int FOLDER_FRAGMENT = 0x3;
    public static int HELP_FRAGMENT = 0x4;

    public FragmentHelper(MainActivity activity) {
        this.activity = activity;
    }


    public interface FagmentCallback {
        boolean handleBackPress();
    }
}
