package com.android.bpcontrol.utils;

import android.util.Log;

/**
 * Created by Adrian on 22/1/15.
 */
public class LogBP {

    private static LogBP instance = null;

    private boolean isDebugMode = true;

    private LogBP(){}

    public static LogBP getInstance(){

        if (instance == null){
            return  new LogBP();
        }

        return instance;
    }

    public static void writelog(String tag,String message){

        if (getInstance().isDebugMode){

            Log.d(tag,message);
        }

    }

    public static void writelog(String message){

        if (getInstance().isDebugMode){

            Log.d("LogBP",message);
        }
    }

    public static void printStackTrace(Exception ex){
        if (getInstance().isDebugMode){

            ex.printStackTrace();
        }

    }


    public static void setDebugMode(boolean debugMode){

        getInstance().isDebugMode = debugMode;
    }
}
