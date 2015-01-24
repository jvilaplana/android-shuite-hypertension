package com.android.bpcontrol.application;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Adrian Carrera on 22/1/15.
 */
public class BPcontrolApplication extends Application{

    public static enum FontsTypeface{
        RobotoRegular,
        RobotoBold,
        RobotoItalic,
        ForteRegular
    }

    private Typeface robotoItalic,robotoBold,robotoRegular,forteRegular;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public Typeface getTypeface(FontsTypeface type){

        switch(type){
            case RobotoRegular:
                return getRobotoRegular();
            case RobotoBold:
                return getRobotoBold();
            case RobotoItalic:
                return getRobotoItalic();
            case ForteRegular:
                return getForteRegular();
        }
        return getRobotoRegular();
    }

    private Typeface getRobotoRegular(){
        if(robotoRegular == null){
            robotoRegular = Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");
        }
        return robotoRegular;
    }

    private Typeface getRobotoBold(){
        if(robotoBold == null){
            robotoBold = Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf");
        }
        return robotoBold;
    }

    private Typeface getRobotoItalic(){
        if(robotoItalic == null){
            robotoItalic= Typeface.createFromAsset(getAssets(),"Roboto-Italic.ttf");
        }
        return robotoItalic;
    }

    private Typeface getForteRegular(){
        if(forteRegular == null){
            forteRegular = Typeface.createFromAsset(getAssets(),"Forte.ttf");
        }
        return forteRegular;
    }
}




