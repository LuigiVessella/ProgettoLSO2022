package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.main.Sensor.Accelerometer;
import com.example.main.Sensor.Gps;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

public class PotholesActivity extends AppCompatActivity {
    private TextView welcomeTv;
    private Button buttonGetPos;
    private TextView latTv;
    private TextView longTv;
    private TextView bucaTv;
    private Gps gps;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_potholes);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        initializeComponents();
    }

    private void initializeComponents() {
        welcomeTv = findViewById(R.id.welcomeTextView);
        buttonGetPos = findViewById(R.id.buttonGetPos);
        latTv = findViewById(R.id.textViewLat);
        longTv = findViewById(R.id.textViewLong);
        bucaTv = findViewById(R.id.textViewBuca);
        Gps gps = new Gps(getApplicationContext(), PotholesActivity.this);

        welcomeTv.setText("Benvenuto, " + getIntent().getStringExtra("user"));

        buttonGetPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Accelerometer acc = new Accelerometer(getApplicationContext(), PotholesActivity.this);
                buttonGetPos.setText("RILEVANDO...");
                buttonGetPos.setTextColor(Color.GREEN);
            }
        });
    }

    public void setCoordinate() {
        CancellationTokenSource cts = new CancellationTokenSource();
        bucaTv.setText("buca rilevata presso:");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latTv.setText(String.valueOf(location.getLatitude()));
                            longTv.setText(String.valueOf(location.getLongitude()));
                        }
                    }
                });

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latTv.setText(String.valueOf(location.getLatitude()));
                            longTv.setText(String.valueOf(location.getLongitude()));
                        }
                    }
                });
    }
}