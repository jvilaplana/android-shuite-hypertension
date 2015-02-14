package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.PressureOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Adrian on 14/02/2015.
 */
public abstract class Pressures implements PressureOperations {
    private List<Pressure> pressures = new ArrayList<>(3);

    @Override
    public void addAll(Collection<? extends Pressure> collection) {
        pressures.addAll(collection);
    }

    @Override
    public void add(Pressure pressure) {
        pressures.add(pressure);
    }

    @Override
    public Pressure getPressure(int position) {
        return pressures.get(position);
    }

    @Override
    public List<Pressure> getAllPressures() {
        return pressures;
    }

    @Override
    public abstract boolean areMorningPressures();

    @Override
    public abstract boolean areAfternoonPressures();

}
