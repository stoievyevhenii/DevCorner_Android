package com.stoiev.devcorner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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

    ////////////////////////////////

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_execute = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_LOGIN + " TEXT, " + COL_PRIVILEGE + " INTEGER, " + COL_SYSTEM_STATUS + " INTEGER)";
        sqLiteDatabase.execSQL(create_table_execute);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
