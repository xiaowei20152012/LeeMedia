/*
* Copyright (C) 2014 The CyanogenMod Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.umedia.android.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.umedia.android.datasource.local.LocalDataInfo;
import com.umedia.android.model.FileInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This database tracks the number of all local files  This is used to drive
 * the total datas
 */
public class LocalDataStore extends SQLiteOpenHelper {
    private static final int MAX_ITEMS_IN_DB = 500;

    @Nullable
    private static LocalDataStore instance = null;

    private static final String DATABASE_NAME = "localdata.db";
    private static final String TABLE_NAME = "local_table";
    private static final int VERSION = 1;


    private LocalDataStore(final Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase db) {
        // create the play count table
        // WARNING: If you change the order of these columns
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(TABLE_NAME);
        builder.append("(");

        builder.append(LocalDataColumns.ID);
        builder.append(" integer primary key autoincrement,");

        builder.append(LocalDataColumns.FILE_NAME);
        builder.append(" varchar(50),");

        builder.append(LocalDataColumns.FILE_PATH);
        builder.append(" varchar(100),");

        builder.append(LocalDataColumns.FILE_SIZE);
        builder.append(" long default 0,");
        builder.append(LocalDataColumns.IS_DIR);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.COUNT);
        builder.append(" int ,");
        builder.append(LocalDataColumns.MODIFIED_DATE);
        builder.append(" long,");
        builder.append(LocalDataColumns.TIME_PLAYED);
        builder.append(" long,");
        builder.append(LocalDataColumns.SELECTED);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.FILE_TYPE);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.CAN_READ);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.CAN_WRITE);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.IS_HIDDEN);
        builder.append(" int default 0 );");

        db.execSQL(builder.toString());
        /*
        * ID
        * FILE_NAME
        * FILE_PATH
        * FILE_SIZE
        * IS_DIR
        * COUNT
        * MODIFIED_DATE
        * TIME_PLAYED
        * SELECTED
        * FILE_TYPE
        * CAN_READ
        * CAN_WRITE
        * IS_HIDDEN
        * */
    }

    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase db, final int oldVersion, final int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + Columns.NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // If we ever have downgrade, drop the table to be safe
