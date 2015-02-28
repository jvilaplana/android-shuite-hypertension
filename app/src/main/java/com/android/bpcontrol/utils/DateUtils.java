package com.android.bpcontrol.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Adrian on 15/02/2015.
 */
public class DateUtils {

    public static String DEFAULT_FORMAT = "dd-MM-yyyy";
    public static String WS_RECEIVEFORMAT="yyyy-MM-dd";
    public static String WS_SENDFORMAT = "yyyyMMdd";
    public static String DB_FORMAT = WS_RECEIVEFORMAT;
    //public static String CURRENT_TIME_FORMAT ="dd-MM-yyy HH:mm:ss";

    public static Date stringToDate(String date,String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(date);
    }

    public static String dateToString(Date date, String format){

        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date wsDateStringToDateClass(String wsCompleteDate) throws ParseException {

        if (wsCompleteDate.contains("T")) {
            String wsdate = wsCompleteDate.split("T")[0];
            return stringToDate(wsdate,WS_RECEIVEFORMAT);
        }else{
            return stringToDate(wsCompleteDate,WS_RECEIVEFORMAT);
        }
    }


    public static Date wsStringDateToDefaultDate(String date) throws ParseException {

        final Date wsdateclass = wsDateStringToDateClass(date);

        final String default_stringdate = dateToString(wsdateclass,DEFAULT_FORMAT);

        return stringToDate(default_stringdate, DEFAULT_FORMAT);
    }

    public static String currentDateToString(){

        return dateToString(new Date(),DEFAULT_FORMAT);
    }

    public static String dateStringToWSdate(String date) throws ParseException {

        Date date_default = stringToDate(date,DEFAULT_FORMAT);

        return dateToString(date_default,WS_SENDFORMAT);
    }

    public static int differenceInDays(Date firstDate, Date secondDate){

        long difference = firstDate.getTime() - secondDate.getTime();


        return (int) difference / (24 * 60 * 60 * 1000);

    }

    public static long differenceInDays(Calendar start, Calendar end){

        long endtime = start.getTimeInMillis();
        long starttime = end.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(endtime - starttime));
    }

    public static boolean isDateEqualsToTodayDate(String date) throws ParseException {

        if (date.equals("")){
            return false;
        }
        Date maybeTodayDate = stringToDate(date,DEFAULT_FORMAT);
        Date todayDate = stringToDate(dateToString(new Date(),DEFAULT_FORMAT),DEFAULT_FORMAT);

        if (todayDate.equals(maybeTodayDate)){
            return true;
        }
        return false;
    }


    public static ArrayList<String> getDates(int days) {
        Calendar calendar = Calendar.getInstance();
        String[] dates = new String[days];
        for (int i=0;i<days;i++){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            Date date = calendar.getTime();
            dates[i]=DateUtils.dateToString(date,DateUtils.DEFAULT_FORMAT);
        }
        return new ArrayList<String>(Arrays.asList(dates));
    }

    public static ArrayList<String> getDatesBetween(String date1,String date2){

        ArrayList<String> dates = new ArrayList<>();
        int days = 0;
        try {
            days = differenceInDays(stringToDate(date1+" 00:00",DEFAULT_FORMAT),stringToDate(date2+" 23:59",DEFAULT_FORMAT));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(stringToDate(date1,DEFAULT_FORMAT));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar2 = Calendar.getInstance();
        try {
            calendar2.setTime(stringToDate(date2,DEFAULT_FORMAT));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        days = (int) differenceInDays(calendar,calendar2);

        dates.add(DateUtils.dateToString(calendar.getTime(),DateUtils.DEFAULT_FORMAT));

        for (int i=0;i<days-1;i++){
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Date date = calendar.getTime();
            dates.add(DateUtils.dateToString(date,DateUtils.DEFAULT_FORMAT));
        }

        return dates;
    }

}
