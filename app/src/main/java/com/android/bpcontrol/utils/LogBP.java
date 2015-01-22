package com.android.bpcontrol.utils;

import android.util.Log;

/**
 * Created by Adrian on 22/1/15.
 */
public class LogBP {

    private static LogBP instance = null;

    public boolean isDebugMode = true;

    public static LogBP getInstance(){

        if (instance == null){
            return  new LogBP();
        }

        return instance;
    }

    public void writelog(String tag,String message){

        if (isDebugMode){

            Log.d(tag,message);
        }

    }

    public void writelog(String message){

        if (isDebugMode){

            Log.d("LogBP",message);
        }
    }


    public void setDebugMode(boolean debugMode){

        this.isDebugMode = debugMode;
    }
}
