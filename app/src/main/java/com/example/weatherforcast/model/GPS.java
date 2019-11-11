package com.example.weatherforcast.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class GPS {
    private static GPS instance;

    public static GPS getInstance(Context context) {
        if (instance == null) {
            instance = new GPS(context);
        }
        return instance;
    }

    public static GPS getInstance() {
        if (instance == null) {
            throw new Error("GPS was not initialized. Use getInstance(Context) instead.");
        }
        return instance;
    }

    private LocationManager locationManager;
    private Context context;

    private GPS(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getLastLocation() {
        if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
}
