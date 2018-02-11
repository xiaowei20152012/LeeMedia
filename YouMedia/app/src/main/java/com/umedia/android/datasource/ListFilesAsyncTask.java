package com.umedia.android.datasource;


import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.umedia.android.datasource.local.LocalDataInfo;
import com.umedia.android.datasource.local.LocalFileUtil;
import com.umedia.android.util.FileUtil;
import com.umedia.android.util.PreferenceUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class ListFilesAsyncTask extends ListingFilesDialogAsyncTask<ListFilesAsyncTask.LoadingInfo, String, LocalDataInfo> {
//    private WeakReference<OnPathsListedCallback> onPathsListedCallbackWeakReference;

    public ListFilesAsyncTask(Context context, OnPathsListedCallback callback) {
        super(context);
//        onPathsListedCallbackWeakReference = new WeakReference<>(callback);
    }
    public ListFilesAsyncTask(Context context) {
        super(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        checkCallbackReference();
    }

    @Override
    protected LocalDataInfo doInBackground(LoadingInfo... params) {
        LocalDataInfo localDataInfo = null;
        try {
//            if (isCancelled() || checkCallbackReference() == null) {
//                return null;
//            }
            if (isCancelled()) {
                return null;
            }

            LoadingInfo info = null;
            if (info==null){
                FileFilter fileFilter = file -> !file.isHidden() && (file.isDirectory() ||
                        LocalFileUtil.fileIsMimeType(file));
                String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                info = new LoadingInfo(new File(sdcardPath),fileFilter);
            }

//            final String[] paths;


            if (info.file.isDirectory()) {
                List<File> files = LocalFileUtil.listLinkedFiles(info.file, info.fileFilter);

                if (isCancelled()) {
                    return null;
                }
                localDataInfo = LocalFileUtil.traverseFiles(files);
//                paths = new String[files.size()];
//                for (int i = 0; i < files.size(); i++) {
//                    File f = files.get(i);
//                    paths[i] = FileUtil.safeGetCanonicalPath(f);
//
//                    if (isCancelled()) {
//                        return paths;
//                    }
//                }

            } else {
//                paths = new String[1];
//                paths[0] = info.file.getPath();
            }

            return localDataInfo;
        } catch (Exception e) {
            e.printStackTrace();
            cancel(false);
            return null;
        }
    }

    @Override
    protected void onPostExecute(LocalDataInfo list) {
        super.onPostExecute(list);
//        OnPathsListedCallback callback = checkCallbackReference();
//        if (callback != null) {
//            callback.onPathsListed(paths);
//        }
    }

//    private OnPathsListedCallback checkCallbackReference() {
//        OnPathsListedCallback callback = onPathsListedCallbackWeakReference.get();
//        if (callback == null) {
//            cancel(false);
//        }
//        return callback;
//    }




    public static class LoadingInfo {
        public final File file;
        public final FileFilter fileFilter;

        public LoadingInfo(File file, FileFilter fileFilter) {
            this.file = file;
            this.fileFilter = fileFilter;
        }
    }

    public interface OnPathsListedCallback {
        void onPathsListed(@Nullable String[] paths);
    }

}
