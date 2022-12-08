package com.example.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.main.EntityClasses.Pothole;

import java.util.ArrayList;
import java.util.Date;

public class GetRecords extends AppCompatActivity {
    private RecyclerView reciclerPotholesView;
    private Button btnBack;

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetRecords.this, PotholesActivity.class);
                startActivity(intent);
            }
        });
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