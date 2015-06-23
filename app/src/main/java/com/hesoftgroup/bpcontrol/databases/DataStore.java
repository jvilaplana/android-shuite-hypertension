package com.hesoftgroup.bpcontrol.databases;

import android.provider.ContactsContract;

import com.hesoftgroup.bpcontrol.model.Pressure;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adrian on 21/02/2015.
 */
public class DataStore {

    private ArrayList<Pressure> pressures;

    private static DataStore dstore;

    private int unReadMessages = 0;

    private DataStore(){
        pressures = new ArrayList<>();
    }

    public static DataStore getInstance(){

        if(dstore == null){
            dstore = new DataStore();
        }
        return dstore;
    }

    public ArrayList<Pressure> getPressures() {
        return pressures;
    }

    public void setPressures(ArrayList<Pressure> pressures) {
        this.pressures.addAll(pressures);
    }

    public int pressuresSize(){

       return  pressures.size();
    }
    public void setUnReadMessages(int unReadMessages){
        this.unReadMessages = unReadMessages;
    }
    public int getUnreadMessages(){
        return unReadMessages;
    }
}
