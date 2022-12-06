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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.Sensor.Accelerometer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class PotholesActivity extends AppCompatActivity {

    private TextView welcomeTv;
    private Button buttonGetPos;
    private TextView latTv;
    private TextView longTv;

    //la documentazione di android consiglia di usare questo, non sembra funzionare al momento
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_potholes);
        requestPermission();


        welcomeTv = findViewById(R.id.welcomeTextView);
        buttonGetPos = findViewById(R.id.buttonGetPos);
        latTv = findViewById(R.id.textViewLat);
        longTv = findViewById(R.id.textViewLong);

        welcomeTv.setText("Benvenuto, " + getIntent().getStringExtra("user"));

        buttonGetPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLoc();
            }
        });
        //Accelerometer acc = new Accelerometer(getApplicationContext(), PotholesActivity.this);
    }

    //questa è la funzione che dovrebbe prendere la posizione

    private void getLastLoc() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //se non si hanno i permessi, li richiediamo.
            requestPermission();
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(PotholesActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latTv.setText( "lat: " + String.valueOf(location.getLatitude()));
                    longTv.setText("long: " + String.valueOf(location.getLongitude()));
                }
            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

}