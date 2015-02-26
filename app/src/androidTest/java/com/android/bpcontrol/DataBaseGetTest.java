package com.android.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.YoutubeVideo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adrian on 14/2/15.
 */
public class DataBaseGetTest extends AndroidTestCase{

    private BPcontrolDB db;
    private Pressure fake;
    private YoutubeVideo link;

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

        link = new YoutubeVideo("","youtube.com");

        db.addPressureAverage(fake);
        db.addYoutubeVideo(link);
    }

    public void testgetAllAfternoonPressures() throws ParseException {

        ArrayList<Pressure> list =(ArrayList<Pressure>) db.getAllPressureAverage();
        assertEquals(fake,list.get(0));
    }

    public void testgetAllYoutubeLink() {

        ArrayList<YoutubeVideo> list =(ArrayList<YoutubeVideo>) db.getAllYoutubeVideos();
        assertEquals(link.getVideoId(),list.get(0).getVideoId());
    }


    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }
}
