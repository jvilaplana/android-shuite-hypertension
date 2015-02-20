package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.HistorialAdapterItem;

/**
 * Created by Adrian on 20/02/2015.
 */
public class HistorialHeader implements HistorialAdapterItem {

    private final int tag = 2;

    private String date;


    public String getDate() {
        return date;
    }

    @Override
    public int getTag() {
        return tag;
    }
}
