package com.example.main.Sensor;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.main.PotholesActivity;


public class Accelerometer extends Application{
    private Sensor accelerometer;
    private double accX, accY, accZ;
    private double valueTest = 0;
    private Context context;
    private double lastValue = 0;
    private Activity activity;

    public Accelerometer(Context c, Activity a){
        activity = a;
        context = c;
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener sel = new SensorEventListener() {
            public void onSensorChanged(SensorEvent se) {

                accX = se.values[0];
                accY = se.values[1];
                accZ = se.values[2];
                valueTest = Math.sqrt(accX + accY + accZ ); // qui andrebbe il quadrato
                checkPothole(valueTest, (PotholesActivity) activity);
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
        sm.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void checkPothole(double newValue, PotholesActivity act){
        double variazione = 0;

        if(lastValue != 0 ) {
            if ((variazione = lastValue - newValue) >= 2) {
                Log.d("Evento: ", "BUCA TROVATA");
                Log.d("LAST VALUE ", String.valueOf(lastValue));
                Log.d("NEW VALUE ", String.valueOf(newValue));

                //Una volta rilevata la buca si ottengono le coordinate di essa.
                act.setCoordinate(variazione);
            }
        }
        lastValue = newValue;
    }
}
