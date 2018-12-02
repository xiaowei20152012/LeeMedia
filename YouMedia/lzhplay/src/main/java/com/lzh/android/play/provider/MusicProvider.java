package com.lzh.android.play.provider;


import java.util.ArrayList;

public interface MusicProvider {

    ArrayList getDataSource();

    void onLoaded(ArrayList datas);

    void onDataChanged(ArrayList datas);

}
