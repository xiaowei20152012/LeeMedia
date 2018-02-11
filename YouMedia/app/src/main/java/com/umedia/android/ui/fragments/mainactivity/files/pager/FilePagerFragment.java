package com.umedia.android.ui.fragments.mainactivity.files.pager;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.umedia.android.ui.fragments.AbsMusicServiceFragment;
import com.umedia.android.ui.fragments.mainactivity.files.FilesFragment;

/**
 * @author Lee (kabouzeid)
 */
public class FilePagerFragment extends AbsMusicServiceFragment {

    /* http://stackoverflow.com/a/2888433 */
    @Override
    public LoaderManager getLoaderManager() {
        return getParentFragment().getLoaderManager();
    }

    public FilesFragment getLibraryFragment() {
        return (FilesFragment) getParentFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
