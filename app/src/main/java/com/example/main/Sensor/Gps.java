package com.example.main.Sensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class Gps {

    private Sensor accelerometer;
    private Context context;
    private Activity activity;
    private LocationManager lm;
    private final LocationListener mLocationListener;

    // The minimum distance to change Updates in meters
    private static final int MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 metro

    // The minimum time between updates in milliseconds
    private static final int MIN_TIME_BW_UPDATES = 1000; // 1 second


    public Gps(Context c, Activity a) {
        context = c;
        activity = a;
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                //Qui rileva la latitudine e la longitudine
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("latitude", String.valueOf(latitude));
                Log.d("longitude", String.valueOf(longitude));

            }
        };
        checkPermissions();
    }


    private void checkPermissions(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,mLocationListener);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
}
