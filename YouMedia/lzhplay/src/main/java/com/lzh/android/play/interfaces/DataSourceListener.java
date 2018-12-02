package com.lzh.android.play.interfaces;


import java.util.ArrayList;

public interface DataSourceListener {

    ArrayList getDataSource();

    void onLoaded(ArrayList datas);

    void onDataChanged(ArrayList datas);

    void onLoadError(Object error);
}
