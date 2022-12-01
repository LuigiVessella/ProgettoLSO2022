package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PotholesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_potholes);
    }
}