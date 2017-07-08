package com.gnuftw.bestandworst;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "bestandworst.db";
    public static final int DB_VERSION = 2;

    private static final String DB_CREATE_TABLE =
            "CREATE TABLE " + DbContract.DbEntry.TABLE_NAME + " (" +
            DbContract.DbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DbContract.DbEntry.COLUMN_DATE + " TEXT," +
            DbContract.DbEntry.COLUMN_BEST + " TEXT," +
            DbContract.DbEntry.COLUMN_WORST + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbContract.DbEntry.TABLE_NAME;

    DbOpenHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("DbOpenHelper", "Database was just created in constructor.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DbOpenHelper", "Executing SQL to create database...");
        try {
            db.execSQL(DB_CREATE_TABLE);
        } catch(SQLException sqlException) {
            Log.e("DbOpenHelper", "SQLException", sqlException);
        }
        Log.d("DbOpenHelper", "Database created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DbOpenHelper", "Database is being upgraded.");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
