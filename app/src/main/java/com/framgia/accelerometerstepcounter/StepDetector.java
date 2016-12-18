package com.framgia.accelerometerstepcounter;

import android.hardware.SensorEvent;

/**
 * Created by Ludo on 12/18/16.
 */

public interface StepDetector {

    /**
     * Handle data from {@link SensorEvent} to detect increased step occurs
     *
     * @param event event from various sensor type
     * @return the number of steps are detected (increased step)
     */
    int detect(SensorEvent event);
}
