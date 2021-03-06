package com.ynov.android.mystaff.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 23/01/2017.
 */

public class FakeData {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(StaffListContract.StaffListEntry.STAFF_NAME, "Pedro Almodovar");
        cv.put(StaffListContract.StaffListEntry.STAFF_PRESENCE, "Active");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StaffListContract.StaffListEntry.STAFF_NAME, "Bernadette Chirac");
        cv.put(StaffListContract.StaffListEntry.STAFF_PRESENCE, "Away");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StaffListContract.StaffListEntry.STAFF_NAME, "Che Guevarra");
        cv.put(StaffListContract.StaffListEntry.STAFF_PRESENCE, "Active");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StaffListContract.StaffListEntry.STAFF_NAME, "Donald Trump");
        cv.put(StaffListContract.StaffListEntry.STAFF_PRESENCE, "Away");
        list.add(cv);

        cv = new ContentValues();
        cv.put(StaffListContract.StaffListEntry.STAFF_NAME, "Barrack Obama");
        cv.put(StaffListContract.StaffListEntry.STAFF_PRESENCE, "Active");
        list.add(cv);

        //insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (StaffListContract.StaffListEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(StaffListContract.StaffListEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}

