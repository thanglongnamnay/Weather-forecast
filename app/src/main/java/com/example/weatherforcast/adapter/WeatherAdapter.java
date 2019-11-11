package com.example.weatherforcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherforcast.R;
import com.example.weatherforcast.model.Weather;
import com.example.weatherforcast.IO.WeatherIO;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    private final WeatherIO weatherIO = WeatherIO.getInstance();
    private ArrayList<Weather> weatherList;
    private Context context;

    public WeatherAdapter(Context context) {
        this.weatherList = weatherIO.getForecast();
        this.context = context;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherViewHolder holder;
        if (convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.weather_row, null);
            holder = new WeatherViewHolder(
                    convertView.findViewById(R.id.tv_weather_time),
                    convertView.findViewById(R.id.iv_weather_image),
                    convertView.findViewById(R.id.tv_weather_temp),
                    convertView.findViewById(R.id.tv_weather_moisture),
                    convertView.findViewById(R.id.tv_weather_wind)
            );

            convertView.setTag(holder);
        } else {
            holder = (WeatherViewHolder) convertView.getTag();
        }
        Weather instant = (Weather) getItem(position);
        if (instant != null) {
            holder.time.setText(android.text.format.DateFormat.format("HH:mm dd/MM", instant.getTime()));
            holder.temp.setText(instant.getTemp() + "C");
            holder.moisture.setText(instant.getMoisture() + "%");
            holder.wind.setText(instant.getWind() + " m/s");
            holder.image.setImageResource(instant.getImage());
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        this.weatherList = weatherIO.getForecast();
        super.notifyDataSetChanged();
    }
}


class WeatherViewHolder {
    TextView time;
    ImageView image;
    TextView temp;
    TextView moisture;
    TextView wind;

    public WeatherViewHolder(TextView time, ImageView image, TextView temp, TextView moisture, TextView wind) {
        this.time = time;
        this.image = image;
        this.temp = temp;
        this.moisture = moisture;
        this.wind = wind;
    }
}