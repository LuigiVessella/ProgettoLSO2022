package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.Sensor.Accelerometer;
import com.example.main.Sensor.Gps;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

public class PotholesActivity extends AppCompatActivity {
    private TextView welcomeTv;
    private Button buttonGetPos, visualizzaBtn;
    private Intent nextActivity;
    private TextView latTv;
    private TextView longTv;
    private TextView bucaTv;
    private LinearLayout registrazioniSv;
    private FusedLocationProviderClient fusedLocationClient;
    private Integer counter;
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
        registrazioniSv = findViewById(R.id.linearScroll);
        bucaTv = findViewById(R.id.textViewBuca);
        visualizzaBtn = findViewById(R.id.buttonVisualizza);
        welcomeTv.setText("Benvenuto, " + getIntent().getStringExtra("user"));
        counter = 0;
        buttonGetPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Accelerometer acc = new Accelerometer(getApplicationContext(), PotholesActivity.this);
                buttonGetPos.setText("RILEVANDO...");
                buttonGetPos.setTextColor(Color.GREEN);
            }
        });

        visualizzaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity = new Intent(PotholesActivity.this, GetRecords.class);
                //nextActivity.putExtra("user", userName);
                startActivity(nextActivity);
            }
        });
    }

    public void setCoordinate() {
        counter++;
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
                            latTv = new TextView(PotholesActivity.this);
                            longTv = new TextView(PotholesActivity.this);
                            latTv.setText("trovata " + counter + " buca\n"+ "lat:" + String.valueOf(location.getLatitude()));
                            longTv.setText("long: " + String.valueOf(location.getLongitude()) + "\n");
                            registrazioniSv.addView(latTv);
                            registrazioniSv.addView(longTv);

                            return;
                        }
                        else getCurrentPosIfNotLast();
                    }
                });
    }

    private void getCurrentPosIfNotLast() {
        CancellationTokenSource cts = new CancellationTokenSource();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cts.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Toast.makeText(PotholesActivity.this, "CURRENT POS", Toast.LENGTH_SHORT).show();
                            latTv = new TextView(PotholesActivity.this);
                            longTv = new TextView(PotholesActivity.this);
                            latTv.setText("trovata " + counter + " buca\n"+ "lat:" + String.valueOf(location.getLatitude()));
                            longTv.setText("long: " + String.valueOf(location.getLongitude()));
                            registrazioniSv.addView(latTv);
                            registrazioniSv.addView(longTv);
                            return;
                        }
                    }
                });
    }

    private void sendQueryToServer(){
        //qui il codice per mandare la query al server

    }
}