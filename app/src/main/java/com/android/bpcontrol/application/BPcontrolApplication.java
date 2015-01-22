package com.android.bpcontrol.application;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by Adrian Carrera on 22/1/15.
 */
public class BPcontrolApplication extends Application{

    public static enum RobotoTypeface{
        Regular,
        Bold,
        Italic
    }

    private Typeface robotoItalic,robotoBold,robotoRegular;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public Typeface getTypeface(RobotoTypeface type){

        switch(type){
            case Regular:
                return getRobotoRegular();
            case Bold:
                return getRobotoBold();
            case Italic:
                return getRobotoItalic();
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
}




