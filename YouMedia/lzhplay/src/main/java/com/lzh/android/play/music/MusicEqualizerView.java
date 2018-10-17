package com.lzh.android.play.music;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MusicEqualizerView extends FrameLayout {

    private Context context;


    public MusicEqualizerView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MusicEqualizerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MusicEqualizerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
    }

}
