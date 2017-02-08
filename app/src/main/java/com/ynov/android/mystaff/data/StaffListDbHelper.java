package com.ynov.android.mystaff.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alex on 23/01/2017.
 */

public class StaffListDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stafflist.db";

    private static final int DATABASE_VERSION = 1;

    public StaffListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_STAFFLIST_TABLE = "CREATE TABLE " +
                StaffListContract.StaffListEntry.TABLE_NAME + " (" +
                StaffListContract.StaffListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                StaffListContract.StaffListEntry.STAFF_NAME + " TEXT NOT NULL, " +
                StaffListContract.StaffListEntry.STAFF_PRESENCE + " TEXT NOT NULL, " +
                StaffListContract.StaffListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_STAFFLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StaffListContract.StaffListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
