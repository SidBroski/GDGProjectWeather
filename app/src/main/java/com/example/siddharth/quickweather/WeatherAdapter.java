package com.example.siddharth.quickweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Siddharth on 21-03-2018.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {
    public WeatherAdapter(@NonNull Context context, ArrayList<Weather> weatherArrayList) {
        super(context, 0, weatherArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Weather weather = getItem(position);

        if(convertView == null)
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        TextView dateTextView = (TextView)convertView.findViewById(R.id.tvdate);
        TextView minTextView = (TextView)convertView.findViewById(R.id.tvLowTemperature);
        TextView maxTextView = (TextView)convertView.findViewById(R.id.tvHighTemperature);
        TextView linkTextView = (TextView)convertView.findViewById(R.id.tvLink);

        dateTextView.setText(weather.getDate());
        minTextView.setText(weather.getMinTemp());
        maxTextView.setText(weather.getMaxTemp());
        linkTextView.setText(weather.getLink());

        return convertView;

    }
}
