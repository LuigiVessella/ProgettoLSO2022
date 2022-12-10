package com.example.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.EntityClasses.Pothole;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;


public class GetRecords extends AppCompatActivity {
    private RecyclerView reciclerPotholesView;
    private Button btnBack;
    private String query;
    private ArrayList<Pothole> listPotholes = new ArrayList<>();
    private Spinner spinnerDistance;
    private ArrayAdapter<CharSequence> adapter;
    private FusedLocationProviderClient fusedLocationClient;
    private double latidutine = 0, longitudine = 0;
    private Location loc;


    //ecco l'array riempito con i risultati
    private ArrayList<String> getResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_get_records);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        initializeComponents();
    }


    public void initializeComponents(){
        btnBack = findViewById(R.id.buttonBack);
        getResult = new ArrayList<>();
        spinnerDistance = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.distances_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistance.setAdapter(adapter);

        spinnerDistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetRecords.this, PotholesActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }

    private void sendQueryToServer(String query){

        //qui il codice per mandare la query al server
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.client.setUp();
                MainActivity.client.sendSomeMessage(query);
                //qua metto i risultati nel nostro arrayList
                getResult =  MainActivity.client.converse();


                for(String s : getResult){
                    Pothole tmpPoth = setString(s);
                    if(tmpPoth != null){
                        listPotholes.add(tmpPoth);
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setText();
                    }
                });
            }
        });

        thread.start();
    }

    private void setText() {
        //ArrayList di prova
        reciclerPotholesView = findViewById(R.id.recyclerViewPoth);
        reciclerPotholesView.setAdapter(null);
        reciclerPotholesView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PotholesAdapter potholesAdapter = new PotholesAdapter(getApplicationContext(), listPotholes);
        reciclerPotholesView.setAdapter(potholesAdapter);
        reciclerPotholesView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

    }

    public Pothole setString(String string){
        double radius = Double.parseDouble((String) spinnerDistance.getSelectedItem()) * 1000;//ottengo i metri

        Log.v("informazioni", "ho" + radius + latidutine + longitudine);
        String[] tmpString = string.split(" ", 2);
        String nameUser = tmpString[0];
        String[] dataSplit = tmpString[1].split(" ", 2);
        String data = dataSplit[0];
        String[] latitudeSplit = dataSplit[1].split(" ", 2);
        String latitude = latitudeSplit[0];
        String[] longitudeSplit = latitudeSplit[1].split(" ", 2);
        String longitude = longitudeSplit[0];
        Location loc2 = new Location("");
        loc2.setLatitude(Double.parseDouble(latitude));
        loc2.setLongitude(Double.parseDouble(longitude));

        if(loc.distanceTo(loc2) < radius) {
            Pothole potholeToReturn = new Pothole(nameUser, " ", data, Double.parseDouble(latitude), Double.parseDouble(longitude), 2.1);
            return potholeToReturn;
        } else return null;
    }

    private void getPosition(){
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
                            latidutine = location.getLatitude();
                            longitudine = location.getLongitude();
                            loc = location;
                            Log.v("ciao", "" + latidutine);
                            query = "select * from rilevazionebuca";
                            listPotholes.removeAll(listPotholes);
                            sendQueryToServer(query);
                        }
                        else Log.v("errore", "non riesco a ottenere la posizione");

                    }
                });


    }
}