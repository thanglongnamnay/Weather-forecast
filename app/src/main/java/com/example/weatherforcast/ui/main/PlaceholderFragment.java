package com.example.weatherforcast.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.weatherforcast.R;
import com.example.weatherforcast.controller.WeatherAdapter;
import com.example.weatherforcast.model.Weather;
import com.example.weatherforcast.model.WeatherIO;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private WeatherAdapter weatherAdapter;
    View root;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        if (index == 0) WeatherIO.fragment = fragment;
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        weatherAdapter = new WeatherAdapter(getContext());
        ((ListView) root.findViewById(R.id.lv_weather_forecast)).setAdapter(weatherAdapter);

        root.findViewById(R.id.btn_refresh).setOnClickListener(view -> {
            refresh();
        });

        System.out.println("root");
        return root;
    }

    public void refresh() {
        Weather current = WeatherIO.getInstance().getCurrentWeather();
        if (current == null) return;
        ((TextView)root.findViewById(R.id.tv_city_name)).setText(current.getCity());
        ((TextView)root.findViewById(R.id.tv_temp)).setText(current.getTemp() + " độ C");
        ((TextView)root.findViewById(R.id.tv_moisture)).setText(current.getMoisture() + "%");
        ((TextView)root.findViewById(R.id.tv_wind)).setText(current.getWind() + " m/s");
        ((TextView)root.findViewById(R.id.tv_weather_description)).setText(current.getDescription());
        ((ImageView)root.findViewById(R.id.iv_weather_image)).setImageResource(current.getImage());

        weatherAdapter.notifyDataSetChanged();
    }

}