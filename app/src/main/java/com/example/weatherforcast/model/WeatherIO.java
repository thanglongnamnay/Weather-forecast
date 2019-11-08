package com.example.weatherforcast.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class WeatherIO {
    private static WeatherIO instance;
    private ArrayList<Weather> weatherList;

    private WeatherIO() {}

    public static WeatherIO getInstance() {
        if (instance == null) {
            instance = new WeatherIO();
        }

        return instance;
    }

    private void mock() {
        weatherList = new ArrayList<>();

        Date now = new Date(System.currentTimeMillis());
//        weatherList.add(new Weather("Hanoi", now, 23, 60, 2, "Ngay dep troi"));
//        weatherList.add(new Weather("Hanoi", new Date(3600)., 21, 55, 1, "Buoi chieu dep troi"));
//        weatherList.add(new Weather("Hanoi", new Date(3600), 18, 42, 1, "Dem dep troi"));
    }

    public ArrayList<Weather> getForecast() {
        // TODO: fetch instead of mock
        if (weatherList == null) {
            mock();
        }

        return weatherList;
    }
}
