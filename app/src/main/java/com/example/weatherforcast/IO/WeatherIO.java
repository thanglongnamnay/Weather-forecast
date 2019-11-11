package com.example.weatherforcast.IO;

import com.example.weatherforcast.IO.fetch.CurrentWeatherFetch;
import com.example.weatherforcast.IO.fetch.ForecastFetch;
import com.example.weatherforcast.R;
import com.example.weatherforcast.model.Constants;
import com.example.weatherforcast.model.QueryType;
import com.example.weatherforcast.model.Weather;
import com.example.weatherforcast.ui.main.PlaceholderFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.TreeMap;

public class WeatherIO {
    private static WeatherIO instance;
    private ArrayList<Weather> weatherList;
    private Weather currentWeather;
    private static TreeMap<String, Integer> iconMap = new TreeMap<>();
    private String city = "Hanoi";
    public static PlaceholderFragment fragment;

    private WeatherIO() {
        new CurrentWeatherFetch(QueryType.gps, city).execute();
        new ForecastFetch(QueryType.gps, city).execute();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        if (city != null) {
            new CurrentWeatherFetch(QueryType.city, city).execute();
            new ForecastFetch(QueryType.city, city).execute();
        } else {
            new CurrentWeatherFetch(QueryType.gps, null).execute();
            new ForecastFetch(QueryType.gps, null).execute();
        }
    }

    public static WeatherIO getInstance() {
        if (instance == null) {
            instance = new WeatherIO();
            iconMap.put("01d", R.drawable.sunny);
            iconMap.put("02d", R.drawable.cloudy002);
            iconMap.put("03d", R.drawable.cloudy002);
            iconMap.put("04d", R.drawable.cloudy002);
            iconMap.put("09d", R.drawable.heavy005_rain);
            iconMap.put("10d", R.drawable.rain003);
            iconMap.put("11d", R.drawable.storm006);
            iconMap.put("13d", R.drawable.snowflake019);
            iconMap.put("50d", R.drawable.wind010);

            iconMap.put("01n", R.drawable.sunny);
            iconMap.put("02n", R.drawable.cloudy002);
            iconMap.put("03n", R.drawable.cloudy002);
            iconMap.put("04n", R.drawable.cloudy002);
            iconMap.put("09n", R.drawable.heavy005_rain);
            iconMap.put("10n", R.drawable.rain003);
            iconMap.put("11n", R.drawable.storm006);
            iconMap.put("13n", R.drawable.snowflake019);
            iconMap.put("50n", R.drawable.wind010);
        }

        return instance;
    }

    private void mock() {
        weatherList = new ArrayList<>();

        Date now = new Date(System.currentTimeMillis());
        weatherList.add(new Weather("Hanoi", now, 23, 60, 2, "brief", "Ngay dep troi", R.drawable.cloudy002));
        weatherList.add(new Weather("Hanoi", new Date(3600), 21, 55, 1, "brief", "Buoi chieu dep troi", R.drawable.cloudy002));
        weatherList.add(new Weather("Hanoi", new Date(3600), 18, 42, 1, "brief", "Dem dep troi", R.drawable.cloudy002));
    }

    public void setWeatherList(ArrayList<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public Weather getCurrentWeather() {
        if (currentWeather == null) {
            currentWeather = weatherList.get(0);
        }
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public ArrayList<Weather> getForecast() {
        if (weatherList == null) {
            mock();
        }

        return weatherList;
    }
    public static Weather parseWeather(JSONObject weather, Long timezone, String city) throws JSONException, IOException {
        JSONObject weather1 = weather.getJSONArray("weather").getJSONObject(0);
        String description = weather1.getString("description");
        String brief = weather1.getString("main");

        JSONObject main = weather.getJSONObject("main");
        Date time;
        if (timezone != null)
            time = new Date(1000 * (weather.getLong("dt") + timezone - Constants.TIME_ZOME));
        else
            time = new Date(1000 * (weather.getLong("dt") + weather.getLong("timezone") - Constants.TIME_ZOME));

        int temp = (int) Float.parseFloat(main.getString("temp")) - 273;
        int moisture = Integer.parseInt(main.getString("humidity"));
        float wind = Float.parseFloat(weather.getJSONObject("wind").getString("speed"));
        String icon = weather.getJSONArray("weather").getJSONObject(0).getString("icon");
//        String city = GPS.getInstance().getCity();

        return new Weather(city, time, temp, moisture, wind, brief, description, iconMap.get(icon));
    }
    public static void refresh() {
        if (fragment != null) {
            fragment.refresh();
        }
    }
}
