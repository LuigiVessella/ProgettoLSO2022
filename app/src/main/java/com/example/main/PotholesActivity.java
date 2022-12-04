package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class PotholesActivity extends AppCompatActivity {

    TextView welcomeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_potholes);
        Toast.makeText(this, "SONO QUI", Toast.LENGTH_LONG).show();
        welcomeTv = findViewById(R.id.welcomeTextView);
        Log.v("verifica", getIntent().getStringExtra("user"));
       // welcomeTv.setText(getIntent().getStringExtra("user"));


    }
}