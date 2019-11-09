package com.example.weatherforcast.model;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ForecastFetch extends AsyncTask<URL, Integer, ArrayList<Weather>> {

    public ForecastFetch() {
    }

    @Override
    protected ArrayList<Weather> doInBackground(URL... urls) {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=Hanoi&appid=f047d3094287dc3a915a15e3458384ed");
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
        System.out.println("current finished");
        WeatherIO.refresh();
    }
}
