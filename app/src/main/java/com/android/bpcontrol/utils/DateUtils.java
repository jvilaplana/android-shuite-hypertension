package com.android.bpcontrol.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adrian on 15/02/2015.
 */
public class DateUtils {

    public static String DEFAULT_FORMAT = "dd-MM-yyyy";
    public static String WS_RECEIVEFORMAT="yyyy-MM-dd";
    public static String WS_SENDFORMAT = "yyyyMMdd";
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

        String wsdate = wsCompleteDate.split("T")[0];

        return stringToDate(wsdate,WS_RECEIVEFORMAT);


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
}
