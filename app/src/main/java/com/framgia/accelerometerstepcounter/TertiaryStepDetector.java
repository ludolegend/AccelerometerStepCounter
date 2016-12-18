package com.framgia.accelerometerstepcounter;

import android.hardware.SensorEvent;

/**
 * Created by Ludo on 12/18/16.
 */

public class TertiaryStepDetector implements StepDetector {

    private AccelerometerProcessing mAccelProcessing;
    private double[] mAccelResult;

    public enum AccelerometerSignals {
        MAGNITUDE,
        MOV_AVERAGE;

        public static final int count = TertiaryStepDetector.AccelerometerSignals.values().length;
    }

    public TertiaryStepDetector() {
        mAccelProcessing = AccelerometerProcessing.getInstance();
        mAccelResult = new double[AccelerometerSignals.count];
    }

    @Override
    public int detect(SensorEvent event) {
        mAccelProcessing.setEvent(event);
        mAccelResult[0] = mAccelProcessing.calcMagnitudeVector(0);
        mAccelResult[0] = mAccelProcessing.calcExpMovAvg(0);
        mAccelResult[1] = mAccelProcessing.calcMagnitudeVector(1);
        if (mAccelProcessing.stepDetected(1)) {
            return 1;
        }
        return 0;
    }

}
