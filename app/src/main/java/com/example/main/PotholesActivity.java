package com.example.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.Sensor.Accelerometer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class PotholesActivity extends AppCompatActivity {

    private TextView welcomeTv;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_potholes);



        Toast.makeText(this, "SONO QUI", Toast.LENGTH_LONG).show();
        welcomeTv = findViewById(R.id.welcomeTextView);


        Log.v("verifica", getIntent().getStringExtra("user"));
        welcomeTv.setText("BENVENUTO " + getIntent().getStringExtra("user"));
        //Accelerometer acc = new Accelerometer(getApplicationContext(), PotholesActivity.this);

        getLastLoc();

    }

    @SuppressLint("MissingPermission")
    private void getLastLoc() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.v("lat:", String.valueOf(location.getLatitude()));
                    Log.v("long", String.valueOf(location.getLongitude()));
                }
            }
        });

    }

}