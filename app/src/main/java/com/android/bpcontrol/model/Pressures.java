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
    public Pressure obtainPressuresAverage(){

        //first pressure is discard, because the patient is nervous
        Pressure tmp1 = pressures.get(1);
        Pressure tmp2 = pressures.get(2);

        int systolicAverage = ((Integer.parseInt(tmp1.getSystolic()) + Integer.parseInt(tmp2.getSystolic()))/2);
        int diastolicAverage = ((Integer.parseInt(tmp1.getDiastolic()) + Integer.parseInt(tmp2.getDiastolic()))/2);
        int pulse = ((Integer.parseInt(tmp1.getPulse()) + Integer.parseInt(tmp2.getPulse()))/2);

        return new Pressure(systolicAverage,diastolicAverage,pulse);
    }

    public static Pressure obtainPressuresDayAverage(PressuresMorning pMorning, PressuresAfternoon pAfternoon){


        Pressure pMorningAverage = pMorning.obtainPressuresAverage();
        Pressure pAfternoonAverage = pAfternoon.obtainPressuresAverage();

        int systolicDayAverage = (Integer.parseInt(pMorningAverage.getSystolic()) + Integer.parseInt(pAfternoonAverage.getSystolic()))/2;
        int diastolicDayAverage = (Integer.parseInt(pMorningAverage.getDiastolic()) + Integer.parseInt(pAfternoonAverage.getDiastolic()))/2;
        int pulseDayAverage = (Integer.parseInt(pMorningAverage.getPulse()) + Integer.parseInt(pAfternoonAverage.getPulse()))/2;


        return new Pressure(systolicDayAverage,diastolicDayAverage,pulseDayAverage);
    }

    @Override
    public abstract boolean areMorningPressures();

    @Override
    public abstract boolean areAfternoonPressures();

}
