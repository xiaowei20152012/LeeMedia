package com.umedia.ad.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.umedia.ad.service.playback.IMusicPlayer;


public class MusicService extends Service implements IMusicPlayer {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean setDataSource(String path) {
        return false;
    }

    @Override
    public void setNextDataSource(@Nullable String path) {

    }

    @Override
    public void setCallbacks(PlaybackCallbacks callbacks) {

    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {

    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public int position() {
        return 0;
    }

    @Override
    public int seek(int whereto) {
        return 0;
    }

    @Override
    public boolean setVolume(float vol) {
        return false;
    }

    @Override
    public boolean setAudioSessionId(int sessionId) {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
