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
    private double accX;
    private double accY;
    private double accZ;
    private double valueTest = 0;
    private Context context;
    private double lastValue = 0;
    private Activity activity;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    public Accelerometer(Context c, Activity a){
        activity = a;
        context = c;
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        SensorEventListener sel = new SensorEventListener() {
            //il nuovo codice è preossochè identico al precedente, solo che ho ordinato un pò le formule e aggiunto il quadrato
            public void onSensorChanged(SensorEvent se) {
                /*
                accX = sEvent.values[0];
                accY = sEvent.values[1];
                accZ = sEvent.values[2];
                valueTest = Math.sqrt(accX * accX + accY * accY + accZ * accZ);
                checkPothole(valueTest, (PotholesActivity) activity);
                //System.out.println("VALORE NUOVO " + accZ);
                */

                float x = se.values[0];
                float y = se.values[1];
                float z = se.values[2];

                mGravity = se.values.clone();
                // Shake detection
                x = mGravity[0];
                y = mGravity[1];
                z = mGravity[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta;
                checkPothole(mAccel, (PotholesActivity) activity);
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
        sm.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void checkPothole(double newValue, PotholesActivity act){
        //double variazione = 0;

        /* if(lastValue != 0 ){
            if((variazione = lastValue - newValue) >= 2){
                Log.d("Evento: ", "BUCA TROVATA");
                Log.d("LAST VALUE ", String.valueOf(lastValue));
                Log.d("NEW VALUE ", String.valueOf(newValue));

                //Una volta rilevata la buca si ottengono le coordinate di essa.
                act.setCoordinate(variazione);
                }

            }
        lastValue = newValue;*/

        if(newValue > 5) {
            act.setCoordinate(mAccel);
        }


    }
}
