package com.example.main.Sensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import com.example.main.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Gps {
    private TextView welcomeTv;
    private TextView latTv;
    private TextView longTv;
    private Sensor accelerometer;
    private Context context;
    private Activity activity;
    private FusedLocationProviderClient fusedLocationClient;


    public Gps(Context c, Activity a) {
        context = c;
        activity = a;
        initializeComponents();
        requestPermission();


    }

    private void getLastLoc() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //se non si hanno i permessi, li richiediamo.
            requestPermission();
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(activity , new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latTv.setText( "lat: " + String.valueOf(location.getLatitude()));
                    longTv.setText("long: " + String.valueOf(location.getLongitude()));
                    Log.d("LATITU: ", String.valueOf(location.getLatitude()));
                    Log.d("LONGI: ",String.valueOf(location.getLongitude()));

                }
            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void initializeComponents(){
        welcomeTv =  activity.findViewById(R.id.welcomeTextView);
        latTv = activity.findViewById(R.id.textViewLat);
        longTv = activity.findViewById(R.id.textViewLong);

    }

    public void getPosition(){
        getLastLoc();
    }







}
