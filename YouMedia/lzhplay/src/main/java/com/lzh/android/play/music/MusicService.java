package com.lzh.android.play.music;


import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.media.audiofx.AudioEffect;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;

import com.lzh.android.play.music.model.Song;
import com.lzh.android.play.music.playback.Playback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MusicService extends Service implements Playback.PlaybackCallbacks {

    public static final String PACKAGE_NAME = "com.lzh.android.play";

    public static final String MUSIC_PACKAGE_NAME = "com.android.music";

    public static final String ACTION_TOGGLE_PAUSE = PACKAGE_NAME + ".togglepause";
    public static final String ACTION_PLAY = PACKAGE_NAME + ".play";
    public static final String ACTION_PLAY_PLAYLIST = PACKAGE_NAME + ".play.playlist";
    public static final String ACTION_PAUSE = PACKAGE_NAME + ".pause";
    public static final String ACTION_STOP = PACKAGE_NAME + ".stop";
    public static final String ACTION_SKIP = PACKAGE_NAME + ".skip";
    public static final String ACTION_REWIND = PACKAGE_NAME + ".rewind";
    public static final String ACTION_QUIT = PACKAGE_NAME + ".quitservice";
    public static final String INTENT_EXTRA_PLAYLIST = PACKAGE_NAME + "intentextra.playlist";
    public static final String INTENT_EXTRA_SHUFFLE_MODE = PACKAGE_NAME + ".intentextra.shufflemode";

    public static final String APP_WIDGET_UPDATE = PACKAGE_NAME + ".appwidgetupdate";
    public static final String EXTRA_APP_WIDGET_NAME = PACKAGE_NAME + "app_widget_name";

    // do not change these three strings as it will break support with other apps (e.g. last.fm scrobbling)
    public static final String META_CHANGED = PACKAGE_NAME + ".metachanged";
    public static final String QUEUE_CHANGED = PACKAGE_NAME + ".queuechanged";
    public static final String PLAY_STATE_CHANGED = PACKAGE_NAME + ".playstatechanged";

    public static final String REPEAT_MODE_CHANGED = PACKAGE_NAME + ".repeatmodechanged";
    public static final String SHUFFLE_MODE_CHANGED = PACKAGE_NAME + ".shufflemodechanged";
    public static final String MEDIA_STORE_CHANGED = PACKAGE_NAME + ".mediastorechanged";

    public static final String SAVED_POSITION = "POSITION";
    public static final String SAVED_POSITION_IN_TRACK = "POSITION_IN_TRACK";
    public static final String SAVED_SHUFFLE_MODE = "SHUFFLE_MODE";
    public static final String SAVED_REPEAT_MODE = "REPEAT_MODE";

    public static final int RELEASE_WAKELOCK = 0;
    public static final int TRACK_ENDED = 1;
    public static final int TRACK_WENT_TO_NEXT = 2;
    public static final int PLAY_SONG = 3;
    public static final int PREPARE_NEXT = 4;
    public static final int SET_POSITION = 5;
    private static final int FOCUS_CHANGE = 6;
    private static final int DUCK = 7;
    private static final int UNDUCK = 8;
    public static final int RESTORE_QUEUES = 9;

    public static final int SHUFFLE_MODE_NONE = 0;
    public static final int SHUFFLE_MODE_SHUFFLE = 1;

    public static final int REPEAT_MODE_NONE = 0;
    public static final int REPEAT_MODE_ALL = 1;
    public static final int REPEAT_MODE_THIS = 2;

    public static final int SAVE_QUEUES = 0;
    private PowerManager.WakeLock wakeLock;
    private HandlerThread playerHandlerThread;
    private PlaybackHandler playerHandler;
    private ContentObserver mediaStoreObserver;
    private MultiPlayer playback;
    @SuppressWarnings("deprecation")
    private MediaSessionCompat mediaSession;
    private AudioManager audioManager;
    private final AudioManager.OnAudioFocusChangeListener audioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(final int focusChange) {
            playerHandler.obtainMessage(FOCUS_CHANGE, focusChange, 0).sendToTarget();
        }
    };

    private ArrayList<Song> playingQueue = new ArrayList<>();
    private int position;

    //    private ArrayList<audioFocusListener> originalPlayingQueue = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        final PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        wakeLock.setReferenceCounted(false);

        playerHandlerThread = new HandlerThread("PlaybackHander");
        playerHandlerThread.start();

        playerHandler = new PlaybackHandler(this, playerHandlerThread.getLooper());

        playback = new MultiPlayer(this);
        playback.setCallbacks(this);

        mediaStoreObserver = new MediaStoreObserver(playerHandler);
        getContentResolver().registerContentObserver(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, true, mediaStoreObserver);
        getContentResolver().registerContentObserver(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, mediaStoreObserver);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getAction() != null) {
//                restoreQueuesAndPositionIfNecessary();
                String action = intent.getAction();
                switch (action) {
                    case ACTION_TOGGLE_PAUSE:
                        if (isPlaying()) {
                            pause();
                        } else {
                            play();
                        }
                        break;
                    case ACTION_PAUSE:
                        pause();
                        break;
                    case ACTION_PLAY:
                        play();
                        break;
                    case ACTION_PLAY_PLAYLIST:
//                        Playlist playlist = intent.getParcelableExtra(INTENT_EXTRA_PLAYLIST);
//                        int shuffleMode = intent.getIntExtra(INTENT_EXTRA_SHUFFLE_MODE, getShuffleMode());
//                        if (playlist != null) {
//                            ArrayList<Song> playlistSongs;
//                            if (playlist instanceof AbsCustomPlaylist) {
//                                playlistSongs = ((AbsCustomPlaylist) playlist).getSongs(getApplicationContext());
//                            } else {
//                                //noinspection unchecked
//                                playlistSongs = (ArrayList<Song>) (List) PlaylistSongLoader.getPlaylistSongList(getApplicationContext(), playlist.id);
//                            }
//                            if (!playlistSongs.isEmpty()) {
//                                if (shuffleMode == SHUFFLE_MODE_SHUFFLE) {
//                                    int startPosition = 0;
//                                    if (!playlistSongs.isEmpty()) {
//                                        startPosition = new Random().nextInt(playlistSongs.size());
//                                    }
//                                    openQueue(playlistSongs, startPosition, true);
//                                    setShuffleMode(shuffleMode);
//                                } else {
//                                    openQueue(playlistSongs, 0, true);
//                                }
//                            } else {
//                                Toast.makeText(getApplicationContext(), R.string.playlist_is_empty, Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), R.string.playlist_is_empty, Toast.LENGTH_LONG).show();
//                        }
                        break;
                    case ACTION_REWIND:
                        back(true);
                        break;
                    case ACTION_SKIP:
                        playNextSong(true);
                        break;
                    case ACTION_STOP:
                    case ACTION_QUIT:
                        quit();
                        break;
                }
            }
        }

        return START_NOT_STICKY;
    }

    private void back(boolean b) {
    }

    private void pause() {

        if (playback.isPlaying()) {
            playback.pause();
//            notifyChange(PLAY_STATE_CHANGED);
        }
    }

    @Override
    public void onDestroy() {
//        unregisterReceiver(widgetIntentReceiver);
//        if (becomingNoisyReceiverRegistered) {
//            unregisterReceiver(becomingNoisyReceiver);
//            becomingNoisyReceiverRegistered = false;
//        }
        mediaSession.setActive(false);
//        quit();
//        releaseResources();
        getContentResolver().unregisterContentObserver(mediaStoreObserver);
//        PreferenceUtil.getInstance(this).unregisterOnSharedPreferenceChangedListener(this);
        wakeLock.release();

//        sendBroadcast(new Intent("com.kabouzeid.gramophone.PHONOGRAPH_MUSIC_SERVICE_DESTROYED"));
    }

    private void quit() {
        pause();
//        playingNotification.stop();

        closeAudioEffectSession();
        getAudioManager().abandonAudioFocus(audioFocusListener);
        stopSelf();
    }

    private void releaseResources() {
        playerHandler.removeCallbacksAndMessages(null);
        if (Build.VERSION.SDK_INT >= 18) {
            playerHandlerThread.quitSafely();
        } else {
            playerHandlerThread.quit();
        }
//        queueSaveHandler.removeCallbacksAndMessages(null);
//        if (Build.VERSION.SDK_INT >= 18) {
//            queueSaveHandlerThread.quitSafely();
//        } else {
//            queueSaveHandlerThread.quit();
//        }
        playback.release();
        playback = null;
        mediaSession.release();
    }

    public boolean isPlaying() {
        return playback != null && playback.isPlaying();
    }

    public int getPosition() {
        return position;
    }

    public void playNextSong(boolean force) {
//        playSongAt(getNextPosition(force));
    }

    private boolean openTrackAndPrepareNextAt(int position) {
        synchronized (this) {
            return false;
        }
    }

    private boolean openCurrent() {
        synchronized (this) {
//            try {
//                return playback.setDataSource(getTrackUri(getCurrentSong()));
//            } catch (Exception e) {
//                return false;
//            }
            return false;
        }
    }

    private void prepareNext() {
        playerHandler.removeMessages(PREPARE_NEXT);
        playerHandler.obtainMessage(PREPARE_NEXT).sendToTarget();
    }

    private boolean prepareNextImpl() {
        synchronized (this) {
//            try {
//                int nextPosition = getNextPosition(false);
//                playback.setNextDataSource(getTrackUri(getSongAt(nextPosition)));
//                this.nextPosition = nextPosition;
//                return true;
//            } catch (Exception e) {
//                return false;
//            }
            return false;
        }
    }

    private void closeAudioEffectSession() {
        final Intent audioEffectsIntent = new Intent(AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION);
        audioEffectsIntent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, playback.getAudioSessionId());
        audioEffectsIntent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME, getPackageName());
        sendBroadcast(audioEffectsIntent);
    }

    private boolean requestFocus() {
        return (getAudioManager().requestAudioFocus(audioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED);
    }


    private AudioManager getAudioManager() {
        if (audioManager == null) {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }
        return audioManager;
    }

    private void setupMediaSession() {
        ComponentName mediaButtonReceiverComponentName = new ComponentName(getApplicationContext(), MediaButtonIntentReceiver.class);

        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setComponent(mediaButtonReceiverComponentName);

        PendingIntent mediaButtonReceiverPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, mediaButtonIntent, 0);

        mediaSession = new MediaSessionCompat(this, "Phonograph", mediaButtonReceiverComponentName, mediaButtonReceiverPendingIntent);
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                play();
            }

            @Override
            public void onPause() {
                pause();
            }

            @Override
            public void onSkipToNext() {
                playNextSong(true);
            }

            @Override
            public void onSkipToPrevious() {
                back(true);
            }

            @Override
            public void onStop() {
                quit();
            }

            @Override
            public void onSeekTo(long pos) {
                seek((int) pos);
            }

            @Override
            public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
                return MediaButtonIntentReceiver.handleIntent(MusicService.this, mediaButtonEvent);
            }
        });

        mediaSession.setFlags(MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS
                | MediaSession.FLAG_HANDLES_MEDIA_BUTTONS);

        mediaSession.setMediaButtonReceiver(mediaButtonReceiverPendingIntent);
    }

    private void seek(int pos) {

    }

    private final IBinder musicBind = new MusicBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public void onTrackWentToNext() {

    }

    @Override
    public void onTrackEnded() {

    }

    public void play() {
        synchronized (this) {
            if (requestFocus()) {
                if (!playback.isPlaying()) {
                    if (!playback.isInitialized()) {
//                        playSongAt(getPosition());
                    } else {
                        playback.start();
//                        if (!becomingNoisyReceiverRegistered) {
//                            registerReceiver(becomingNoisyReceiver, becomingNoisyReceiverIntentFilter);
//                            becomingNoisyReceiverRegistered = true;
//                        }
//                        if (notHandledMetaChangedForCurrentTrack) {
//                            handleChangeInternal(META_CHANGED);
//                            notHandledMetaChangedForCurrentTrack = false;
//                        }
//                        notifyChange(PLAY_STATE_CHANGED);
//
//                        // fixes a bug where the volume would stay ducked because the AudioManager.AUDIOFOCUS_GAIN event is not sent
//                        playerHandler.removeMessages(DUCK);
//                        playerHandler.sendEmptyMessage(UNDUCK);
                    }
                }
            } else {
//                Toast.makeText(this, getResources().getString(R.string.audio_focus_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    private static final class PlaybackHandler extends Handler {
        private final WeakReference<MusicService> weakService;
        private float currentDuckVolume = 1.0f;

        public PlaybackHandler(final MusicService service, @NonNull final Looper looper) {
            super(looper);
            this.weakService = new WeakReference<MusicService>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            final MusicService service = this.weakService.get();
            if (service == null) {
                return;
            }

            switch (msg.what) {

            }
        }
    }

    private class MediaStoreObserver extends ContentObserver implements Runnable {
        // milliseconds to delay before calling refresh to aggregate events
        private static final long REFRESH_DELAY = 500;
        private Handler mHandler;

        public MediaStoreObserver(Handler handler) {
            super(handler);
            mHandler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            // if a change is detected, remove any scheduled callback
            // then post a new one. This is intended to prevent closely
            // spaced events from generating multiple refresh calls
            mHandler.removeCallbacks(this);
            mHandler.postDelayed(this, REFRESH_DELAY);
        }

        @Override
        public void run() {
            // actually call refresh when the delayed callback fires
            // do not send a sticky broadcast here
//            handleAndSendChangeInternal(MEDIA_STORE_CHANGED);
        }
    }
}
