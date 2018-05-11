package com.umedia.ad.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.umedia.ad.service.playback.IMusicPlayer;


public class MusicService extends Service implements IMusicPlayer.PlaybackCallbacks {
    private MultiPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MultiPlayer(this);
    }

    public class MusicBinder extends Binder {
        @NonNull
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    @Override
    public void onTrackWentToNext() {

    }

    @Override
    public void onTrackEnded() {

    }


}
