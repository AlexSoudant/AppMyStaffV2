package com.ynov.android.mystaff.utilities;

/**
 * Created by admin on 30/11/16.
 */

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the Slack servers.
 */


public final class NetworkUtils {

    private static final String STATIC_SLACK_URL_CHANNEL = "https://slack.com/api/channels.list?";

    private static final String STATIC_SLACK_URL_PRESENCE = "https://slack.com/api/users.list?";

    final static  String SLACK_API_TOKEN = "token";

    final static String QUERY_PARAM_PRETTY = "pretty";

    final static String QUERY_PARAM_PRESENCE = "presence";


    /**
     * Builds the URL used to talk to the Slack server using a command query. QUERY_PARAM_CHANNEL
     * retrieve channels list and users in these channels.
     *
     * @param channelQuery The channel that will be queried for.
     * @return The URL to use to query the slack server.
     * **/

    public static URL buildUrl(String channelQuery){
        Uri buildUri = Uri.parse(STATIC_SLACK_URL_CHANNEL).buildUpon()
                .appendQueryParameter(SLACK_API_TOKEN, com.ynov.android.mystaff.BuildConfig.OPEN_SLACK_API_KEY)
                .appendQueryParameter(QUERY_PARAM_PRETTY, "1")
                .build();

        URL Url = null;
        try{
            Url = new URL(buildUri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return Url;
    }

    /**
     * Builds the URL used to talk to the Slack server. QUERY_PARAM_PRESENCE retrieve the
     * presence status of the wanted users.
     * @return The Url to use to query the Slack server.
     */

    public static URL buildUrlPresence(){
        Uri buildUri = Uri.parse(STATIC_SLACK_URL_PRESENCE).buildUpon()
                .appendQueryParameter(SLACK_API_TOKEN, com.ynov.android.mystaff.BuildConfig.OPEN_SLACK_API_KEY)
                .appendQueryParameter(QUERY_PARAM_PRESENCE, "presence")
                .appendQueryParameter(QUERY_PARAM_PRETTY, "1")
                .build();
        URL Url = null;
        try{
            Url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return Url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */

    public static String getReponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }

}
