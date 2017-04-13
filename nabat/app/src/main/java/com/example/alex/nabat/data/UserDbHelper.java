package com.example.alex.nabat.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Junior on 30.03.2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Nabat.db";
    private static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DbContracts.UserEntry.TABLE_NAME + " ("
            + DbContracts.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbContracts.UserEntry.NAME + " TEXT, "
            + DbContracts.UserEntry.EMAIL + " TEXT, "
            + DbContracts.UserEntry.BIRTHDAY + " TEXT, "
            + DbContracts.UserEntry.TOKEN + " TEXT, "
            + DbContracts.UserEntry.LOCATION + " TEXT, "
            + DbContracts.UserEntry.PHONE_NUMBER + " TEXT, "
            + DbContracts.UserEntry.GENDER + " TEXT) ";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DbContracts.UserEntry.TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
