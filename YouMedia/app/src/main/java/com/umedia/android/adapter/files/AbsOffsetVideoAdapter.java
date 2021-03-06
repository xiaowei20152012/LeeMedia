package com.umedia.android.adapter.files;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umedia.android.R;
import com.umedia.android.adapter.song.SongAdapter;
import com.umedia.android.helper.MusicPlayerRemote;
import com.umedia.android.interfaces.CabHolder;
import com.umedia.android.model.FileInfo;
import com.umedia.android.model.Song;

import java.util.ArrayList;

/**
 * @author Eugene Cheung (arkon)
 */
public abstract class AbsOffsetVideoAdapter extends VideoAdapter {

    protected static final int OFFSET_ITEM = 0;
    protected static final int SONG = 1;

    public AbsOffsetVideoAdapter(AppCompatActivity activity, ArrayList<FileInfo> dataSet, @LayoutRes int itemLayoutRes, boolean usePalette, @Nullable CabHolder cabHolder) {
        super(activity, dataSet, itemLayoutRes, usePalette, cabHolder);
    }

    public AbsOffsetVideoAdapter(AppCompatActivity activity, ArrayList<FileInfo> dataSet, @LayoutRes int itemLayoutRes, boolean usePalette, @Nullable CabHolder cabHolder, boolean showSectionName) {
        super(activity, dataSet, itemLayoutRes, usePalette, cabHolder, showSectionName);
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == OFFSET_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_list_single_row, parent, false);
            return createViewHolder(view);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected VideoAdapter.ViewHolder createViewHolder(View view) {
        return new AbsOffsetVideoAdapter.ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        position--;
        if (position < 0) return -2;
        return super.getItemId(position);
    }

    @Override
    protected FileInfo getIdentifier(int position) {
        position--;
//        if (position < 0) return Song.EMPTY_SONG;
        return super.getIdentifier(position);
    }

    @Override
    public int getItemCount() {
        int superItemCount = super.getItemCount();
        return superItemCount == 0 ? 0 : superItemCount + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? OFFSET_ITEM : SONG;
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        position--;
        if (position < 0) return "";
        return super.getSectionName(position);
    }

    public class ViewHolder extends VideoAdapter.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        protected FileInfo getSong() {
//            if (getItemViewType() == OFFSET_ITEM) return Song.EMPTY_SONG;
            return dataSet.get(getAdapterPosition() - 1);
        }

        @Override
        public void onClick(View v) {
            if (isInQuickSelectMode() && getItemViewType() != OFFSET_ITEM) {
                toggleChecked(getAdapterPosition());
            } else {
//                MusicPlayerRemote.openQueue(dataSet, getAdapterPosition() - 1, true);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (getItemViewType() == OFFSET_ITEM) return false;
            toggleChecked(getAdapterPosition());
            return true;
        }
    }
}
