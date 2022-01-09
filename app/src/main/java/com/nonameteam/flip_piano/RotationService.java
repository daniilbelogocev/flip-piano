package com.nonameteam.flip_piano;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import java.util.Timer;
import java.util.TimerTask;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

enum RotationType {
    left, middle, right
}

public class RotationService {
    static SensorManager sensorManager;
    static Sensor sensorAccel;
    static Sensor sensorMagnet;

    static Timer timer;

    static float[] valuesAccel = new float[3];
    static float[] valuesMagnet = new float[3];
    static float[] valuesResult = new float[3];
    static float[] r = new float[9];

    public static void initialize(Context context) {
        SensorEventListener listener = new SensorEventListener() {

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        for (int i=0; i < 3; i++){
                            valuesAccel[i] = event.values[i];
                        }
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        for (int i=0; i < 3; i++){
                            valuesMagnet[i] = event.values[i];
                        }
                        break;
                }
            }
        };
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
                SensorManager.getOrientation(r, valuesResult);

                valuesResult[0] = (float) Math.toDegrees(valuesResult[0]);
                valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
                valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);
                Log.d("123", " " + format(valuesResult));
            }
        };
        timer.schedule(task, 0, 50);
    }

    static RotationType format(float values[]) {
        Log.d("x", String.format("%1$.1f",values[1]));
        if (values[1] > 15) {
            return RotationType.left;
        }
        else if (values[1] < -15) {
            return RotationType.right;
        }
        else {
            return RotationType.middle;
        }
    }
}
