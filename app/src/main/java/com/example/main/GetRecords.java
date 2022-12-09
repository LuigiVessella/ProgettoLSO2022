package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.main.EntityClasses.Pothole;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class GetRecords extends AppCompatActivity {
    private RecyclerView reciclerPotholesView;
    private Button btnBack;
    private String query;
    private ArrayList<String> getResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_get_records);
        initializeComponents();
    }


    public void initializeComponents(){
        btnBack = findViewById(R.id.buttonBack);
        getResult = new ArrayList<>();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetRecords.this, PotholesActivity.class);
                startActivity(intent);
            }
        });




        query = "select * from rilevazionebuca";
        sendQueryToServer(query);

    }

    private void sendQueryToServer(String query){
        //qui il codice per mandare la query al server
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.client.setUp();
                MainActivity.client.sendSomeMessage(query);
                getResult =  MainActivity.client.converse();
                System.out.println("sto qua");
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("ho: " + getResult.get(1));

                    }
                });
            }
        });

        thread.start();
    }

    private void setText() {
        //ArrayList di prova
        ArrayList<Pothole> listPotholes = new ArrayList<>();
        Pothole tmpPot = new Pothole("Mario", "20:30", "2020-10-10", 40.234, 42.234, 2.01);
        Pothole tmpPot2 = new Pothole("Mario", "20:30", "2020-10-10", 40.234, 42.234, 2.01);
        listPotholes.add(tmpPot);
        listPotholes.add(tmpPot2);
        reciclerPotholesView = findViewById(R.id.recyclerViewPoth);
        reciclerPotholesView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PotholesAdapter potholesAdapter = new PotholesAdapter(getApplicationContext(), listPotholes);
        reciclerPotholesView.setAdapter(potholesAdapter);
        reciclerPotholesView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

    }
}