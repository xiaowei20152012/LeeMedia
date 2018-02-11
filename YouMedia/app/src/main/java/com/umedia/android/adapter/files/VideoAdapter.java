package com.umedia.android.adapter.files;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialcab.MaterialCab;
import com.kabouzeid.appthemehelper.util.ColorUtil;
import com.kabouzeid.appthemehelper.util.MaterialValueHelper;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.umedia.android.R;
import com.umedia.android.adapter.base.AbsMultiSelectAdapter;
import com.umedia.android.adapter.base.MediaEntryViewHolder;
import com.umedia.android.helper.menu.SongMenuHelper;
import com.umedia.android.interfaces.CabHolder;
import com.umedia.android.model.FileInfo;
import com.umedia.android.util.MusicUtil;

import java.util.ArrayList;

public class VideoAdapter  extends AbsMultiSelectAdapter<VideoAdapter.ViewHolder, FileInfo> implements MaterialCab.Callback, FastScrollRecyclerView.SectionedAdapter {

    public static final String TAG = VideoAdapter.class.getSimpleName();

    protected final AppCompatActivity activity;
    protected ArrayList<FileInfo> dataSet;

    protected int itemLayoutRes;

    protected boolean usePalette = false;
    protected boolean showSectionName = true;

    public VideoAdapter(AppCompatActivity activity, ArrayList<FileInfo> dataSet, @LayoutRes int itemLayoutRes, boolean usePalette, @Nullable CabHolder cabHolder) {
        this(activity, dataSet, itemLayoutRes, usePalette, cabHolder, true);
    }

    public VideoAdapter(AppCompatActivity activity, ArrayList<FileInfo> dataSet, @LayoutRes int itemLayoutRes, boolean usePalette, @Nullable CabHolder cabHolder, boolean showSectionName) {
        super(activity, cabHolder, R.menu.menu_media_selection);
        this.activity = activity;
        this.dataSet = dataSet;
        this.itemLayoutRes = itemLayoutRes;
        this.usePalette = usePalette;
        this.showSectionName = showSectionName;
        setHasStableIds(true);
    }

    public void swapDataSet(ArrayList<FileInfo> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public void usePalette(boolean usePalette) {
        this.usePalette = usePalette;
        notifyDataSetChanged();
    }

    public ArrayList<FileInfo> getDataSet() {
        return dataSet;
    }

    @Override
    public long getItemId(int position) {
        return dataSet.get(position).dbId;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(itemLayoutRes, parent, false);
        return createViewHolder(view);
    }

    protected VideoAdapter.ViewHolder createViewHolder(View view) {
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoAdapter.ViewHolder holder, int position) {
        final FileInfo song = dataSet.get(position);

        boolean isChecked = isChecked(song);
        holder.itemView.setActivated(isChecked);

        if (holder.getAdapterPosition() == getItemCount() - 1) {
            if (holder.shortSeparator != null) {
                holder.shortSeparator.setVisibility(View.GONE);
            }
        } else {
            if (holder.shortSeparator != null) {
                holder.shortSeparator.setVisibility(View.VISIBLE);
            }
        }

        if (holder.title != null) {
            holder.title.setText(getSongTitle(song));
        }
        if (holder.text != null) {
            holder.text.setText(getSongText(song));
        }

        loadAlbumCover(song, holder);

    }

    private void setColors(int color, VideoAdapter.ViewHolder holder) {
        if (holder.paletteColorContainer != null) {
            holder.paletteColorContainer.setBackgroundColor(color);
            if (holder.title != null) {
                holder.title.setTextColor(MaterialValueHelper.getPrimaryTextColor(activity, ColorUtil.isColorLight(color)));
            }
            if (holder.text != null) {
                holder.text.setTextColor(MaterialValueHelper.getSecondaryTextColor(activity, ColorUtil.isColorLight(color)));
            }
        }
    }

    protected void loadAlbumCover(FileInfo song, final VideoAdapter.ViewHolder holder) {
        if (holder.image == null) return;

//        SongGlideRequest.Builder.from(Glide.with(activity), song)
//                .checkIgnoreMediaStore(activity)
//                .generatePalette(activity).build()
//                .into(new PhonographColoredTarget(holder.image) {
//                    @Override
//                    public void onLoadCleared(Drawable placeholder) {
//                        super.onLoadCleared(placeholder);
//                        setColors(getDefaultFooterColor(), holder);
//                    }
//
//                    @Override
//                    public void onColorReady(int color) {
//                        if (usePalette)
//                            setColors(color, holder);
//                        else
//                            setColors(getDefaultFooterColor(), holder);
//                    }
//                });
    }

    protected String getSongTitle(FileInfo song) {
        return song.fileName;
    }

    protected String getSongText(FileInfo song) {
        return song.fileName;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    protected FileInfo getIdentifier(int position) {
        return dataSet.get(position);
    }

    @Override
    protected String getName(FileInfo song) {
        return song.fileName;
    }

    @Override
    protected void onMultipleItemAction(@NonNull MenuItem menuItem, @NonNull ArrayList<FileInfo> selection) {
//        SongsMenuHelper.handleMenuClick(activity, selection, menuItem.getItemId());
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return showSectionName ? MusicUtil.getSectionName(dataSet.get(position).fileName) : "";
    }

    public class ViewHolder extends MediaEntryViewHolder {
        protected int DEFAULT_MENU_RES = SongMenuHelper.MENU_RES;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setImageTransitionName(activity.getString(R.string.transition_album_art));

            if (menu == null) {
                return;
            }
//            menu.setOnClickListener(new SongMenuHelper.OnClickSongMenu(activity) {
//                @Override
//                public Song getSong() {
//                    return VideoAdapter.ViewHolder.this.getSong();
//                }
//
//                @Override
//                public int getMenuRes() {
//                    return getSongMenuRes();
//                }
//
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    return onSongMenuItemClick(item) || super.onMenuItemClick(item);
//                }
//            });
        }

//        protected Song getSong() {
//            return dataSet.get(getAdapterPosition());
//        }
//
//        protected int getSongMenuRes() {
//            return DEFAULT_MENU_RES;
//        }
//
//        protected boolean onSongMenuItemClick(MenuItem item) {
//            if (image != null && image.getVisibility() == View.VISIBLE) {
//                switch (item.getItemId()) {
//                    case R.id.action_go_to_album:
//                        Pair[] albumPairs = new Pair[]{
//                                Pair.create(image, activity.getResources().getString(R.string.transition_album_art))
//                        };
//                        NavigationUtil.goToAlbum(activity, getSong().albumId, albumPairs);
//                        return true;
//                }
//            }
//            return false;
//        }

        @Override
        public void onClick(View v) {
            if (isInQuickSelectMode()) {
                toggleChecked(getAdapterPosition());
            } else {
//                MusicPlayerRemote.openQueue(dataSet, getAdapterPosition(), true);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return toggleChecked(getAdapterPosition());
        }
    }
}
