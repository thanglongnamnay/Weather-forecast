package com.example.weatherforcast.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Noti {
    private static final String defaultDescription = "Báo thức";
    private String time;
    private String description;
    private boolean isEnable;

    public Noti(String time, String description, boolean isEnable) {
        this.time = time;
        this.description = description;
        this.isEnable = isEnable;
    }

    public Noti(String time) {
        this.time = time;
        this.description = defaultDescription;
        this.isEnable = true;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("time", time);
            object.put("description", description);
            object.put("isEnable", isEnable);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Noti fromJson(JSONObject object) {
        try {
            return new Noti(
                    object.getString("time"),
                    object.getString("description"),
                    object.getBoolean("isEnable")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
