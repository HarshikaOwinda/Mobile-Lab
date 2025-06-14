package com.s23010260.harshika;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sen extends AppCompatActivity {

    private Sensors sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sen);

        TextView tempStatus = findViewById(R.id.tempStatus);

        // Using last 2 digits of SID: 60
        float threshold = 60.0f;

        sensors = new Sensors(this, tempStatus, threshold);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensors.start(); // Start listening sensor
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensors.stop(); // Stop sensor to save battery
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensors.release(); // Release MediaPlayer
    }
}
