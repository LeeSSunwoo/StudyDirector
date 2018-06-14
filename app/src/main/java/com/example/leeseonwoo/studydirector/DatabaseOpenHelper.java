package com.example.leeseonwoo.studydirector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseOpenHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "city.db";
    public static final String TABLE_NAME1 = "City";


    public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "create table "+TABLE_NAME1+" (_id integer PRIMARY KEY autoincrement,"+
                " Name text not null, CountryCode text, District text, Population integer)";
        try{
            db.execSQL(createTable1);
            Log.d(TAG, "Create Table1 City");
        }catch (Exception ex){
            Log.e(TAG, "Exception in CREATE_SQL", ex);
        }
        Log.w(TAG, "Create database");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.w(TAG, "opened database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ".");
    }
}