package com.example.weatherforcast.model;

import java.sql.Date;

public class Noti {
    private static final String defaultDescription = "Báo thức";
    private Date time;
    private String description;

    public Noti(Date time, String description) {
        this.time = time;
        this.description = description;
    }

    public Noti(Date time) {
        this.time = time;
        this.description = defaultDescription;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
