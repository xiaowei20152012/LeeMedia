package com.umedia.android.ui.fragments.mainactivity.files;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;

import com.umedia.android.R;
import com.umedia.android.adapter.files.ShuffleButtonVideoAdapter;
import com.umedia.android.adapter.files.VideoAdapter;
import com.umedia.android.datasource.local.LocalFileDataSource;
import com.umedia.android.interfaces.LoaderIds;
import com.umedia.android.misc.WrappedAsyncTaskLoader;
import com.umedia.android.model.FileInfo;
import com.umedia.android.ui.fragments.mainactivity.files.pager.FilesPagerRecyclerViewCustomGridSizeFragment;
import com.umedia.android.util.PreferenceUtil;

import java.util.ArrayList;

/**
 */
public class FileVideoFragment extends FilesPagerRecyclerViewCustomGridSizeFragment<VideoAdapter, GridLayoutManager> implements LoaderManager.LoaderCallbacks<ArrayList<FileInfo>> {

    public static final String TAG = FileVideoFragment.class.getSimpleName();

    private static final int LOADER_ID = LoaderIds.FILE_VIDEO_FRAGMENT;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    protected GridLayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), getGridSize());
    }

    @NonNull
    @Override
    protected VideoAdapter createAdapter() {
        int itemLayoutRes = getItemLayoutRes();
        notifyLayoutResChanged(itemLayoutRes);
        boolean usePalette = loadUsePalette();
        ArrayList<FileInfo> dataSet = getAdapter() == null ? new ArrayList<FileInfo>() : getAdapter().getDataSet();

        if (getGridSize() <= getMaxGridSizeForList()) {
            return new ShuffleButtonVideoAdapter(
                    getLibraryFragment().getMainActivity(),
                    dataSet,
                    itemLayoutRes,
                    usePalette,
                    getLibraryFragment());
        }
        return new VideoAdapter(
                getLibraryFragment().getMainActivity(),
                dataSet,
                itemLayoutRes,
                usePalette,
                getLibraryFragment());
    }

    @Override
    protected int getEmptyMessage() {
        return R.string.no_songs;
    }

    @Override
    public void onMediaStoreChanged() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    protected int loadGridSize() {
        return PreferenceUtil.getInstance(getActivity()).getSongGridSize(getActivity());
    }

    @Override
    protected void saveGridSize(int gridSize) {
        PreferenceUtil.getInstance(getActivity()).setSongGridSize(gridSize);
    }

    @Override
    protected int loadGridSizeLand() {
        return PreferenceUtil.getInstance(getActivity()).getSongGridSizeLand(getActivity());
    }

    @Override
    protected void saveGridSizeLand(int gridSize) {
        PreferenceUtil.getInstance(getActivity()).setSongGridSizeLand(gridSize);
    }

    @Override
    public void saveUsePalette(boolean usePalette) {
        PreferenceUtil.getInstance(getActivity()).setSongColoredFooters(usePalette);
    }

    @Override
    public boolean loadUsePalette() {
        return PreferenceUtil.getInstance(getActivity()).songColoredFooters();
    }

    @Override
    public void setUsePalette(boolean usePalette) {
        getAdapter().usePalette(usePalette);
    }

    @Override
    protected void setGridSize(int gridSize) {
        getLayoutManager().setSpanCount(gridSize);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public Loader<ArrayList<FileInfo>> onCreateLoader(int id, Bundle args) {
        return new AsyncFilesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FileInfo>> loader, ArrayList<FileInfo> data) {
        getAdapter().swapDataSet(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FileInfo>> loader) {
        getAdapter().swapDataSet(new ArrayList<FileInfo>());
    }

    private static class AsyncFilesLoader extends WrappedAsyncTaskLoader<ArrayList<FileInfo>> {
        public AsyncFilesLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<FileInfo> loadInBackground() {
            if (null == LocalFileDataSource.getInstance().getVideoInfos()) {
                return LocalFileDataSource.getInstance().getNormalInfos(getContext());
            }
            return LocalFileDataSource.getInstance().getVideoInfos();
        }
    }
}
