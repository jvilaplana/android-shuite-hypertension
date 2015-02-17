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
public class DataBaseUpdateTest extends AndroidTestCase{

    private BPcontrolDB db;
    private Pressure fake;
    private Pressure newPressure;
    private YoutubeLink fakelink;
    private YoutubeLink newLink;

    public void setUp(){
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new BPcontrolDB(context);

        Date date = new Date();
        fake = new Pressure();
        fake.setId(1);
        fake.setSystolic("120");
        fake.setDiastolic("90");
        fake.setPulse("75");
        fake.setDate(date);

        fakelink = new YoutubeLink("youtube.com");

        newPressure = new Pressure();
        newPressure.setSystolic("120");
        newPressure.setDiastolic("80");
        newPressure.setPulse("75");
        newPressure.setDate(date);

        newLink = new YoutubeLink("youtube.com/url");

        db.addPressureAverage(fake, "0");
        db.addYoutubeLink(fakelink);
    }

    public void testUpdatePressure() throws ParseException {

        int res = db.updatePressureAverage(newPressure);
        ArrayList<Pressure> pressures = (ArrayList<Pressure>)db.getAllPressureAverage();
        assertTrue(pressures.get(0).getDiastolic().equals(newPressure.getDiastolic()));
    }

    public void testUpdateYoutubeLink() {

        int res = db.updateYoutubeLink(newLink);

        ArrayList<YoutubeLink> links = (ArrayList<YoutubeLink>)db.getAllYoutubeLinks();

        assertTrue(links.get(0).getUrl().equals(newLink.getUrl()));

    }
    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }


}
