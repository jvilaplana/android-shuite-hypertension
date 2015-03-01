package com.android.bpcontrol.test;

import com.android.bpcontrol.fragments.PressuresFragment;
import com.android.bpcontrol.fragments.PressuresPlotFragment;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Adrian on 28/02/2015.
 */
public class DatabasePlotMock {



    public static List<Pressure> getFakePressures(PressuresPlotFragment.PressuresPlot type){

        List<Pressure> list = new ArrayList<>();
        List<String> dates = null;

        Pressure pressure;

        int days = 0;

        switch (type){

            case ONE_MONTH:
                days = 30;
                 dates = DateUtils.getDates(30);
                break;
            case THREE_MONTH:
                days = 90;
                dates = DateUtils.getDates(90);
                break;
            case SIX_MONTH:
                days = 180;
                dates = DateUtils.getDates(180);
                break;
        }

        for (int i=0;i<days;i++){
            pressure = new Pressure();
            try {
                pressure.setDate(DateUtils.stringToDate(dates.get(i),DateUtils.DEFAULT_FORMAT));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            pressure.setSystolic(String.valueOf(generateRandom(50,220)));
            pressure.setDiastolic(String.valueOf(generateRandom(30,140)));
            pressure.setPulse(String.valueOf(generateRandom(50,220)));
            list.add(pressure);
        }
        return  list;
    }

    private static int generateRandom(int low,int hight){
        Random r = new Random();
        return  r.nextInt(hight-low)+low;
    }

}
