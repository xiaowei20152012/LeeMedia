package com.umedia.android.datasource.local;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.umedia.android.model.FileInfo;
import com.umedia.android.util.MimeUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LocalFileUtil {

    public static LocalDataInfo traverseFiles(List<File> list) {
        LocalDataInfo localDataInfo = new LocalDataInfo();
        for (File file : list) {
            FileInfo fileInfo = getFileInfo(file);
            if (fileInfo.isMusic()) {
                localDataInfo.setMusicInfo(fileInfo);
            } else if (fileInfo.isVideo()) {
                localDataInfo.setVideoInfo(fileInfo);
            } else if (fileInfo.isPicture()) {
                localDataInfo.setImageInfo(fileInfo);
            } else {
                localDataInfo.setOtherInfo(fileInfo);
            }
        }
        return localDataInfo;
    }

    public static FileInfo getFileInfo(File file) {
//        File lFile = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setCanRead(file.canRead());
        fileInfo.setCanWrite(file.canWrite());
        fileInfo.setHidden(file.isHidden());
        fileInfo.setFileName(file.getName());
//        fileInfo.fileName = Util.getNameFromFilepath(filePath);
        fileInfo.setModifiedDate(file.lastModified());
        fileInfo.setDir(file.isDirectory());
//        fileInfo.filePath = filePath;
        fileInfo.setFilePath(file.getAbsolutePath());
        fileInfo.setFileType(FileInfo.getFileTypeFromPath(fileInfo.getFilePath()));
        fileInfo.setFileSize(file.length());
        return fileInfo;
    }

    @NonNull
    public static List<File> listFilesDataDeep(@NonNull File directory, @Nullable FileFilter fileFilter) {
        List<File> files = new LinkedList<>();
        internalListFilesDataDeep(files, directory, fileFilter);
        return files;
    }

    private static void internalListFilesDataDeep(@NonNull Collection<File> files, @NonNull File directory, @Nullable FileFilter fileFilter) {
        File[] found = directory.listFiles(fileFilter);

        if (found != null) {
            for (File file : found) {
                if (file.isDirectory()) {
                    internalListFilesDataDeep(files, file, fileFilter);
                } else {
                    files.add(file);
                }
            }
        }
    }

    /**
     * 非递归遍历
     */
    public static LinkedList<File> listLinkedFiles(@NonNull File directory, @Nullable FileFilter fileFilter) {
        LinkedList<File> realListFile = new LinkedList<>();
        LinkedList<File> list = new LinkedList<File>();//所有文件夹集合
        File[] file = directory.listFiles(fileFilter);
        for (File file1 : file) {
            if (file1.isDirectory() && isNotDeep(file1.getAbsolutePath())) {
                list.add(file1);
            } else {
                if (file1.exists() && file1.isFile()) {
                    realListFile.add(file1);
                }
            }
        }
        File tmp;
        while (!list.isEmpty()) {
            tmp = (File) list.removeFirst();
            if (tmp.isDirectory() && isNotDeep(tmp.getAbsolutePath())) {
                file = tmp.listFiles(fileFilter);
                if (file == null) {
                    continue;
                }
                for (File file2 : file) {
                    if (file2.isDirectory() && isNotDeep(file2.getAbsolutePath())) {
                        list.add(file2);
                    } else {
                        if (file2.exists() && file2.isFile()) {
                            realListFile.add(file2);
                        }
                    }
                }
            } else {
                if (tmp.exists() && tmp.isFile()) {
                    realListFile.add(tmp);
                }
            }
        }
        return realListFile;
    }

    /**
     * path should not too deep
     * /storage/emulated/0/cache/SystemUpdater/LogCache/1.17.8/style/sy
     *
     * @param path
     * @return
     */
    private static boolean isNotDeep(String path) {
        return getPathDeep(path) <= 5;
    }

    private static int getPathDeep(String path) {
        int deep = 0;
        if (TextUtils.isEmpty(path)) {
            return deep;
        }
        if (path.indexOf("/") != -1) {
            String[] count = path.split("/");
            return count.length - 1;
        } else {
            return deep;
        }
    }

    public static boolean fileIsMimeType(File file) {
        // get the file mime type
        String filename = file.toURI().toString();
        int dotPos = filename.lastIndexOf('.');
        if (dotPos == -1) {
            return false;
        }
        String fileExtension = filename.substring(dotPos + 1).toLowerCase();
        String fileType = MimeUtils.guessMimeTypeFromExtension(fileExtension);
        if (fileType == null) {
            return false;
        }
        return MimeUtils.hasMimeType(fileType);
    }
}
