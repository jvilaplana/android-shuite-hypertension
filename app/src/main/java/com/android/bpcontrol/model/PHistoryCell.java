package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.PHistoryAdapterItem;

/**
 * Created by Adrian on 20/02/2015.
 */
public class PHistoryCell implements PHistoryAdapterItem {

    private final int tag = 1;

    private Pressure pressure;

    public PHistoryCell(Pressure pressure) {
       this.pressure = pressure;
    }


    public int getSemaphore() {
      return pressure.getSemaphore();
    }

    public String getDiastolic() {
        return pressure.getDiastolic();
    }

    public String getSystolic() {
        return pressure.getSystolic();
    }

    public String getPulse() {
        return pressure.getPulse();
    }

    @Override
    public int getTag() {
        return tag;
    }
}
