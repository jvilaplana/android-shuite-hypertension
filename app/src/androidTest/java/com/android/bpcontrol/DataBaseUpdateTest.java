package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.PressuresDataBase;
import com.android.bpcontrol.model.Pressure;

import java.util.ArrayList;

/**
 * Created by Adrian on 14/2/15.
 */
public class DataBaseUpdateTest extends AndroidTestCase{

    private PressuresDataBase db;
    private Pressure fake;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new PressuresDataBase(context);
        fake = new Pressure();
        fake.setId(1);
        fake.setSystolic("120");
        fake.setDiastolic("90");
        fake.setPulse("75");

        Pressure newPressure = new Pressure();
        newPressure.setSystolic("120");
        newPressure.setDiastolic("80");
        newPressure.setPulse("75");


        db.addMorningPreasure(newPressure,"0");
        db.addAfternoonPreasure(newPressure,"0");
    }

    public void testUpdateMorningPressure() {

        int res = db.updatePressureMorning(fake);
        ArrayList<Pressure> pressures = (ArrayList<Pressure>)db.getAllMorningPressures();
        assertTrue(pressures.get(0).getDiastolic().equals(fake.getDiastolic()));
    }

    public void testUpdateAfternoonPressure() {

        int res = db.updatePressureAfternoon(fake);

        ArrayList<Pressure> pressures = (ArrayList<Pressure>)db.getAllAfternoonPressures();
        System.out.println(""+pressures.get(0).getDiastolic()+"sds "+pressures.get(0).getId());
        assertTrue(pressures.get(0).getDiastolic().equals(fake.getDiastolic()));

    }
    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }


}
