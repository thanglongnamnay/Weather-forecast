package com.example.weatherforcast.model;

import org.json.JSONArray;

import java.util.ArrayList;

public class NotiArrayList extends ArrayList<Noti> {
    public JSONArray toJson() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < size(); ++i) {
            array.put(get(i).toJson());
        }

        return array;
    }
}
