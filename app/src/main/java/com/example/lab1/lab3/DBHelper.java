package com.example.lab1.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;

public class DBHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "studentsDb";
    public static final String TABLE_S = "students";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "fio";
    public static final String KEY_DATE = "date";
    public static final String KEY_F = "f";
    public static final String KEY_I = "i";
    public static final String KEY_O = "o";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + TABLE_S);
        db.execSQL("create table " + TABLE_S + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_DATE + " text" + ")");
        System.out.println("created database");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Upgraded database");
            if ( oldVersion == 2 && newVersion == 3) {

            db.execSQL("drop table if exists " + TABLE_S);

            db.beginTransaction();
            try {
                db.execSQL("create table " + TABLE_S + "(" + KEY_ID
                        + " integer primary key," + KEY_F + " text," + KEY_I + " text,"+ KEY_O + " text,"+ KEY_DATE + " text" + ")");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
}
