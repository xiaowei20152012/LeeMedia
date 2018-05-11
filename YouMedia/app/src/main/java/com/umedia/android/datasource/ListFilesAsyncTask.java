package com.umedia.android.datasource;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.umedia.android.datasource.local.LocalDataInfo;
import com.umedia.android.datasource.local.LocalFileDataSource;
import com.umedia.android.datasource.local.LocalFileUtil;
import com.umedia.android.model.FileInfo;
import com.umedia.android.provider.LocalDataStore;
import com.umedia.android.util.FileUtil;
import com.umedia.android.util.PreferenceUtil;

import java.io.File;
import java.io.FileFilter;
import java.lang.ref.WeakReference;
import java.util.List;

public class ListFilesAsyncTask extends AsyncTask<ListFilesAsyncTask.LoadingInfo, String, LocalDataInfo> {
    private WeakReference<OnDataCallback> onDataListedCallbackWeakReference;
    private WeakReference<Context> contextWeakReference;
    private Handler handler;
    private LocalDataInfo localDataInfo;

    public ListFilesAsyncTask(Context context, OnDataCallback callback) {
//        super(context);
        this.contextWeakReference = new WeakReference<Context>(context);
        onDataListedCallbackWeakReference = new WeakReference<>(callback);
        handler = new Handler(Looper.getMainLooper());
    }

    public void onRelease() {
        cancel(false);
        handler = null;
        localDataInfo = null;
        onDataListedCallbackWeakReference = null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        checkCallbackReference();
    }

    @Override
    protected LocalDataInfo doInBackground(LoadingInfo... params) {
        try {
            if (checkContextReference() == null || handler == null || isCancelled() || checkCallbackReference() == null) {
                return null;
            }
            localDataInfo = LocalDataStore.getInstance(checkContextReference()).queryAllFileInfo();
            if (handler != null && checkCallbackReference() != null && localDataInfo != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (checkCallbackReference()!=null) {
                            checkCallbackReference().onPreLoaded(localDataInfo);
                        }
                    }
                });
            }

            LoadingInfo info = null;
            if (info == null) {
                FileFilter fileFilter = file -> !file.isHidden() && (file.isDirectory() ||
                        LocalFileUtil.fileIsMimeType(file));
                String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                info = new LoadingInfo(new File(sdcardPath), fileFilter);
            }

            if (info.file.isDirectory()) {
                List<File> files = LocalFileUtil.listLinkedFiles(info.file, info.fileFilter);

                if (isCancelled()) {
                    return null;
                }
                localDataInfo = LocalFileUtil.traverseFiles(files);
                LocalDataStore.getInstance(checkContextReference()).addFileInfos(localDataInfo.getTotalInfos());

            }
            if (checkCallbackReference() != null) {
                handler.post(() -> checkCallbackReference().onLoaded(localDataInfo));
            }
            return localDataInfo;
        } catch (Exception e) {
            e.printStackTrace();
            cancel(false);
            if (checkCallbackReference() != null) {
                handler.post(() -> checkCallbackReference().onLoadingError(localDataInfo, null));
            }
            return null;
        }
    }

    public static LocalDataInfo quaryAll(Context context) {
        return LocalDataStore.getInstance(context).queryAllFileInfo();
    }

    @Override
    protected void onPostExecute(LocalDataInfo list) {
        super.onPostExecute(list);
    }

    private OnDataCallback checkCallbackReference() {
        OnDataCallback callback = onDataListedCallbackWeakReference.get();
        if (callback == null) {
            cancel(false);
        }
        return callback;
    }

    private Context checkContextReference() {
        Context context = contextWeakReference.get();
        if (context == null) {
            cancel(false);
        }
        return context;
    }


    public static class LoadingInfo {
        public final File file;
        public final FileFilter fileFilter;

        public LoadingInfo(File file, FileFilter fileFilter) {
            this.file = file;
            this.fileFilter = fileFilter;
        }
    }

    public interface OnDataCallback {

        void onPreLoaded(LocalDataInfo dataInfo);

        void onLoaded(@Nullable LocalDataInfo dataInfo);

        void onLoadingError(@Nullable LocalDataInfo dataInfo, Throwable error);

        void onDataChanged(@Nullable LocalDataInfo dataInfo);

    }
}
