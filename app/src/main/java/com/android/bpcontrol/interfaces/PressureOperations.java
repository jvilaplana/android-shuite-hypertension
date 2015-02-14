package com.android.bpcontrol.interfaces;

import com.android.bpcontrol.model.Pressure;

import java.util.Collection;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by Adrian on 14/02/2015.
 */
public interface PressureOperations {

    public void addAll(Collection<? extends Pressure> collection);
    public void add(Pressure pressure);
    public Pressure getPressure(int position);
    public List<Pressure> getAllPressures();
    public Pressure obtainPressuresAverage();
    public boolean areMorningPressures();
    public boolean areAfternoonPressures();



}