//        db.execSQL("DROP TABLE IF EXISTS " + Columns.NAME);
        onCreate(db);
    }

    /**
     * @param context The {@link Context} to use
     * @return A new instance of this class.
     */
    @NonNull
    public static synchronized LocalDataStore getInstance(@NonNull final Context context) {
        if (instance == null) {
            instance = new LocalDataStore(context.getApplicationContext());
        }
        return instance;
    }


    public void addFileInfo(final FileInfo fileInfo) {
        if (TextUtils.isEmpty(fileInfo.getFileName())) {
            return;
        }

        final SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();

        try {
            // remove previous entries
            removeFileInfo(fileInfo.getFileName());

            // add the entry
            final ContentValues values = new ContentValues(2);
            values.put(LocalDataColumns.FILE_NAME, fileInfo.getFileName());
            values.put(LocalDataColumns.FILE_PATH, fileInfo.getFilePath());
            values.put(LocalDataColumns.FILE_SIZE, fileInfo.getFileSize());
            values.put(LocalDataColumns.MODIFIED_DATE, fileInfo.getModifiedDate());
            values.put(LocalDataColumns.IS_DIR, fileInfo.getDir());
            values.put(LocalDataColumns.IS_HIDDEN, fileInfo.getHidden());
            values.put(LocalDataColumns.COUNT, fileInfo.getCount());
            values.put(LocalDataColumns.SELECTED, fileInfo.getSelected());
            values.put(LocalDataColumns.CAN_READ, fileInfo.getCanRead());
            values.put(LocalDataColumns.CAN_WRITE, fileInfo.getCanWrite());
            values.put(LocalDataColumns.FILE_TYPE, fileInfo.getFileType());
            values.put(LocalDataColumns.TIME_PLAYED, System.currentTimeMillis());
            database.insert(TABLE_NAME, null, values);

            // if our db is too large, delete the extra items
            Cursor oldest = null;
            try {
                oldest = database.query(TABLE_NAME,
                        new String[]{LocalDataColumns.TIME_PLAYED}, null, null, null, null,
                        LocalDataColumns.TIME_PLAYED + " ASC");

                if (oldest != null && oldest.getCount() > MAX_ITEMS_IN_DB) {
                    oldest.moveToPosition(oldest.getCount() - MAX_ITEMS_IN_DB);
                    long timeOfRecordToKeep = oldest.getLong(0);

                    database.delete(TABLE_NAME,
                            LocalDataColumns.TIME_PLAYED + " < ?",
                            new String[]{String.valueOf(timeOfRecordToKeep)});

                }
            } finally {
                if (oldest != null) {
                    oldest.close();
                }
            }
        } finally {
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }

    public void addFileInfos(final List<FileInfo> fileInfos) {
        try {
            final SQLiteDatabase database = getWritableDatabase();
            database.beginTransaction();
            for (FileInfo fileInfo : fileInfos) {
                if (TextUtils.isEmpty(fileInfo.getFileName())) {
                    return;
                }

                try {
                    // remove previous entries
                    checkRemoveFileInfo(database, fileInfo.getFileName());

                    // add the entry
                    final ContentValues values = new ContentValues(2);
                    values.put(LocalDataColumns.FILE_NAME, fileInfo.getFileName());
                    values.put(LocalDataColumns.FILE_PATH, fileInfo.getFilePath());
                    values.put(LocalDataColumns.FILE_SIZE, fileInfo.getFileSize());
                    values.put(LocalDataColumns.MODIFIED_DATE, fileInfo.getModifiedDate());
                    values.put(LocalDataColumns.IS_DIR, fileInfo.getDir());
                    values.put(LocalDataColumns.IS_HIDDEN, fileInfo.getHidden());
                    values.put(LocalDataColumns.COUNT, fileInfo.getCount());
                    values.put(LocalDataColumns.SELECTED, fileInfo.getSelected());
                    values.put(LocalDataColumns.CAN_READ, fileInfo.getCanRead());
                    values.put(LocalDataColumns.CAN_WRITE, fileInfo.getCanWrite());
                    values.put(LocalDataColumns.FILE_TYPE, fileInfo.getFileType());
                    values.put(LocalDataColumns.TIME_PLAYED, System.currentTimeMillis());
                    database.insert(TABLE_NAME, null, values);

                    // if our db is too large, delete the extra items
                    Cursor oldest = null;
                    try {
                        oldest = database.query(TABLE_NAME,
                                new String[]{LocalDataColumns.TIME_PLAYED}, null, null, null, null,
                                LocalDataColumns.TIME_PLAYED + " ASC");

                        if (oldest != null && oldest.getCount() > MAX_ITEMS_IN_DB) {
                            oldest.moveToPosition(oldest.getCount() - MAX_ITEMS_IN_DB);
                            long timeOfRecordToKeep = oldest.getLong(0);

                            database.delete(TABLE_NAME,
                                    LocalDataColumns.TIME_PLAYED + " < ?",
                                    new String[]{String.valueOf(timeOfRecordToKeep)});

                        }
                    } finally {
                        if (oldest != null) {
                            oldest.close();
                        }
                    }
                } catch (Exception ignore) {
                }
            }
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Exception ignore) {
        }
    }

    public List<FileInfo> queryAllFileInfo(int fileType) {
        ArrayList<FileInfo> list = new ArrayList<>(1);
        final SQLiteDatabase database = getReadableDatabase();
        String[] columns = new String[]{LocalDataColumns.ID, LocalDataColumns.FILE_NAME, LocalDataColumns.FILE_PATH,
                LocalDataColumns.FILE_SIZE, LocalDataColumns.IS_DIR, LocalDataColumns.COUNT, LocalDataColumns.MODIFIED_DATE,
                LocalDataColumns.TIME_PLAYED, LocalDataColumns.SELECTED, LocalDataColumns.FILE_TYPE,
                LocalDataColumns.CAN_READ, LocalDataColumns.CAN_WRITE, LocalDataColumns.IS_HIDDEN};
        String selection = LocalDataColumns.FILE_TYPE + "=?";
        String[] selectionArgs = new String[]{"" + fileType};
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_NAME, columns, null, null, null, null, LocalDataColumns.TIME_PLAYED + " DESC");
            while (cursor.moveToNext()) {
                FileInfo fileInfo = new FileInfo();
                int dbId = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.ID)); // id in the database, if is from database
                String fileName = cursor.getString(cursor.getColumnIndex(LocalDataColumns.FILE_NAME));
                String filePath = cursor.getString(cursor.getColumnIndex(LocalDataColumns.FILE_PATH));
                long fileSize = cursor.getLong(cursor.getColumnIndex(LocalDataColumns.FILE_SIZE));
                int isDir = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.IS_DIR));
                int count = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.COUNT));
                long modifiedDate = cursor.getLong(cursor.getColumnIndex(LocalDataColumns.MODIFIED_DATE));
                long timePlayed = cursor.getLong(cursor.getColumnIndex(LocalDataColumns.TIME_PLAYED));
                int selected = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.SELECTED));
                int fileTyp = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.FILE_TYPE));
                int canRead = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.CAN_READ));
                int canWrite = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.CAN_WRITE));
                int isHidden = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.IS_HIDDEN));
                fileInfo.setDbId(dbId);
                fileInfo.setFileName(fileName);
                fileInfo.setFilePath(filePath);
                fileInfo.setFileSize(fileSize);
                fileInfo.setDir(isDir);
                fileInfo.setCount(count);
                fileInfo.setModifiedDate(modifiedDate);
                fileInfo.setTimePlayed(timePlayed);
                fileInfo.setSelected(selected);
                fileInfo.setFileType(fileTyp);
                fileInfo.setCanRead(canRead);
                fileInfo.setCanWrite(canWrite);
                fileInfo.setHidden(isHidden);
                list.add(fileInfo);
            }
        } catch (Exception ignore) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public LocalDataInfo queryAllFileInfo() {
        LocalDataInfo localDataInfo = new LocalDataInfo();
        final SQLiteDatabase database = getReadableDatabase();
        String[] columns = new String[]{LocalDataColumns.ID, LocalDataColumns.FILE_NAME, LocalDataColumns.FILE_PATH,
                LocalDataColumns.FILE_SIZE, LocalDataColumns.IS_DIR, LocalDataColumns.COUNT, LocalDataColumns.MODIFIED_DATE,
                LocalDataColumns.TIME_PLAYED, LocalDataColumns.SELECTED, LocalDataColumns.FILE_TYPE,
                LocalDataColumns.CAN_READ, LocalDataColumns.CAN_WRITE, LocalDataColumns.IS_HIDDEN};
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_NAME, columns, null, null, null, null, LocalDataColumns.TIME_PLAYED + " DESC");
            while (cursor.moveToNext()) {
                FileInfo fileInfo = new FileInfo();
                int dbId = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.ID)); // id in the database, if is from database
                String fileName = cursor.getString(cursor.getColumnIndex(LocalDataColumns.FILE_NAME));
                String filePath = cursor.getString(cursor.getColumnIndex(LocalDataColumns.FILE_PATH));
                long fileSize = cursor.getLong(cursor.getColumnIndex(LocalDataColumns.FILE_SIZE));
                int isDir = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.IS_DIR));
                int count = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.COUNT));
                long modifiedDate = cursor.getLong(cursor.getColumnIndex(LocalDataColumns.MODIFIED_DATE));
                long timePlayed = cursor.getLong(cursor.getColumnIndex(LocalDataColumns.TIME_PLAYED));
                int selected = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.SELECTED));
                int fileTyp = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.FILE_TYPE));
                int canRead = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.CAN_READ));
                int canWrite = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.CAN_WRITE));
                int isHidden = cursor.getInt(cursor.getColumnIndex(LocalDataColumns.IS_HIDDEN));
                fileInfo.setDbId(dbId);
                fileInfo.setFileName(fileName);
                fileInfo.setFilePath(filePath);
                fileInfo.setFileSize(fileSize);
                fileInfo.setDir(isDir);
                fileInfo.setCount(count);
                fileInfo.setModifiedDate(modifiedDate);
                fileInfo.setTimePlayed(timePlayed);
                fileInfo.setSelected(selected);
                fileInfo.setFileType(fileTyp);
                fileInfo.setCanRead(canRead);
                fileInfo.setCanWrite(canWrite);
                fileInfo.setHidden(isHidden);
                localDataInfo.setDataInfo(fileInfo);
            }
        } catch (Exception ignore) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {//if need
                database.close();
            }
        }

        return localDataInfo;
    }

    public void removeFileInfo(final String fileName) {
        final SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, LocalDataColumns.FILE_NAME + " = ?", new String[]{
                fileName
        });
    }

    public void checkRemoveFileInfo(SQLiteDatabase database, final String fileName) {
        database.delete(TABLE_NAME, LocalDataColumns.FILE_NAME + " = ?", new String[]{
                fileName
        });
    }

    public void clear() {
        final SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }

    public boolean contains(String fileName) {
        final SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,
                new String[]{LocalDataColumns.FILE_NAME},
                LocalDataColumns.FILE_NAME + "=?",
                new String[]{fileName},
                null, null, null, null);

        boolean containsId = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        return containsId;
    }

    public Cursor queryRecentIds() {
        final SQLiteDatabase database = getReadableDatabase();
        return database.query(TABLE_NAME,
                new String[]{LocalDataColumns.TIME_PLAYED}, null, null, null, null,
                LocalDataColumns.TIME_PLAYED + " DESC");
    }


    public interface LocalDataColumns {
        /**
         * CREATE TABLE IF NOT EXISTS local_table (
         * _id integer primary key autoincrement,
         * file_name varchar(50),
         * file_path varchar(100),
         * file_size long default 0,
         * is_dir int default 0,
         * count int ,
         * modifyed_date long,
         * selected int default 0,
         * can_read int default 0,
         * can_write int default 0,
         * is_hidden int default 0);
         */

        String FILE_NAME = "file_name";

        String FILE_PATH = "file_path";

        String FILE_SIZE = "file_size";

        String IS_DIR = "is_dir";

        String COUNT = "count";

        String MODIFIED_DATE = "modifyed_date";

        String SELECTED = "selected";

        String CAN_READ = "can_read";

        String CAN_WRITE = "can_write";

        String IS_HIDDEN = "is_hidden";

        String ID = "_id";

        String TIME_PLAYED = "time_played";

        String FILE_TYPE = "file_type";

    }

}
