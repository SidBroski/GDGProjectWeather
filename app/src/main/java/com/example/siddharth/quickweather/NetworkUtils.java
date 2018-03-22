package com.example.siddharth.quickweather;

import android.net.Uri;
import android.text.LoginFilter;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

/**
 * Created by Siddharth on 20-03-2018.
 */

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private final static String WEATHERDB_BASE_URL=
            "http://dataservice.accuweather.com/forecasts/v1/daily/5day/190795";
    private final static String API_KEY="DJzDdcy71yWa6xkd1a4iPHG0P3YG0Blw";

    private final static String METRIC_VALUE = "true";

    private final static String PARAM_API_KEY="apikey";

    private final static String PARAM_METRIC = "metric";

    public static URL buildURLforWeather() {
        Uri builtUri = Uri.parse(WEATHERDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_METRIC, METRIC_VALUE)
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "buildURLforWeather: url: "+url);
        return url;
    }

    public static String GetResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }

}
