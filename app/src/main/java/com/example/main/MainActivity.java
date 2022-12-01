package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.main.ClientServer.ClientServer;

public class MainActivity extends AppCompatActivity {
    ClientServer client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        client = new ClientServer("4.236.136.210", 8080);
        initializeComponents();
    }


    public void initializeComponents(){
        Button btnSignin = findViewById(R.id.loginButton);
        Intent potholesIntent = new Intent(this, PotholesActivity.class);
        EditText someText = findViewById(R.id.emailLoginEditText);

        //Action after sign in
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(potholesIntent);
                //String stringa = String.valueOf(someText.getText());
                //sendMessageToServer(stringa);
            }
        });

    }

    public void sendMessageToServer(String stringa){
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sono qui");
                client.setUp();
                client.sendSomeMessage(stringa);
                client.cleanUp();
            }
        });

        thread.start();



    }
}