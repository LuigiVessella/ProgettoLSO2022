package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.main.ClientServer.ClientServer;
import com.example.main.Sensor.Accelerometer;

public class MainActivity extends AppCompatActivity {
    public static final ClientServer client = new ClientServer("4.236.136.210", 8080);;
    private Accelerometer acc;
    private Intent activitySwitcher;
    private Button btnSignin;
    private Intent potholesIntent;
    private EditText someText;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        initializeComponents();
    }


    public void initializeComponents(){

        btnSignin = findViewById(R.id.loginButton2);
        potholesIntent = new Intent(this, PotholesActivity.class);
        someText = findViewById(R.id.emailLoginEditText);

        //Action after sign in
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = String.valueOf(someText.getText() );
                if(TextUtils.isEmpty(userName) || userName.length() < 3){
                    someText.setError("inserisci un nome valido!");
                    return;
                }
                //mandiamo l'username al server
                sendMessageToServer(userName);
                switchActivity(userName);
            }
        });

    }


    private void switchActivity(String userName) {

        Toast.makeText(this, "logged in", Toast.LENGTH_LONG).show();
        activitySwitcher = new Intent(MainActivity.this, PotholesActivity.class);
        activitySwitcher.putExtra("user", userName);
        startActivity(activitySwitcher);
        finish();

    }

    public void sendMessageToServer(String stringa){
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.setUp();
               // client.sendSomeMessage(stringa.trim());
                //client.cleanUp();

            }
        });

        thread.start();
    }
}