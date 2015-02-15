package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.PressuresDataBase;
import com.android.bpcontrol.model.Pressure;

import java.util.ArrayList;

/**
 * Created by Adrian on 14/2/15.
 */
public class DataBaseGetTest extends AndroidTestCase{

    private PressuresDataBase db;
    private Pressure fake;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new PressuresDataBase(context);
        fake = new Pressure();
        fake.setId(1);
        fake.setSystolic("120");
        fake.setDiastolic("80");
        fake.setPulse("75");

        db.addMorningPreasure(fake,"0");
        db.addAfternoonPreasure(fake, "0");
    }

    public void testgetAllAfternoonPressures() {

        ArrayList<Pressure> list =(ArrayList<Pressure>) db.getAllAfternoonPressures();
        assertEquals(fake,list.get(0));
    }

    public void testgetAllMorningPressures() {

        ArrayList<Pressure> list =(ArrayList<Pressure>) db.getAllMorningPressures();
        assertEquals(fake,list.get(0));
    }

    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }
}
