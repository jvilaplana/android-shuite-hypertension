package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.YoutubeVideo;

import java.util.Date;

/**
 * Created by Adrian on 14/2/15.
 */
public class DataBaseAddTest extends AndroidTestCase{

    private BPcontrolDB db;
    private Pressure pFake;
    private YoutubeVideo linkfake;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new BPcontrolDB(context);
        pFake = new Pressure();
        pFake.setId(0);
        pFake.setSystolic("120");
        pFake.setDiastolic("80");
        pFake.setPulse("75");
        pFake.setDate(new Date());

        linkfake = new YoutubeVideo("","youtube.com");
    }

    public void testAddOnePressure(){

        db.addPressureAverage(pFake, "0");
    }

    public void testAddYoutubeLink(){

        db.addYoutubeVideo(linkfake);
    }

    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }
}
