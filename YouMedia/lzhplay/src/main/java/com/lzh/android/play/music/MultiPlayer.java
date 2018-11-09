package com.lzh.android.play.music;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lzh.android.play.music.playback.Playback;

public class MultiPlayer implements Playback, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer currentMediaPlayer = new MediaPlayer();
    private MediaPlayer nextMediaPlayer;

    private Context context;

    @Nullable
    private Playback.PlaybackCallbacks callbacks;

    private boolean isInitialized = false;

    public MultiPlayer(final Context context) {
        this.context = context;
        currentMediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp == currentMediaPlayer && nextMediaPlayer != null) {
            isInitialized = false;
            currentMediaPlayer.release();
            currentMediaPlayer = nextMediaPlayer;
            isInitialized = true;
            nextMediaPlayer = null;
            if (callbacks != null) {
                callbacks.onTrackWentToNext();
            }
        } else {
            if (callbacks != null) {
                callbacks.onTrackEnded();
            }
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        isInitialized = false;
        currentMediaPlayer.release();
        ;
        currentMediaPlayer = new MediaPlayer();
        currentMediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
        if (context != null) {

        }
        return false;
    }

    @Override
    public boolean setDataSource(String path) {
        isInitialized = false;
        isInitialized = setDataSourceImpl(currentMediaPlayer, path);
        if (isInitialized) {
            setNextDataSource(null);
        }
        return isInitialized;
    }

    private boolean setDataSourceImpl(@NonNull final MediaPlayer player, @NonNull final String path) {
        if (context == null)
            return false;
        try {
            player.reset();
            player.setOnPreparedListener(null);
            if (path.startsWith("content://")) {
                player.setDataSource(context, Uri.parse(path));
            } else {
                player.setDataSource(path);
            }
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.prepare();
        } catch (Exception e) {
            return false;
        }

        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        final Intent intent = new Intent(AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION);
        intent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, getAudioSessionId());
        intent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, context.getPackageName());
        intent.putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC);
        context.sendBroadcast(intent);
        return true;
    }

    @Override
    public void setNextDataSource(@Nullable String path) {
        if (context == null) {
            return;
        }
        try {
            currentMediaPlayer.setNextMediaPlayer(null);
        } catch (IllegalArgumentException e) {

        } catch (IllegalStateException e) {
            return;
        }
        if (nextMediaPlayer != null) {
            nextMediaPlayer.release();
            nextMediaPlayer = null;
        }
        if (path == null) {
            return;
        }
        // smooth play next

        nextMediaPlayer = new MediaPlayer();
        nextMediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
        nextMediaPlayer.setAudioSessionId(getAudioSessionId());
        if (setDataSourceImpl(nextMediaPlayer, path)) {
            try {
                currentMediaPlayer.setNextMediaPlayer(nextMediaPlayer);
            } catch (IllegalArgumentException | IllegalStateException e) {
                if (nextMediaPlayer != null) {
                    nextMediaPlayer.release();
                    nextMediaPlayer = null;
                }
            }
        } else {
            if (nextMediaPlayer != null) {
                nextMediaPlayer.release();
                nextMediaPlayer = null;
            }
        }

    }

    @Override
    public void setCallbacks(PlaybackCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public boolean start() {
        try {
            currentMediaPlayer.start();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public void stop() {
        currentMediaPlayer.stop();
        isInitialized = false;
    }

    @Override
    public void release() {
        stop();
        currentMediaPlayer.release();
        if (nextMediaPlayer != null) {
            nextMediaPlayer.release();
        }
    }

    @Override
    public boolean pause() {
        try {
            currentMediaPlayer.pause();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public boolean isPlaying() {
        return isInitialized && currentMediaPlayer.isPlaying();
    }

    @Override
    public int duration() {
        if (!isInitialized) {
            return -1;
        }
        try {
            return currentMediaPlayer.getDuration();
        } catch (IllegalStateException e) {
            return -1;
        }
    }

    @Override
    public int position() {
        if (!isInitialized) {
            return -1;
        }
        try {
            return currentMediaPlayer.getCurrentPosition();
        } catch (IllegalStateException e) {
            return -1;
        }
    }

    @Override
    public int seek(int whereTo) {
        try {
            currentMediaPlayer.seekTo(whereTo);
            return whereTo;
        } catch (IllegalStateException e) {
            return -1;
        }
    }

    @Override
    public boolean setVolume(float volume) {
        try {
            currentMediaPlayer.setVolume(volume, volume);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public boolean setAudioSessionId(int sessionId) {
        try {
            currentMediaPlayer.setAudioSessionId(sessionId);
            return true;
        } catch (IllegalStateException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public int getAudioSessionId() {
        return currentMediaPlayer.getAudioSessionId();
    }
}
