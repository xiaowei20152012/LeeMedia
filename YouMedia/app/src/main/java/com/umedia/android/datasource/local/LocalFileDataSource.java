package com.umedia.android.datasource.local;

import android.content.Context;
import android.support.annotation.Nullable;

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
    private ListFilesAsyncTask asyncTask;
    private LinkedList<WeakReference<OnViewCallback>> weakListeners;
    private LocalDataInfo localDataInfo;

    private LocalFileDataSource() {
        weakListeners = new LinkedList<>();
        localDataInfo = new LocalDataInfo();
    }

    public static synchronized LocalFileDataSource getInstance() {
        if (instance == null) {
            instance = new LocalFileDataSource();
        }
        return instance;
    }

    public void registerListener(OnViewCallback listener) {
        weakListeners.add(new WeakReference<>(listener));
    }

    public void unregisterListener(OnViewCallback listener) {
        Iterator<WeakReference<OnViewCallback>> iterator = weakListeners.iterator();
        while (iterator.hasNext()) {
            WeakReference<OnViewCallback> next = iterator.next();
            OnViewCallback sourceListener = next.get();
            if (sourceListener == null || sourceListener == listener) {
                iterator.remove();
            }
        }
    }

    public ArrayList<FileInfo> getVideoInfos() {
        return localDataInfo.getVideoInfos();
    }

    public ArrayList<FileInfo> getAudioInfos() {
        return localDataInfo.getAudioInfos();
    }

    public ArrayList<FileInfo> getImageInfos() {
        return localDataInfo.getImageInfos();
    }

    public ArrayList<FileInfo> getNormalInfos() {
        return localDataInfo.getTotalInfos();
    }

    public ArrayList<FileInfo> getNormalInfos(Context context) {
        return ListFilesAsyncTask.quaryAll(context).getTotalInfos();
    }

    public ArrayList<FileInfo> getOtherInfos() {
        return localDataInfo.getOtherInfos();
    }

    /**
     * 回调处理view
     */
    public interface OnViewCallback {

        void onPreLoaded();

        void onLoaded();

        void onLoading();

        void onLoadingError(Throwable error, String msg);

        void onDataChanged();

    }

    public void loadData(Context context) {
        asyncTask = new ListFilesAsyncTask(context, new ListFilesAsyncTask.OnDataCallback() {
            @Override
            public void onPreLoaded(LocalDataInfo dataInfo) {
                localDataInfo = dataInfo;
                onPreLoadView();
            }

            @Override
            public void onLoaded(@Nullable LocalDataInfo dataInfo) {
                localDataInfo = dataInfo;
                onLoadedView();
            }

            @Override
            public void onLoadingError(@Nullable LocalDataInfo dataInfo, Throwable error) {
                localDataInfo = dataInfo;
                onLoadErrorView();
            }

            @Override
            public void onDataChanged(@Nullable LocalDataInfo dataInfo) {

            }
        });
        asyncTask.execute();
    }

    public void release() {
        asyncTask.onRelease();
    }

    //************private method **********************

    private void onPreLoadView() {
        for (WeakReference<OnViewCallback> listener : weakListeners) {
            OnViewCallback sourceListener = listener.get();
            if (sourceListener != null) {
                sourceListener.onPreLoaded();
            }
        }
    }

    private void onLoadedView() {
        for (WeakReference<OnViewCallback> listener : weakListeners) {
            OnViewCallback sourceListener = listener.get();
            if (sourceListener != null) {
                sourceListener.onLoaded();
            }
        }
    }

    private void onLoadErrorView() {
        for (WeakReference<OnViewCallback> listener : weakListeners) {
            OnViewCallback sourceListener = listener.get();
            if (sourceListener != null) {
                sourceListener.onLoadingError(null, null);
            }
        }
    }

}
