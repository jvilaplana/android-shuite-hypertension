package com.android.bpcontrol.test;

import com.android.bpcontrol.fragments.PressuresFragment;
import com.android.bpcontrol.fragments.PressuresPlotFragment;
import com.android.bpcontrol.model.Message;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.utils.DateUtils;

import java.io.BufferedReader;
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
            case CUSTOM:
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

    public static List<Message> getFakeMessages(){

      List<Message> messages = new ArrayList<>();

        Message ms1 = new Message();
        ms1.setUser(true);
        ms1.setContent("Hola, que tal?");
        Message ms2 = new Message();
        ms2.setUser(false);
        ms2.setContent("Bien, y tu?");
        Message ms3 = new Message();
        ms3.setUser(false);
        ms3.setContent("Como ha ido el verano?");
        Message ms4 = new Message();
        ms4.setUser(true);
        ms4.setContent("Bien bien, y a ti que tal?");
        Message ms5 = new Message();
        ms5.setContent("Guay tio");
        ms5.setUser(false);

        messages.add(ms1);
        messages.add(ms2);
        messages.add(ms3);
        messages.add(ms4);
        messages.add(ms5);


        Message ms6 = new Message();
        ms6.setUser(true);
        ms6.setContent("Hola, que tal?");
        Message ms7 = new Message();
        ms7.setUser(false);
        ms7.setContent("Bien, y tu?");
        Message ms8 = new Message();
        ms8.setUser(false);
        ms8.setContent("Como ha ido el verano?");
        Message ms9 = new Message();
        ms9.setUser(true);
        ms9.setContent("Bien bien, y a ti que tal?");
        Message ms10 = new Message();
        ms10.setContent("Guay tio");
        ms10.setUser(false);

        messages.add(ms6);
        messages.add(ms7);
        messages.add(ms8);
        messages.add(ms9);
        messages.add(ms10);

        return messages;
    }

}
