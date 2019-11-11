package com.example.weatherforcast.IO.fetch;

import android.location.Location;
import android.os.AsyncTask;

import com.example.weatherforcast.IO.WeatherIO;
import com.example.weatherforcast.model.GPS;
import com.example.weatherforcast.model.QueryType;
import com.example.weatherforcast.model.Weather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentWeatherFetch extends AsyncTask<URL, Integer, Weather> {
    private final GPS gps = GPS.getInstance();
    private QueryType type;
    String city;

    public CurrentWeatherFetch(QueryType type, String city) {
        this.type = type;
        this.city = city;
    }
    @Override
    protected Weather doInBackground(URL... urls) {
        try {

            URL url;
            if (type == QueryType.gps) {
                Location current = gps.getLastLocation();
                if (current != null) {
                    url = new URL("https://api.openweathermap.org/data/2.5/weather?lat="
                            + current.getLatitude() +
                            "&lon=" + current.getLongitude() +
                            "&appid=f047d3094287dc3a915a15e3458384ed");
                } else {
                    url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Hanoi&appid=f047d3094287dc3a915a15e3458384ed");
                }
            } else {
                url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=f047d3094287dc3a915a15e3458384ed");
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
            JSONObject weather = new JSONObject(data.toString());
            return WeatherIO.parseWeather(weather, null, weather.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(Weather result) {
        WeatherIO.getInstance().setCurrentWeather(result);
        WeatherIO.refresh();
    }
}
