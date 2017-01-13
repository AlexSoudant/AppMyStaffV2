package com.ynov.android.mystaff.data;

/**
 * Created by admin on 30/11/16.
 */

public class MystaffPreferences {

    /*
 * Human readable channel name string, provided by the API.
  * */
    public static final String PREF_CHANNEL_NAME = "channel_name";

    /*
 * Before you implement methods to return your REAL preference for location,
 * we provide some default values to work with.
 */
    private static final String DEFAULT_CHANNEL_NAME = "beacons";

    public static String getDefaultSlackChannel() {
        /** This will be implemented in a future lesson **/
        return DEFAULT_CHANNEL_NAME;
    }


}
