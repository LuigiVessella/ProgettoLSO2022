package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.main.Sensor.Accelerometer;
import com.example.main.Sensor.Gps;

public class PotholesActivity extends AppCompatActivity {

    private TextView welcomeTv;
    private Button buttonGetPos;
    private TextView latTv;
    private TextView longTv;
    private Gps gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_potholes);
        initializeComponents();
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void initializeComponents(){
        welcomeTv = findViewById(R.id.welcomeTextView);
        buttonGetPos = findViewById(R.id.buttonGetPos);
        latTv = findViewById(R.id.textViewLat);
        longTv = findViewById(R.id.textViewLong);
        Gps gps = new Gps(getApplicationContext(), PotholesActivity.this);

        welcomeTv.setText("Benvenuto, " + getIntent().getStringExtra("user"));

        buttonGetPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Accelerometer acc = new Accelerometer(getApplicationContext(), PotholesActivity.this);
                //Qui c'è un problema di sincronizzazione, getLatitude viene restituita prima di aggiornare la latitude trovata.
                gps.getPosition();
                Log.d("LATITUDE TROVATA: ", String.valueOf(gps.getLatitude()));
            }
        });
    }

}