package com.android.bpcontrol.utils;

import com.android.bpcontrol.model.Pressure;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Adrian on 28/02/2015.
 */
public class DatabasePlotMock {



    public static List<Pressure> getOneMonthPressures(){

        List<Pressure> list = new ArrayList<>();

        Pressure pressure;
        List<String> dates = DateUtils.getDates(30);

        for (int i=0;i<30;i++){
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

    public static List<Pressure> getThreeMonthPressures(){
        List<Pressure> list = new ArrayList<>();

        Pressure pressure;
        List<String> dates = DateUtils.getDates(90);

        for (int i=0;i<90;i++){
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

    public static List<Pressure> getSixMonthPressures(){
        List<Pressure> list = new ArrayList<>();

        Pressure pressure;
        List<String> dates = DateUtils.getDates(180);

        for (int i=0;i<180;i++){
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
