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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * This database tracks the number of all local files  This is used to drive
 * the total datas
 */
public class LocalDataStore extends SQLiteOpenHelper {
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
        builder.append(LocalDataColumns.SELECTED);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.CAN_READ);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.CAN_WRITE);
        builder.append(" int default 0,");
        builder.append(LocalDataColumns.IS_HIDDEN);
        builder.append(" int default 0 );");

        db.execSQL(builder.toString());
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

    }

}
