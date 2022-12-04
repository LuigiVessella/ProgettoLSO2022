package com.example.main.Sensor;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class Accelerometer extends Application{
    private Sensor accelerometer;
    private double accX;
    private Context context;
    private double lastX = 0;
    private Activity activity;


    public Accelerometer(Context c, Activity a){
        activity = a;

        context = c;
        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener sel = new SensorEventListener() {

            public void onSensorChanged(SensorEvent sEvent) {
                accX = sEvent.values[0];
                checkPothole(accX);
                System.out.println("VALORE X " + accX);

            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
        sm.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void checkPothole(double newValue){
        if(lastX != 0 ){
            if(Math.abs(newValue - lastX) >= 2){
                System.out.println("BUCA RILEVATA");
                Gps gps = new Gps(context, activity);

            }
        }
        lastX = newValue;
    }
}
