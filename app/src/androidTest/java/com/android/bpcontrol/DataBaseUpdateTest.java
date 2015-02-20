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
public class DataBaseUpdateTest extends AndroidTestCase{

    private BPcontrolDB db;
    private Pressure fake;
    private Pressure newPressure;
    private YoutubeVideo fakelink;
    private YoutubeVideo newLink;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new BPcontrolDB(context);

        Date date = new Date();
        fake = new Pressure();
        fake.setSystolic("120");
        fake.setDiastolic("90");
        fake.setPulse("75");
        fake.setDate(date);

        fakelink = new YoutubeVideo("","youtube.com");
        fakelink.setId(1);
        newPressure = new Pressure();
        newPressure.setSystolic("120");
        newPressure.setDiastolic("80");
        newPressure.setPulse("75");
        newPressure.setDate(date);

        newLink = new YoutubeVideo("","youtube.com/url");
        newLink.setId(1);
        db.addPressureAverage(fake, "0");
        db.addYoutubeVideo(fakelink);
    }

    public void testUpdatePressure() throws ParseException {

        int res = db.updatePressureAverage(newPressure);
        ArrayList<Pressure> pressures = (ArrayList<Pressure>)db.getAllPressureAverage();
        assertTrue(pressures.get(0).getDiastolic().equals(newPressure.getDiastolic()));
    }

    public void testUpdateYoutubeLink() {

        int res = db.updateYoutubeVideo(newLink);

        ArrayList<YoutubeVideo> links = (ArrayList<YoutubeVideo>)db.getAllYoutubeLinks();

        assertTrue(links.get(0).getVideoId().equals(newLink.getVideoId()));

    }
    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }


}
