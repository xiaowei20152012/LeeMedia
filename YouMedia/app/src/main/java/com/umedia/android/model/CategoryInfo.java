package com.umedia.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.umedia.android.R;

public class CategoryInfo implements Parcelable {
    public Category category;
    public FileCategory fileCategory;
    public boolean visible;

    public CategoryInfo(Category category, boolean visible) {
        this.category = category;
        this.visible = visible;
    }
    public CategoryInfo(FileCategory category, boolean visible) {
        this.fileCategory = category;
        this.visible = visible;
    }
    private CategoryInfo(Parcel source) {
        category = (Category) source.readSerializable();
        visible = source.readInt() == 1;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(category);
        dest.writeInt(visible ? 1 : 0);
    }

    public static final Parcelable.Creator<CategoryInfo> CREATOR = new Parcelable.Creator<CategoryInfo>() {
        public CategoryInfo createFromParcel(Parcel source) {
            return new CategoryInfo(source);
        }

        public CategoryInfo[] newArray(int size) {
            return new CategoryInfo[size];
        }
    };

    public enum Category {
        SONGS(R.string.songs),
        ALBUMS(R.string.albums),
        ARTISTS(R.string.artists),
        GENRES(R.string.genres),
        PLAYLISTS(R.string.playlists);

        public final int stringRes;

        Category(int stringRes) {
            this.stringRes = stringRes;
        }
    }
    public enum FileCategory {
        VIDEO(R.string.songs),
        MUSIC(R.string.albums),
        PICTURE(R.string.artists),
        OTHER(R.string.genres);

        public final int stringRes;

        FileCategory(int stringRes) {
            this.stringRes = stringRes;
        }
    }
}
