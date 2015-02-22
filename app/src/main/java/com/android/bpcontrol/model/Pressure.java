package com.android.bpcontrol.model;

import android.provider.ContactsContract;

import com.android.bpcontrol.utils.DateUtils;

import java.util.Date;

/**
 * Created by Adrian on 14/2/15.
 */
public class Pressure{

    private int id = -1;
    private Date date;
    private String systolic;
    private String diastolic;
    private String pulse;
    private int semaphore=1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pressure(){}

    public Pressure(int systolic, int diastolic, int pulse){

        this.systolic = String.valueOf(systolic);
        this.diastolic = String.valueOf(diastolic);
        this.pulse = String.valueOf(pulse);
    }

    public Pressure(String systolic, String diastolic, String pulse){

        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }
    public Pressure(String systolic,String diastolic,String pulse, Date date){

        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.date = date;

    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate(){

        return DateUtils.dateToString(date,DateUtils.DEFAULT_FORMAT);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(int semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public boolean equals(Object object){

        if(object instanceof Pressure){

            Pressure other = (Pressure)object;
            return  getSystolic().equals(other.getSystolic()) && getDiastolic().equals(other.getDiastolic())
                    && getPulse().equals(other.getPulse());
        }
        return false;
    }

}
