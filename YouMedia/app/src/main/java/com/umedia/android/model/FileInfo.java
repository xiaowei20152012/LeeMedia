package com.umedia.android.model;


import com.umedia.android.util.MediaFile;
import com.umedia.android.util.MimeUtils;

public class FileInfo {

    public static final int TYPE_ALL = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_PICTURE = 3;
    public static final int TYPE_DOC = 4;
    public static final int TYPE_OTHER = 5;
    public static final int TYPE_APK = 6;
    public static final int TYPE_THEME = 7;
    public static final int TYPE_ZIP = 8;

    private static String APK_EXT = "apk";
    private static String THEME_EXT = "mtz";
    private static String[] ZIP_EXTS = new String[]{
            "zip", "rar"
    };

    public String fileName;

    public String filePath;

    public long fileSize;

    public boolean isDir;

    public int count;

    public long modifiedDate;

    public boolean selected;

    public boolean canRead;

    public boolean canWrite;

    public boolean isHidden;

    public long dbId; // id in the database, if is from database

    public int fileType = TYPE_ALL;


    public static int getFileTypeFromPath(String path) {
        MediaFile.MediaFileType type = MediaFile.getFileType(path);
        if (type != null) {
            if (MediaFile.isAudioFileType(type.fileType)) {
                return FileInfo.TYPE_MUSIC;
            }
            if (MediaFile.isVideoFileType(type.fileType)) {
                return FileInfo.TYPE_VIDEO;
            }
            if (MediaFile.isImageFileType(type.fileType)) {
                return FileInfo.TYPE_PICTURE;
            }
            if (MimeUtils.sDocMimeTypesSet.contains(type.mimeType)) {
                return FileInfo.TYPE_DOC;
            }
        }

        int dotPosition = path.lastIndexOf('.');
        if (dotPosition < 0) {
            return FileInfo.TYPE_OTHER;
        }

        String ext = path.substring(dotPosition + 1);
        if (ext.equalsIgnoreCase("apk")) {
            return FileInfo.TYPE_APK;
        }
        if (ext.equalsIgnoreCase("mtz")) {
            return FileInfo.TYPE_THEME;
        }

        if (matchExts(ext, ZIP_EXTS)) {
            return FileInfo.TYPE_ZIP;
        }

        return FileInfo.TYPE_OTHER;
    }

    private static boolean matchExts(String ext, String[] exts) {
        for (String ex : exts) {
            if (ex.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }


    //*******getter setter*********

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public boolean isMusic() {
        return fileType == TYPE_MUSIC;
    }

    public boolean isVideo() {
        return fileType == TYPE_VIDEO;
    }

    public boolean isPicture() {
        return fileType == TYPE_PICTURE;
    }

    public boolean isDoc() {
        return fileType == TYPE_DOC;
    }

    public boolean isZip() {
        return fileType == TYPE_ZIP;
    }

    public boolean isApk() {
        return fileType == TYPE_APK;
    }

    public boolean isOther() {
        return fileType == TYPE_OTHER;
    }

    public boolean isTheme() {
        return fileSize == TYPE_THEME;
    }

}
