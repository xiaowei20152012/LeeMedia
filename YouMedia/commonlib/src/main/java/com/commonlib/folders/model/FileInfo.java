package com.commonlib.folders.model;


import com.commonlib.folders.util.MediaFile;
import com.commonlib.folders.util.MimeUtils;

public class FileInfo {
    /*0 false 1 true*/
    private static final int TRUE = 1;
    private static final int FALSE = 0;

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

    private String fileName;

    private String filePath;

    private long fileSize;

    private int isDir;

    private int count;

    private long modifiedDate;

    private int selected;

    private int canRead;

    private int canWrite;

    private int isHidden;

    private int dbId; // id in the database, if is from database

    private int fileType = TYPE_ALL;

    private long timePlayed;


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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isDir() {
        return isDir == TRUE;
    }

    public int getDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir ? TRUE : FALSE;
    }

    public void setDir(int dir) {
        isDir = dir;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isSelected() {
        return selected == TRUE;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected ? TRUE : FALSE;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public boolean isCanRead() {
        return canRead == TRUE;
    }

    public int getCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead ? TRUE : FALSE;
    }

    public void setCanRead(int canRead) {
        this.canRead = canRead;
    }

    public boolean isCanWrite() {
        return canWrite == TRUE;
    }

    public int getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite ? TRUE : FALSE;
    }

    public void setCanWrite(int canWrite) {
        this.canWrite = canWrite;
    }

    public boolean isHidden() {
        return isHidden == TRUE;
    }

    public int getHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden ? TRUE : FALSE;
    }

    public void setHidden(int hidden) {
        isHidden = hidden;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    //*******************end******************


}
