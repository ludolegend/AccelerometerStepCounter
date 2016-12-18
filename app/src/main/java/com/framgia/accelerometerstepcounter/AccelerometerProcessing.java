package com.framgia.accelerometerstepcounter;

import android.hardware.SensorEvent;

/**
 * Created by Ludo on 12/17/16.
 */

public class AccelerometerProcessing {

    private static AccelerometerProcessing instance = null;

    public static AccelerometerProcessing getInstance() {
        if (instance == null)
            return new AccelerometerProcessing();
        return instance;
    }
    private static final int INACTIVE_PERIODS = 12;
    public static final float THRESH_INIT_VALUE = 12.72f;

    private int mInactiveCounter = 0;
    public boolean isActiveCounter = true;

    private static double mThresholdValue = THRESH_INIT_VALUE;
    private double[] mAccelValues = new double[TertiaryStepDetector.AccelerometerSignals.count];
    private double[] mAccelLastValues = new double[TertiaryStepDetector.AccelerometerSignals.count];

    private SensorEvent mEvent;

    private double[] gravity = new double[3];
    private double[] linear_acceleration = new double[3];

    public void setEvent(SensorEvent e) {
        mEvent = e;
    }

    public double calcMagnitudeVector(int i) {
        linear_acceleration[0] = mEvent.values[0] - gravity[0];
        linear_acceleration[1] = mEvent.values[1] - gravity[1];
        linear_acceleration[2] = mEvent.values[2] - gravity[2];

        mAccelValues[i] = Math.sqrt(
                linear_acceleration[0] * linear_acceleration[0] +
                        linear_acceleration[1] * linear_acceleration[1] +
                        linear_acceleration[2] * linear_acceleration[2]);
        return mAccelValues[i];
    }

    public double calcExpMovAvg(int i) {
        final double alpha = 0.1;
        mAccelValues[i] = alpha * mAccelValues[i] + (1 - alpha) * mAccelLastValues[i];
        mAccelLastValues[i] = mAccelValues[i];
        return mAccelValues[i];
    }

    public boolean stepDetected(int i) {
        if (mInactiveCounter == INACTIVE_PERIODS) {
            mInactiveCounter = 0;
            if (!isActiveCounter)
                isActiveCounter = true;
        }
        if (mAccelValues[i] > mThresholdValue) {
            if (isActiveCounter) {
                mInactiveCounter = 0;
                isActiveCounter = false;
                return true;
            }
        }
        ++mInactiveCounter;
        return false;
    }

}
