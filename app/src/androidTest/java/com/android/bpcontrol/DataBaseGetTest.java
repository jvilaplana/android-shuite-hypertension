package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.YoutubeLink;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adrian on 14/2/15.
 */
public class DataBaseGetTest extends AndroidTestCase{

    private BPcontrolDB db;
    private Pressure fake;
    private YoutubeLink link;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new BPcontrolDB(context);
        fake = new Pressure();
        fake.setId(1);
        fake.setSystolic("120");
        fake.setDiastolic("80");
        fake.setPulse("75");
        fake.setDate(new Date());

        link = new YoutubeLink("youtube.com");

        db.addPressureAverage(fake,"0");
        db.addYoutubeLink(link);
    }

    public void testgetAllAfternoonPressures() throws ParseException {

        ArrayList<Pressure> list =(ArrayList<Pressure>) db.getAllPressureAverage();
        assertEquals(fake,list.get(0));
    }

    public void testgetAllYoutubeLink() {

        ArrayList<YoutubeLink> list =(ArrayList<YoutubeLink>) db.getAllYoutubeLinks();
        assertEquals(link.getUrl(),list.get(0).getUrl());
    }

    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }
}
