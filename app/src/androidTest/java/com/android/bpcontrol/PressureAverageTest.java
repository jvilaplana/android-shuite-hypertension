package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.Pressures;
import com.android.bpcontrol.model.PressuresAfternoon;
import com.android.bpcontrol.model.PressuresMorning;

/**
 * Created by Adrian on 15/02/2015.
 */
public class PressureAverageTest extends AndroidTestCase {

    private BPcontrolDB db;
    private PressuresMorning morningfake;
    private PressuresAfternoon afternoonfake;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
//        db = new PressuresDataBase(context);
            initializeFakes();
//        db.addMorningPreasure(newPressure,"0");
//        db.addAfternoonPreasure(newPressure,"0");
    }


    public void testObtainMorningAvarage(){

        Pressure fake = new Pressure(108,72,81);

        assertEquals(morningfake.obtainPressuresAverage(), fake);
    }

    public void testObtainAfternoonAverage(){

        Pressure fake = new Pressure(130,89,86);

        assertEquals(afternoonfake.obtainPressuresAverage(),fake);

    }

    public void testdayPressuresAverage(){

        Pressure fake = new Pressure(119,80,83);
        Pressure tmp = Pressures.obtainPressuresDayAverage(morningfake,afternoonfake);
        assertEquals(Pressures.obtainPressuresDayAverage(morningfake,afternoonfake),fake);

    }

    private void initializeFakes(){

        morningfake = new PressuresMorning();
        afternoonfake = new PressuresAfternoon();

        Pressure fake1m = new Pressure(120,80,100);
        Pressure fake2m = new Pressure(106,69,92);
        Pressure fake3m= new Pressure(110,75,70);

        Pressure fake1n = new Pressure(150,105,100);
        Pressure fake2n = new Pressure(134,100,90);
        Pressure fake3n= new Pressure(126,78,82);

        morningfake.add(fake1m);
        morningfake.add(fake2m);
        morningfake.add(fake3m);

        afternoonfake.add(fake1n);
        afternoonfake.add(fake2n);
        afternoonfake.add(fake3n);

    }



}
