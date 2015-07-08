package com.hesoftgroup.bpcontrol;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.hesoftgroup.bpcontrol.databases.BPcontrolDB;
import com.hesoftgroup.bpcontrol.model.Pressure;
import com.hesoftgroup.bpcontrol.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Adrian on 27/2/15.
 */
public class GraphicsDBCheck extends AndroidTestCase {

    private BPcontrolDB db;


    public void setUp(){

        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new BPcontrolDB(context);

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH,6);

        List<Pressure> list = new ArrayList<Pressure>();
        Pressure p;
        for (int i=0;i<15;i++){
            calendar.add(Calendar.DAY_OF_MONTH,1);
            p = new Pressure();
            p.setSystolic("123");
            p.setDiastolic("89");
            p.setSemaphore(0);
            p.setPulse("67");
            p.setDate(calendar.getTime());
            list.add(p);
        }

        db.addAllPressuresAverage(list);


    }


//    public void test_get_pressures_between_two_dates() throws ParseException {
//
//        Calendar calendar = Calendar.getInstance();
//        Date d1 = calendar.getTime();
//
//        calendar.add(Calendar.DAY_OF_MONTH,30);
//        List<Pressure> pressurelist = db.getAllPressureAverage();
//        Date d2 = calendar.getTime();
//        List<Pressure> pressures = db.getPressuresAverageBetweenTwoDates(DateUtils.dateToString(d1,DateUtils.DB_FORMAT),DateUtils.dateToString(d2, DateUtils.DB_FORMAT));
//        assertTrue(pressures_between_two_dates(pressures,DateUtils.stringToDate(DateUtils.dateToString(d1,DateUtils.DEFAULT_FORMAT),DateUtils.DEFAULT_FORMAT)
//                ,DateUtils.stringToDate(DateUtils.dateToString(d2, DateUtils.DEFAULT_FORMAT), DateUtils.DEFAULT_FORMAT)));
//
//    }


    private boolean pressures_between_two_dates(List<Pressure> pressures, Date d1, Date d2){

        boolean isbetween=true;

        for(Pressure p:pressures){
            if (p.getDate().before(d1) || p.getDate().before(d2)){
                isbetween = false;
                break;
            }

        }

        return isbetween;


    }

    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }


}
