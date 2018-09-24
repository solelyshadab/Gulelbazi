package com.baig.shadab.Dhishkyaon;

/**
 * Created by shadabbaig on 30/12/16.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class OrientationData implements SensorEventListener { //attaches sensors to this class and whenever those sensors have change in values this class will listen to those and allow us to do stuff.
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    private float[] accelOutput;
    private float[] magOutput;

    private float[] orientation = new float[3];
    public float[] getOrientation(){
        return orientation;
    }

    private float[] startOrientation = null;
    public float[] getStartOrientation(){
        return startOrientation;
    }

    public void newGame(){
        startOrientation = null;
    }

    public OrientationData(){
        manager = (SensorManager)Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    public void register(){ //register the sensors to the main class so that we can listen to value change
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME); // this - this class, delay in calling onsensorchanged method to avoid load on phone.
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) { //method called when sensor value change
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;
        if(accelOutput !=null && magOutput !=null){ //then we want to calculate our orientation
            // rotation matrix will take a vector in one co-ordinate system and rotate that into it's corresponding position in another co-ordinate system.
            // in this case rotate from phone' local co-ordinate system(centred on the centre of phone) to world co-ordinate system(centered on centre of earth)
            float[] R = new float[9];
            float[] I = new float[9]; // create float array R for rotation matrix, 9 elements representing 3 by 3 matrix
            boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput);
            if(success) {
                SensorManager.getOrientation(R, orientation);
                if(startOrientation == null){
                    startOrientation = new float[orientation.length];
                    //orientation[1] = (float) (orientation[1] - 0.20); // added 45 degree to y -axis
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);

                }
            }
        }
    }
}

