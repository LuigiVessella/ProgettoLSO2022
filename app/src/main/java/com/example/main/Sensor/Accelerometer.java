package com.example.main.Sensor;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


public class Accelerometer extends Application{
    private Sensor accelerometer;
    private double accX;
    private double accY;
    private double accZ;
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

            public void onSensorChanged(SensorEvent sEvent) {
                accX = sEvent.values[0];
                accY = sEvent.values[1];
                accZ = sEvent.values[2];
                valueTest = Math.sqrt(accX + accY + accZ);
                checkPothole(valueTest);
                //System.out.println("VALORE NUOVO " + accZ);

            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
        sm.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void checkPothole(double newValue){
        if(lastValue != 0 ){
            if((lastValue - newValue) >= 2){
                Log.d("Evento: ", "BUCA TROVATA");
                Log.d("LAST VALUE ", String.valueOf(lastValue));
                Log.d("NEW VALUE ", String.valueOf(newValue));

                //Una volta rilevata la buca si ottengono le coordinate di essa.
                //Qui c'è un problema di sincronizzazione, getLatitude viene restituita prima di aggiornare la latitude trovata.
                Gps gps = new Gps(context, activity);
                gps.getPosition();
                double tmpLatitudePotholes = gps.getLatitude();
                double tmpLongitudePotholes = gps.getLongitude();
                Log.d("LATITUDE TROVATA: ", String.valueOf(tmpLatitudePotholes));
                Log.d("LONGITUDE TROVATA: ", String.valueOf(tmpLongitudePotholes));


            }
        }
        lastValue = newValue;
    }
}
