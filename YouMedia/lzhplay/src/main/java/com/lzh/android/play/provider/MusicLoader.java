package com.lzh.android.play.provider;


import com.lzh.android.play.interfaces.DataSourceListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class MusicLoader implements MusicProvider {

    private WeakReference<ArrayList<DataSourceListener>> listProviderListener;
    private static MusicLoader musicLoader;

    private static MusicLoader create() {
        if (musicLoader != null) {
            return musicLoader;
        }
        musicLoader = new MusicLoader();

        return musicLoader;
    }

    public MusicLoader() {
        listProviderListener = new WeakReference<ArrayList<DataSourceListener>>(new ArrayList<>());
    }

    @Override
    public ArrayList getDataSource() {
        return null;
    }

    @Override
    public void onLoaded(ArrayList datas) {
        ArrayList<DataSourceListener> listeners = listProviderListener.get();
        if (listeners == null) {
            return;
        }
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onLoaded(datas);
        }
    }

    @Override
    public void onDataChanged(ArrayList datas) {
        ArrayList<DataSourceListener> listeners = listProviderListener.get();
        if (listeners == null) {
            return;
        }
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onDataChanged(datas);
        }
    }

    public void registerLoader(DataSourceListener listener) {
        ArrayList listeners = listProviderListener.get();
        if (listeners != null && listener != null) {
            listeners.add(listener);
        }
    }

    public void unregisterLoader(DataSourceListener listener) {
        ArrayList listeners = listProviderListener.get();
        if (listeners != null && listener != null) {
            listeners.remove(listener);
        }
    }


}
