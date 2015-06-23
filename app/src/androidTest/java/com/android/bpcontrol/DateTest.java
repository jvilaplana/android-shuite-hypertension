package com.hesoftgroup.bpcontrol;

import android.test.AndroidTestCase;

import com.hesoftgroup.bpcontrol.utils.DateUtils;

import junit.framework.Assert;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


import static com.hesoftgroup.bpcontrol.utils.DateUtils.*;
/**
 * Created by Adrian on 21/02/2015.
 */
public class DateTest extends AndroidTestCase {

    private final String WSstringdate = "2015-02-01T19:01:32";
    private final String defaultDate = "01-02-2015";

    public void setUp(){

    }

    public void test_convert_WS_string_date_to_date_class() throws ParseException{

        final Date wsdateclass = wsDateStringToDateClass(WSstringdate);
        final String date = dateToString(wsdateclass, WS_RECEIVEFORMAT);

        assertEquals(date,"2015-02-01");
    }

    public void test_convert_WS_string_date_to_default_date() throws ParseException {

        final Date wsdateclass = wsDateStringToDateClass(WSstringdate);

        final String date = dateToString(wsdateclass,DEFAULT_FORMAT);

        assertEquals(date,defaultDate);
    }

    public void test_convert_defaultDateToWSdate() throws ParseException {

        assertEquals(dateStringToWSdate(defaultDate),"20150201");
    }



    public void test_trigger_parse_exception_by_bad_parse(){

        try {
            stringToDate("12-03-1999",DEFAULT_FORMAT);
        } catch (ParseException e) {
            Assert.fail("ParseException");
            e.printStackTrace();
        }
    }

    public void test_calculate_differente_between_two_dates() throws ParseException {

        Date d1 = stringToDate("21-02-2015",DEFAULT_FORMAT);
        Date d2 = stringToDate("15-02-2015",DEFAULT_FORMAT);

        int days = differenceInDays(d1,d2);

        assertTrue(days==6);
    }

    public void test_is_date_equals_to_today_date() throws ParseException {

        String todaydate = dateToString(new Date(),DEFAULT_FORMAT);
        assertTrue(isDateEqualsToTodayDate(todaydate));
    }


    public void test_obtain_dates_between_two_dates(){

        ArrayList<String> list = DateUtils.getDatesBetween("21-11-2014", "25-12-2014");
        assertTrue(list.size()==34);
    }

    public void tearDown() throws Exception{

        super.tearDown();
    }
}
