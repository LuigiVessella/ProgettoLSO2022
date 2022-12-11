package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.ClientServer.ClientServer;
import com.example.main.Sensor.Accelerometer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String userName = MainActivity.userName + " ";
    private String latidutine = "";
    private String longitudine = "";
    private Typeface typeface;


    //lo dichiariamo static e final, così sarà comune a tutte le classi
    //public static final ClientServer newSock = new ClientServer("192.168.1.14", 8080);

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
        typeface = getResources().getFont(R.font.roboto_black);
        welcomeTv = findViewById(R.id.welcomeTextView);
        buttonGetPos = findViewById(R.id.buttonGetPos);
        registrazioniSv = findViewById(R.id.linearScroll);
        bucaTv = findViewById(R.id.textViewBuca);
        visualizzaBtn = findViewById(R.id.buttonVisualizza);
        welcomeTv.setText("Benvenuto " + userName);


        counter = 0;
        buttonGetPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Accelerometer acc = new Accelerometer(getApplicationContext(), PotholesActivity.this);
                buttonGetPos.setText("RILEVANDO...");
                buttonGetPos.setTextColor(Color.WHITE);
            }
        });

        visualizzaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity = new Intent(PotholesActivity.this, GetRecords.class);

                startActivity(nextActivity);
                buttonGetPos.setText("Registrazione");
                finish();
            }
        });
    }

    public void setCoordinate(double variazione) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
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
                            latTv.setTypeface(typeface);
                            longTv.setTypeface(typeface);
                            latidutine = " " + String.valueOf(location.getLatitude()) + " ";
                            longitudine = String.valueOf(location.getLongitude()) + " ";
                            latTv.setText("trovata " + counter + " buca\n"+ "lat:" + latidutine);
                            longTv.setText("long: " + longitudine+ "\n");
                            registrazioniSv.addView(latTv);
                            registrazioniSv.addView(longTv);
                        }
                        else getCurrentPosIfNotLast();
                    }
                });
        if(!latidutine.isEmpty()) {
            String query = "insert into rilevazionebuca values('" + userName + "', '" + now + "','" + latidutine + "', '" + longitudine + "', " + variazione + ")";
            Log.v("query:", query);
            sendQueryToServer(query);
        }
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
                            latidutine = String.valueOf(location.getLatitude());
                            longitudine = String.valueOf(location.getLongitude());
                            latTv.setText("trovata " + counter + " buca\n"+ "lat:" + latidutine);
                            longTv.setText("long: " + longitudine+ "\n");
                            registrazioniSv.addView(latTv);
                            registrazioniSv.addView(longTv);
                        }
                    }
                });
    }

    private void sendQueryToServer(String query){
        //qui il codice per mandare la query al server
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                MainActivity.client.sendSomeMessage(query);
                //client.cleanUp();
            }
        });

        thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //newSock.cleanUp();
    }
}