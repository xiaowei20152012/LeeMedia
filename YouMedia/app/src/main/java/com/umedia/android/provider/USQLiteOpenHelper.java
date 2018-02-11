package com.umedia.android.provider;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

public abstract class USQLiteOpenHelper {

    public USQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    }

    public USQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
    }

    abstract void onCreate(SQLiteDatabase db) ;

    abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) ;

}
