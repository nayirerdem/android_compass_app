package com.nagikho.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nagikho.compass.view.CompassView;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private CompassView compassView;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compassView= new CompassView(this);
        setContentView(compassView);

        sensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if(sensor != null) {
            if (sensorManager != null) {
                sensorManager.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }else {
            Log.e("TAG", "onCreate: Orientation not found");
        }
    }

    private SensorEventListener mySensorEventListener= new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float azimuth= sensorEvent.values[0];
            compassView.updateData(azimuth);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(sensor != null) {
            sensorManager.unregisterListener(mySensorEventListener);
        }
    }
}
