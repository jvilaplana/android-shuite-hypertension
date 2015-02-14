package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.PressuresDataBase;
import com.android.bpcontrol.model.Pressure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 14/2/15.
 */
public class DataBaseAddTest extends AndroidTestCase{

    private PressuresDataBase db;
    private Pressure fake;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new PressuresDataBase(context);
        fake = new Pressure();
        fake.setId(0);
        fake.setSystolic("120");
        fake.setDiastolic("80");
        fake.setPulse("75");
    }

    public void testAddOnePressureMorning(){

        db.addMorningPreasure(fake);
    }

    public void testAddOnePressureAfternoon(){

        db.addAfternoonPreasure(fake);
    }

    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }
}
