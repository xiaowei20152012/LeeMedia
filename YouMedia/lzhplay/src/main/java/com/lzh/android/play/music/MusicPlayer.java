package com.lzh.android.play.music;


public interface MusicPlayer {

    void play();

    void pause();

    void playNext();

    void playPrevious();

    void seekTo(long position);

    long getDuration();

}
