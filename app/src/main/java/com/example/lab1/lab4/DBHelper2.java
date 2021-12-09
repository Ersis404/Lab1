package com.example.lab1.lab4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper2 extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "musics";
    public static final String TABLE_M = "music";

    public static final String KEY_ID = "_id";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_MUSIC = "musicName";
    public static final String KEY_DATE = "date";

    public DBHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + TABLE_M);
        db.execSQL("create table " + TABLE_M + "(" + KEY_ID
                + " integer primary key," + KEY_AUTHOR + " text," + KEY_MUSIC + " text," + KEY_DATE + " text" + ")");
        System.out.println("created database");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
