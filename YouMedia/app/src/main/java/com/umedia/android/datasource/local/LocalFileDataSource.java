package com.umedia.android.datasource.local;

import android.content.Context;

import com.umedia.android.datasource.ListFilesAsyncTask;
import com.umedia.android.model.FileInfo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * provide datasource of local files
 */
public class LocalFileDataSource {

    private static LocalFileDataSource instance;
    private LinkedList<WeakReference<OnCallbackListener>> weakListeners;
    private ArrayList<FileInfo> videoInfos;
    private ArrayList<FileInfo> audioInfos;
    private ArrayList<FileInfo> imageInfos;
    private ArrayList<FileInfo> normalInfos;

    private LocalFileDataSource() {
        weakListeners = new LinkedList<>();
        videoInfos = new ArrayList<>(1);
        audioInfos = new ArrayList<>(1);
        imageInfos = new ArrayList<>(1);
        normalInfos = new ArrayList<>(1);
    }

    public static synchronized LocalFileDataSource getInstance() {
        if (instance == null) {
            instance = new LocalFileDataSource();
        }
        return instance;
    }

    public void registerListener(OnCallbackListener listener) {
        weakListeners.add(new WeakReference<>(listener));
    }

    public void unregisterListener(OnCallbackListener listener) {
//        listeners.remove(listener);

        Iterator<WeakReference<OnCallbackListener>> iterator = weakListeners.iterator();
        while (iterator.hasNext()) {
            WeakReference<OnCallbackListener> next = iterator.next();
            OnCallbackListener sourceListener = next.get();
            if (sourceListener == null || sourceListener == listener) {
                iterator.remove();
            }
        }
    }
//         for (WeakReference<SourceListener> listener : weakListeners) {
//        SourceListener sourceListener = listener.get();
//        if (sourceListener != null)
//            sourceListener.onLoading(DataSource.this);
//    }

    public ArrayList<FileInfo> getVideoInfos() {
        return videoInfos;
    }

    public ArrayList<FileInfo> getAudioInfos() {
        return videoInfos;
    }

    public ArrayList<FileInfo> getImageInfos() {
        return videoInfos;
    }

    public ArrayList<FileInfo> getNormalInfos() {
        return videoInfos;
    }

    public void preLoad(Context context) {
        new ListFilesAsyncTask(context).execute();
    }


    public interface OnCallbackListener {

        void onPreLoaded();

        void onLoaded(List<FileInfo> source);

        void onLoadingError(List<FileInfo> source, Throwable error);

        void onDataChanged(List<FileInfo> source);

    }


    public void release() {

    }

}
