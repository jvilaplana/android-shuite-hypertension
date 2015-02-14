package com.android.bpcontrol.model;

/**
 * Created by Adrian on 14/2/15.
 */
public class Pressure{

    private int id;
    private String systolic;
    private String diastolic;
    private String pulse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object object){

        if(object instanceof Pressure){

            Pressure other = (Pressure)object;
            return getId()==other.getId() && getSystolic().equals(other.getSystolic()) && getDiastolic().equals(other.getDiastolic())
                    && getPulse().equals(other.getPulse());
        }
        return false;
    }
}
