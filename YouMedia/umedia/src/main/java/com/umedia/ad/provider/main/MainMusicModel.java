package com.umedia.ad.provider.main;


import com.umedia.ad.App;
import com.umedia.ad.model.Song;
import com.umedia.ad.model.SongLoader;
import com.umedia.ad.provider.AsyncLoader;

import java.util.ArrayList;
import java.util.Random;

public class MainMusicModel implements AsyncLoader.LoadCallBack<ArrayList<Song>> {

    public interface Listener {
        void loading();

        void loaded();

        void loadError(String error);
    }

    private AsyncLoader songLoader;
    private ArrayList<Song> mainSongs;
    private Listener listener;

    public static MainMusicModel create(Listener listener) {
        return new MainMusicModel(listener);
    }

    private MainMusicModel(Listener listener) {
        songLoader = new AsyncLoader(this);
        mainSongs = new ArrayList<>(20);
        this.listener = listener;
    }

    public void loadData() {
        if (listener != null) {
            listener.loading();
        }
        if (!mainSongs.isEmpty() && listener != null) {
            listener.loaded();
        }
        songLoader.release();
        songLoader.load();
    }

    public void release() {
        listener = null;
        songLoader.release();
    }


    public ArrayList<Song> getSongs(){
        return mainSongs;
    }


    @Override
    public ArrayList<Song> doInbackground() {
        return SongLoader.getAllSongs(App.context);
    }

    @Override
    public void loaded(ArrayList<Song> result) {

        //check if is empty
        if (mainSongs.isEmpty()) {
            mainSongs.addAll(result);
            loadedSuccess();
        } else if (mainSongs.size() != result.size()) {
            //check is size equal
            mainSongs.clear();
            mainSongs.addAll(result);
            loadedSuccess();
        } else {
            Random random = new Random();
            int first = random.nextInt(mainSongs.size());
            int second = random.nextInt(mainSongs.size());
            int lastId = mainSongs.get(first).id;
            int lastTempId = result.get(first).id;
            int firstId = mainSongs.get(second).id;
            int firstTempId = result.get(second).id;
            if (lastId != lastTempId || firstId != firstTempId) {
                mainSongs.clear();
                mainSongs.addAll(result);
                loadedSuccess();
            }
        }



    }
    private void loadedSuccess(){
        if (listener!=null){
            listener.loaded();
        }
    }

    @Override
    public void loadError(String msg) {
        if (listener != null) {
            listener.loadError(msg);
        }
    }
}
