package com.android.bpcontrol.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.YoutubeLink;
import com.android.bpcontrol.utils.DateUtils;
import com.android.bpcontrol.utils.LogBP;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian on 14/2/15.
 */
public class BPcontrolDB extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PressuresDB";

//    private static final String TABLE_PRESSURESMORNING = "PressuresMorning";
//    private static final String TABLE_PRESSURESAFTERNOON = "PressuresAfternoon";
    private static final String TABLE_PRESSURESAVERAGE = "PressuresAverage";
    private static final String TABLE_YOUTUBE = "YoutubeLink";

    private static final String KEY_ID = "id";

    private static final String KEY_SYSTOLIC = "systolic";
    private static final String KEY_DIASTOLIC = "diastolic";
    private static final String KEY_PULSE = "pulse";
    private static final String KEY_DATE = "date";
    private static final String KEY_SEMAPHORE = "semaphore";

    private static final String KEY_URL="url";

    public BPcontrolDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        LogBP.writelog("DATABASE","Database created");

//        String CREATE_PRESSURESMORNING_TABLE = "CREATE TABLE PressuresMorning ( " +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "systolic TEXT, "+
//                "diastolic TEXT, "+
//                "pulse TEXT, "+
//                "semaphore INTEGER )";
//
//        String CREATE_PRESSURESAFTERNOON_TABLE = "CREATE TABLE PressuresAfternoon ( " +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "systolic TEXT, "+
//                "diastolic TEXT, "+
//                "pulse TEXT, "+
//                "semaphore INTEGER )";

        String CREATE_PRESSURESAVERAGE_TABLE = "CREATE TABLE PressuresAverage ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, "+
                "systolic TEXT, "+
                "diastolic TEXT, "+
                "pulse TEXT, "+
                "semaphore INTEGER )";

        String CREATE_YOUTUBE_TABLE = "CREATE TABLE YoutubeLink ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "url TEXT )";

        db.execSQL(CREATE_PRESSURESAVERAGE_TABLE);
        db.execSQL(CREATE_YOUTUBE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogBP.writelog("DATABASE","Database upgrade");
        db.execSQL("DROP TABLE IF EXISTS PressuresMorning");
        db.execSQL("DROP TABLE IF EXISTS PressuresAfternoon");
        db.execSQL("DROP TABLE IF EXISTS PressuresAverage");
        db.execSQL("DROP TABLE IF EXISTS YoutubeLink");
        this.onCreate(db);
    }

    public void addPressureAverage(Pressure pressure,String semaphore){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, DateUtils.dateToString(pressure.getDate(),DateUtils.DEFAULT_FORMAT));
        values.put(KEY_SYSTOLIC, pressure.getSystolic());
        values.put(KEY_DIASTOLIC, pressure.getDiastolic());
        values.put(KEY_PULSE, pressure.getPulse());
        values.put(KEY_SEMAPHORE,semaphore);

        db.insert(TABLE_PRESSURESAVERAGE, null, values);

        db.close();

    }

    public void addYoutubeLink(YoutubeLink youtubeLink){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_URL,youtubeLink.getUrl());

        db.insert(TABLE_YOUTUBE, null, values);

        db.close();

    }
    private List<YoutubeLink> getAllYoutubeLinks(){

        List<YoutubeLink> listpressures = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_PRESSURESAVERAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        YoutubeLink link = null;
        if (cursor.moveToFirst()) {
            do {
                link = new YoutubeLink(cursor.getString(1));
                link.setId(Integer.parseInt(cursor.getString(0)));
                listpressures.add(link);
            } while (cursor.moveToNext());
        }

        LogBP.writelog("DATABASE","getAllYoutubeLinks()");

        return listpressures;

    }


    private List<Pressure> getAllPressureAverage() throws ParseException {

        List<Pressure> listpressures = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_PRESSURESAVERAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Pressure pressure = null;
        if (cursor.moveToFirst()) {
            do {
                pressure = new Pressure();
                pressure.setId(Integer.parseInt(cursor.getString(0)));
                pressure.setDate(DateUtils.stringToDate(cursor.getString(1), DateUtils.DEFAULT_FORMAT));
                pressure.setSystolic(cursor.getString(2));
                pressure.setDiastolic(cursor.getString(3));
                pressure.setPulse(cursor.getString(4));
                listpressures.add(pressure);
            } while (cursor.moveToNext());
        }

        LogBP.writelog("DATABASE","getAllAfternoonPressures()");

        return listpressures;

    }


    private int pressureUpdate(String table, Pressure pressure){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String date = DateUtils.dateToString(pressure.getDate(),DateUtils.DEFAULT_FORMAT);
        values.put(KEY_DATE, date);
        values.put(KEY_SYSTOLIC, pressure.getSystolic() );
        values.put(KEY_DIASTOLIC, pressure.getDiastolic());
        values.put(KEY_PULSE, pressure.getPulse());

        int res = db.update(table, values, KEY_DATE+" = ?",new String[] {date});

        db.close();

        return res;

    }
    public int updatePressureAverage(Pressure pressure){

        return pressureUpdate(TABLE_PRESSURESAVERAGE,pressure);
    }

    public int updateYoutubeLink(YoutubeLink youtubeLink){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(KEY_URL,youtubeLink.getUrl());

        int res = db.update(TABLE_YOUTUBE, values, KEY_ID+" = ?",new String[] { String.valueOf(youtubeLink.getId())});

        db.close();

        return res;
    }

    public void deletePressureAverage(Pressure pressure){

        deletePressure(TABLE_PRESSURESAVERAGE,pressure);
    }

    public void deleteYoutubeLink(Pressure pressure){

        deletePressure(TABLE_YOUTUBE, pressure);
    }

    private void deletePressure(String table, Pressure pressure){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table,
                 KEY_ID+" = ?",
                new String[] { String.valueOf(pressure.getId()) });

        db.close();
    }


//    public void deletePressureMorning(Pressure pressure) {
//
//        deletePressure(TABLE_PRESSURESMORNING,pressure);
//    }
//
//
//    public void deletePressureAfternoon(Pressure pressure) {
//
//        deletePressure(TABLE_PRESSURESAFTERNOON,pressure);
//    }


//    public int updatePressureMorning(Pressure pressure) {
//
//        return pressureUpdate(TABLE_PRESSURESMORNING,pressure);
//    }
//
//
//    public int updatePressureAfternoon(Pressure pressure) {
//
//        return pressureUpdate(TABLE_PRESSURESAFTERNOON,pressure);
//
//    }


//    public List<Pressure> getAllMorningPressures(){
//
//        return getAllPressure(TABLE_PRESSURESMORNING);
//    }
//
//    public List<Pressure> getAllAfternoonPressures(){
//
//        return getAllPressure(TABLE_PRESSURESAFTERNOON);
//    }


//
//    public void addMorningPreasure(Pressure pressure,String semaphore){
//
//        addPressure(TABLE_PRESSURESMORNING,pressure,semaphore);
//    }
//
//    public void addAfternoonPreasure(Pressure pressure, String semaphore){
//
//        addPressure(TABLE_PRESSURESAFTERNOON,pressure,semaphore);
//    }



}
