package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.PHistoryAdapterItem;
import com.android.bpcontrol.utils.LogBP;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Adrian on 20/02/2015.
 */
public class PHistoryHeader implements PHistoryAdapterItem {

    private final int tag = 2;

    private String date;

    public static String[] MONTHS;

    public PHistoryHeader(String date){
        modifyDateFormat(date);
    }

    public String getDate() {
        return date;
    }

    @Override
    public int getTag() {
        return tag;
    }

    private void modifyDateFormat(String date){
        String[] dateitems = date.split("-");

        switch (Integer.parseInt(dateitems[1])){

            case 1:
                this.date = dateitems[0]+" "+MONTHS[0]+" "+dateitems[2];
                break;
            case 2:
                this.date = dateitems[0]+" "+MONTHS[1]+" "+dateitems[2];
                break;
            case 3:
                this.date = dateitems[0]+" "+MONTHS[2]+" "+dateitems[2];
                break;
            case 4:
                this.date = dateitems[0]+" "+MONTHS[3]+" "+dateitems[2];
                break;
            case 5:
                this.date = dateitems[0]+" "+MONTHS[4]+" "+dateitems[2];
                break;
            case 6:
                this.date = dateitems[0]+" "+MONTHS[5]+" "+dateitems[2];
                break;
            case 7:
                this.date = dateitems[0]+" "+MONTHS[6]+" "+dateitems[2];
                break;
            case 8:
                this.date = dateitems[0]+" "+MONTHS[7]+" "+dateitems[2];
                break;
            case 9:
                this.date = dateitems[0]+" "+MONTHS[8]+" "+dateitems[2];
                break;
            case 10:
                this.date = dateitems[0]+" "+MONTHS[9]+" "+dateitems[2];
                break;
            case 11:
                this.date = dateitems[0]+" "+MONTHS[10]+" "+dateitems[2];
                break;
            case 12:
                this.date = dateitems[0]+" "+MONTHS[11]+" "+dateitems[2];
                break;
        }
    }
}
