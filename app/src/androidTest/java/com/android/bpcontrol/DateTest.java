package com.android.bpcontrol;

import android.test.AndroidTestCase;


import java.text.ParseException;
import java.util.Date;


import static com.android.bpcontrol.utils.DateUtils.*;
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


    public void tearDown() throws Exception{

        super.tearDown();
    }
}
