package com.commonlibrary.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public final class ImageLoader {
    public static final void load(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url)
                .noFade()//无淡入淡出效果
                .config(Bitmap.Config.RGB_565)//设置图片制式
//                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)//设置不使用缓存
                .into(imageView);
    }


//    1. 设置内存缓存大小和标记
//
//    Picasso picasso = newPicasso.Builder(this)
//
//            .memoryCache(new LruCache(10 << 20))//设置内存缓存大小10M
//
//            .indicatorsEnabled(true) //设置左上角标记，主要用于测试
//
//            .build();

//    2. 设置Picasso单例模式
//
//Picasso.setSingletonInstance(picasso);

}
