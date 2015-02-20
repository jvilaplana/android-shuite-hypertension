package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.HistorialAdapterItem;

/**
 * Created by Adrian on 20/02/2015.
 */
public class HistorialCell  implements HistorialAdapterItem{

    private final int tag = 1;

    private String semaphore;
    private String diastolic;
    private String systolic;
    private String pulse;

    public HistorialCell(String semaphore, String pulse, String systolic, String diastolic) {
        this.semaphore = semaphore;
        this.pulse = pulse;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }


    public String getSemaphore() {
        return semaphore;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public String getSystolic() {
        return systolic;
    }

    public String getPulse() {
        return pulse;
    }

    @Override
    public int getTag() {
        return tag;
    }
}
