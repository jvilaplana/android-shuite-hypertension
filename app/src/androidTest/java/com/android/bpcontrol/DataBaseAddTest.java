package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.model.Pressure;

/**
 * Created by Adrian on 14/2/15.
 */
public class DataBaseAddTest extends AndroidTestCase{

    private BPcontrolDB db;
    private Pressure fake;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new BPcontrolDB(context);
        fake = new Pressure();
        fake.setId(0);
        fake.setSystolic("120");
        fake.setDiastolic("80");
        fake.setPulse("75");
    }

    public void testAddOnePressureMorning(){

        db.addMorningPreasure(fake,"0");
    }

    public void testAddOnePressureAfternoon(){

        db.addAfternoonPreasure(fake,"0");
    }

    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }
}
