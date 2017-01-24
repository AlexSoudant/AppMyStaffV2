package com.ynov.android.mystaff.data;

import android.provider.BaseColumns;

/**
 * Created by Alex on 22/01/2017.
 */

public class StaffListContract {
    public static final class StaffListEntry implements BaseColumns {
        public static final String TABLE_NAME = "stafflistdata";
        public static final String STAFF_NAME = "staffname";
        public static final String STAFF_PRESENCE = "staffpresence";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
