package com.stoiev.devcorner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Todo: create splash screen with method for checking user status in system

public class DatabaseHelper extends SQLiteOpenHelper {

    ////////////////////////////////
    //    Database information    //
    ////////////////////////////////

    private static final String DATABASE_NAME = "UserData.db";
    private static final String TABLE_NAME = "user_table";
    private static final String COL_ID = "ID";
    private static final String COL_LOGIN = "LOGIN";
    private static final String COL_PRIVILEGE = "PRIVILEGE";
    private static final String COL_SYSTEM_STATUS = "SYSTEM_STATUS";
    public static final int DATABASE_VERSION = 1;

    ////////////////////////////////

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LOGIN + " TEXT, " + COL_PRIVILEGE + " INTEGER, " +
                COL_SYSTEM_STATUS + " INTEGER)";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
