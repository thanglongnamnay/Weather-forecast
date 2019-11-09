package com.example.weatherforcast.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
    }

    public Location getLastLocation() {
        if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public String getCity() throws IOException {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        Location current = getLastLocation();

        List<Address> addresses = geocoder.getFromLocation(current.getLatitude(), current.getLongitude(), 1);
        System.out.println(addresses.get(0));
        return addresses.get(0).getAddressLine(0);
    }
}
