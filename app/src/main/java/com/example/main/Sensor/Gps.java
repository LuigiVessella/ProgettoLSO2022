package com.example.main.Sensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import com.example.main.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Gps {

    private Sensor accelerometer;
    private Context context;
    private Activity activity;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitudeDetected;
    private double longitudeDetected;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    public Gps(Context c, Activity a) {
        context = c;
        activity = a;
        requestPermission();
    }

    private void getLastLoc() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //se non si hanno i permessi, li richiediamo.
            requestPermission();
            return;
        }

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitudeDetected = location.getLatitude();
                        longitudeDetected = location.getLongitude();
                    }
                }
            }
        };

        fusedLocationClient.getLastLocation().addOnSuccessListener(activity , new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitudeDetected = location.getLatitude();
                    longitudeDetected = location.getLongitude();
                }
                else{
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //se non si hanno i permessi, li richiediamo.
                        requestPermission();
                        return;
                    }
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                }
            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }


    public void getPosition(){
        getLastLoc();
    }



}
