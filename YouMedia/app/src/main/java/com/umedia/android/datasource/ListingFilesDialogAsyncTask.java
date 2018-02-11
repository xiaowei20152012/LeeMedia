package com.umedia.android.datasource;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.umedia.android.R;
import com.umedia.android.misc.DialogAsyncTask;

public abstract class ListingFilesDialogAsyncTask<Params, Progress, Result> extends DialogAsyncTask<Params, Progress, Result> {
    public ListingFilesDialogAsyncTask(Context context) {
        super(context);
    }

    public ListingFilesDialogAsyncTask(Context context, int showDelay) {
        super(context, showDelay);
    }

    @Override
    protected Dialog createDialog(@NonNull Context context) {
        return new MaterialDialog.Builder(context)
                .title(R.string.listing_files)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelListener(dialog -> cancel(false))
                .dismissListener(dialog -> cancel(false))
                .negativeText(android.R.string.cancel)
                .onNegative((dialog, which) -> cancel(false))
                .show();
    }
}
