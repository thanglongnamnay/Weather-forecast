package com.example.weatherforcast.model;

import java.sql.Date;
import java.util.Map;
import java.util.TreeMap;


public class Weather {
    String city;
    Date time;
    int temp;
    int moisture;
    float wind;
    String brief;
    String description;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Weather(String city, Date time, int temp, int moisture, float wind, String brief, String description, int image) {
        this.city = city;
        this.time = time;
        this.temp = temp;
        this.moisture = moisture;
        this.wind = wind;
        this.brief = brief;
        this.description = description;
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getMoisture() {
        return moisture;
    }

    public void setMoisture(int moisture) {
        this.moisture = moisture;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
