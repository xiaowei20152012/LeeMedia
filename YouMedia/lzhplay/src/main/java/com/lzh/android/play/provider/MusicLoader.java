package com.lzh.android.play.provider;


import java.util.ArrayList;

public class MusicLoader implements MusicProvider {
    private static MusicLoader musicLoader;

    private static MusicLoader create() {
        if (musicLoader != null) {
            return musicLoader;
        }
        musicLoader = new MusicLoader();
        return musicLoader;
    }

    @Override
    public ArrayList getDataSource() {
        return null;
    }

    @Override
    public void onLoaded(ArrayList datas) {

    }

    @Override
    public void onDataChanged(ArrayList datas) {

    }


}
