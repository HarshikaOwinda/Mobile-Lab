package com.s23010260.harshika;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.widget.TextView;

public class Sensors implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor tempSensor;
    private MediaPlayer mediaPlayer;
    private TextView tempStatusTextView;
    private float threshold;
    private boolean isPlaying = false;

    public Sensors(Context context, TextView tempStatus, float threshold) {
        this.tempStatusTextView = tempStatus;
        this.threshold = threshold;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        mediaPlayer = MediaPlayer.create(context, R.raw.ab);  // ab.mp3 in res/raw

        if (tempSensor == null) {
            tempStatusTextView.setText("Ambient Temperature Sensor not available.");
        }
    }

    public void start() {
        if (tempSensor != null) {
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();  // Use pause instead of stop
            isPlaying = false;
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float currentTemp = event.values[0];
            tempStatusTextView.setText("Current Temp: " + currentTemp + "°C");

            if (currentTemp > threshold) {
                if (mediaPlayer != null && !isPlaying) {
                    mediaPlayer.start();
                    isPlaying = true;
                }
            } else {
                if (mediaPlayer != null && isPlaying) {
                    mediaPlayer.pause();
                    isPlaying = false;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No action needed
    }
}
