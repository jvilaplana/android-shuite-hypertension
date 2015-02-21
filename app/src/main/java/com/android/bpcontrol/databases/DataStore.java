package com.android.bpcontrol.databases;

import android.provider.ContactsContract;

import com.android.bpcontrol.model.Pressure;

import java.util.ArrayList;

/**
 * Created by Adrian on 21/02/2015.
 */
public class DataStore {

    private ArrayList<Pressure> pressures;

    private static DataStore dstore;

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
        set(pressures);
    }

    private void set(ArrayList<Pressure> pressures){

        for (int i=pressures.size()-1;i>-1;i--){

            this.pressures.add(pressures.get(i));

        }
    }
}
