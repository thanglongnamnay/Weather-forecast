package com.example.weatherforcast.IO.fetch;

import android.location.Location;
import android.os.AsyncTask;

import com.example.weatherforcast.IO.WeatherIO;
import com.example.weatherforcast.model.GPS;
import com.example.weatherforcast.model.QueryType;
import com.example.weatherforcast.model.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ForecastFetch extends AsyncTask<URL, Integer, ArrayList<Weather>> {

    private String city;
    QueryType type;
    public ForecastFetch(QueryType type, String city) {
        this.type = type;
        this.city = city;
    }

    @Override
    protected ArrayList<Weather> doInBackground(URL... urls) {
        try {
            URL url;
            if (type == QueryType.gps) {
                Location current = GPS.getInstance().getLastLocation();
                if (current != null) {
                url = new URL("https://api.openweathermap.org/data/2.5/forecast?lat="
                        + current.getLatitude() +
                        "&lon="
                        + current.getLongitude() +
                        "&appid=f047d3094287dc3a915a15e3458384ed");
                } else {
                    url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=Hanoi&appid=f047d3094287dc3a915a15e3458384ed");
                }
            } else {
                url = new URL("https://api.openweathermap.org/data/2.5/forecast?q="
                        + city
                        + "&appid=f047d3094287dc3a915a15e3458384ed");
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder data = new StringBuilder();
            while(line != null){
                line = bufferedReader.readLine();
                data.append(line);
            }
            System.out.println(data.toString());

            JSONObject jsonObject = new JSONObject(data.toString());
            JSONArray dataArray = jsonObject.getJSONArray("list");
            ArrayList<Weather> weathers = new ArrayList<>();
            for (int i = 0; i < dataArray.length(); ++i) {
                JSONObject city = jsonObject.getJSONObject("city");
                weathers.add(WeatherIO.parseWeather(dataArray.getJSONObject(i), city.getLong("timezone"), city.getString("name")));
            }

            return weathers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        WeatherIO.getInstance().setWeatherList(weathers);
        WeatherIO.refresh();
    }
}
