package com.example.weatherforcast.model;

import java.sql.Date;

public class Weather {
    String city;
    Date time;
    int temp;
    int moisture;
    int wind;
    String description;

    public Weather(String city, Date time, int temp, int moisture, int wind, String description) {
        this.city = city;
        this.time = time;
        this.temp = temp;
        this.moisture = moisture;
        this.wind = wind;
        this.description = description;
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

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
