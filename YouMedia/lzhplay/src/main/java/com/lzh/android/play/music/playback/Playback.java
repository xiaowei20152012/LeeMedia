package com.lzh.android.play.music.playback;


import android.support.annotation.Nullable;

public interface Playback {

    boolean setDataSource(String path);

    void setNextDataSource(@Nullable String path);

    void setCallbacks(PlaybackCallbacks callbacks);

    boolean isInitialized();

    boolean start();

    void stop();

    void release();

    boolean pause();

    boolean isPlaying();

    int duration();

    int position();

    int seek(int whereTo);

    boolean setVolume(float volume);

    boolean setAudioSessionId(int sessionId);

    int getAudioSessionId();


    interface PlaybackCallbacks {
        void onTrackWentToNext();

        void onTrackEnded();
    }
}
