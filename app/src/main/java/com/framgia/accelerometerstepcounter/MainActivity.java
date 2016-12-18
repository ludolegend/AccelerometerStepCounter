package com.framgia.accelerometerstepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private int mStepCount = 0;

    private TextView mTextViewStep;
    private SensorManager mSensorManager;
    private Sensor mStepSensor;
    private StepDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewStep = (TextView) findViewById(R.id.step);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mDetector = new TertiaryStepDetector();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this, mStepSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int step = mDetector.detect(sensorEvent);
        if (step != 0) {
            ++mStepCount;
            mTextViewStep.setText(String.valueOf(mStepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
