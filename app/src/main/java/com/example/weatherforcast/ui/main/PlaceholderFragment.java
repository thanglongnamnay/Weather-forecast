package com.example.weatherforcast.ui.main;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherforcast.R;
import com.example.weatherforcast.controller.WeatherAdapter;
import com.example.weatherforcast.model.Weather;
import com.example.weatherforcast.model.WeatherIO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private WeatherAdapter weatherAdapter;
    View root;

    public static PlaceholderFragment newInstance() {
        PlaceholderFragment fragment = new PlaceholderFragment();
        WeatherIO.fragment = fragment;
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        weatherAdapter = new WeatherAdapter(getContext());
        ((ListView) root.findViewById(R.id.lv_weather_forecast)).setAdapter(weatherAdapter);

        root.findViewById(R.id.btn_refresh).setOnClickListener(view -> refresh());
        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream is = assetManager.open("vn cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            System.out.println(json.charAt(0));
            JSONArray array = new JSONArray(json);
            String[] strings = new String[array.length()];
            String[] cityNames = new String[array.length()];
            for (int i = 0; i < array.length(); ++i) {
                JSONObject jsonObject = array.getJSONObject(i);
                strings[i] = jsonObject.getString("name") + ", " + jsonObject.getString("country");
                cityNames[i] = jsonObject.getString("name");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, strings);

            Spinner spinner = root.findViewById(R.id.spinner2);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        System.out.println(strings[position]);
                        WeatherIO.getInstance().setCity(cityNames[position]);
                    } else {
                        WeatherIO.getInstance().setCity(null);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    System.out.println("yo wtf?");
                }
            });
//            spinner.setSelection(0);

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("root");
        return root;
    }

    public void refresh() {
        Weather current = WeatherIO.getInstance().getCurrentWeather();
        if (current == null) return;
        ((TextView)root.findViewById(R.id.tv_city_name)).setText(current.getCity());
        ((TextView)root.findViewById(R.id.tv_time)).setText(android.text.format.DateFormat.format("hh:mm dd/MM/yyyy", current.getTime()));
        ((TextView)root.findViewById(R.id.tv_temp)).setText(current.getTemp() + " độ C");
        ((TextView)root.findViewById(R.id.tv_moisture)).setText(current.getMoisture() + "%");
        ((TextView)root.findViewById(R.id.tv_wind)).setText(current.getWind() + " m/s");
        ((TextView)root.findViewById(R.id.tv_weather_description)).setText(current.getDescription());
        ((ImageView)root.findViewById(R.id.iv_weather_image)).setImageResource(current.getImage());

        weatherAdapter.notifyDataSetChanged();
    }

}