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
    private ArrayList<Pothole> listPotholes = new ArrayList<>();


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
                finish();
            }
        });



        setString("luigi 2022-12-0941.295457214.3299172.836057685946772");
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
                //qua metto i risultati nel nostro arrayList
                getResult =  MainActivity.client.converse();
                getResult.remove(0);
                getResult.remove(0);;
                for(String s : getResult){
                    Pothole tmpPoth = setString(s);
                    listPotholes.add(tmpPoth);
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
        reciclerPotholesView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PotholesAdapter potholesAdapter = new PotholesAdapter(getApplicationContext(), listPotholes);
        reciclerPotholesView.setAdapter(potholesAdapter);
        reciclerPotholesView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

    }

    public Pothole setString(String string){
        String[] tmpString = string.split(" ", 2);
        String nameUser = tmpString[0];
        String data = tmpString[1].substring(0, 10);
        String latitude = tmpString[1].substring(10, 20);
        String longitude = tmpString[1].substring(20, 29);
        Pothole potholeToReturn = new Pothole(nameUser, " ", data, Double.parseDouble(latitude) , Double.parseDouble(longitude), 2.1);
        return potholeToReturn;

    }
}