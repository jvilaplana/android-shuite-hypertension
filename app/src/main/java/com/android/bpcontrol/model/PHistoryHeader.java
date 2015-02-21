package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.PHistoryAdapterItem;

/**
 * Created by Adrian on 20/02/2015.
 */
public class PHistoryHeader implements PHistoryAdapterItem {

    private final int tag = 2;

    private String date;

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
//        String resultdate=null,month=null;
//
//        String[] dateitems = date.split("-");
//
//
//
//        switch ()
//        this.date = (dateitems[0]+" "+month+""+dateitems[2]);
        this.date = date;
    }
}
