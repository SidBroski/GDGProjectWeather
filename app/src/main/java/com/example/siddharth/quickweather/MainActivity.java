package com.example.siddharth.quickweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Weather> weatherArrayList = new ArrayList<>();
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.idListView);

        URL weatherUrl = NetworkUtils.buildURLforWeather();
        new FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);
    }

    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL weatherurl = urls[0];
            String WeatherSearchResults = null;

            try {
                WeatherSearchResults = NetworkUtils.GetResponseFromHttpUrl(weatherurl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: WeatherSearchResults:" + WeatherSearchResults);
            return WeatherSearchResults;
        }

        @Override
        protected void onPostExecute(String WeatherSearchResults) {
            if(WeatherSearchResults != null && !WeatherSearchResults.equals("")) {
                weatherArrayList = parseJSON(WeatherSearchResults);
                //Testing
                Iterator itr = weatherArrayList.iterator();
                while (itr.hasNext()) {
                    Weather weatherInIterator = (Weather) itr.next();
                    Log.i(TAG, "parseJSON: Date: " + weatherInIterator.getDate() +
                            "Min: " + weatherInIterator.getMinTemp() +
                            "Max: " + weatherInIterator.getMaxTemp() +
                            "Link: " + weatherInIterator.getLink());
                }


            }
            super.onPostExecute(WeatherSearchResults);
        }
    }

    private ArrayList<Weather> parseJSON(String WeatherSearchResults) {
        if(weatherArrayList != null){
            weatherArrayList.clear();
        }
        else{
            weatherArrayList = new ArrayList<>();
        }

        if(WeatherSearchResults != null){
            try {

                JSONObject rootObject = new JSONObject(WeatherSearchResults);
                Log.v("WEATHERSEARCH", rootObject.toString());
                JSONArray results = rootObject.getJSONArray("DailyForecasts");
                Log.v("LENGTH", ""+results.length());

                for (int i = 0; i < results.length(); i++) {
                    Weather weather = new Weather();

                    JSONObject resultsObj = results.getJSONObject(i);

                    String date = resultsObj.getString("Date");
                    weather.setDate(date);


                    JSONObject temperatureObj = resultsObj.getJSONObject("Temperature");
                    String minTemperature = temperatureObj.getJSONObject("Minimum").getString("Value");
                    weather.setMinTemp(minTemperature);


                    String maxTemperature = temperatureObj.getJSONObject("Maximum").getString("Value");
                    weather.setMaxTemp(maxTemperature);


                    String link = resultsObj.getString("Link");
                    weather.setLink(link);

                    Log.i(TAG, "parseJSON: date: " + date + " " +
                            "Min: " + minTemperature + " " +
                            "Max: " + maxTemperature + " " +
                            "Link: " + link);

                    weatherArrayList.add(weather);

                }
                    //Testing
                    /*Iterator itr = weatherArrayList.iterator();
                    while (itr.hasNext()) {
                        weather weatherInIterator = (weather) itr.next();
                        Log.i(TAG, "parseJSON: Date: " + weatherInIterator.getDate()+
                                "Min: " + weatherInIterator.getMinTemp() +
                                "Max: " + weatherInIterator.getMaxTemp() +
                                "Link: " + weatherInIterator.getLink() );*/




                if(weatherArrayList != null) {
                    WeatherAdapter weatherAdapter = new WeatherAdapter(this, weatherArrayList);
                    listView.setAdapter(weatherAdapter);


                }
                Log.v("bleh", weatherArrayList.size()+"");
                return weatherArrayList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
