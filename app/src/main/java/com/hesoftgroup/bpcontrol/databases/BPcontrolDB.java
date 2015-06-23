package com.hesoftgroup.bpcontrol.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hesoftgroup.bpcontrol.model.YoutubeVideo;
import com.hesoftgroup.bpcontrol.model.Pressure;
import com.hesoftgroup.bpcontrol.utils.DateUtils;
import com.hesoftgroup.bpcontrol.utils.LogBP;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian on 14/2/15.
 */
public class    BPcontrolDB extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PressuresDB";

 //     private static final String TABLE_PRESSURESMORNING = "PressuresMorning";
//    private static final String TABLE_PRESSURESAFTERNOON = "PressuresAfternoon";
    private static final String TABLE_PRESSURESAVERAGE = "PressuresAverage";
    private static final String TABLE_YOUTUBE = "YoutubeLink";

    private static final String KEY_ID = "id";

    private static final String KEY_SYSTOLIC = "systolic";
    private static final String KEY_DIASTOLIC = "diastolic";
    private static final String KEY_PULSE = "pulse";
    private static final String KEY_DATE = "date";
    private static final String KEY_SEMAPHORE = "semaphore";

    private static final String KEY_URL = "url";
    private static final String KEY_DESCRIPTION="description";

    public BPcontrolDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        LogBP.writelog("DATABASE", "Database created");

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
                "date TEXT, " +
                "systolic TEXT, " +
                "diastolic TEXT, " +
                "pulse TEXT, " +
                "semaphore INTEGER )";

        String CREATE_YOUTUBE_TABLE = "CREATE TABLE YoutubeLink ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "description TEXT,"+
                "url TEXT )";

        db.execSQL(CREATE_PRESSURESAVERAGE_TABLE);
        db.execSQL(CREATE_YOUTUBE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogBP.writelog("DATABASE", "Database upgrade");
        db.execSQL("DROP TABLE IF EXISTS PressuresAverage");
        db.execSQL("DROP TABLE IF EXISTS YoutubeLink");
        this.onCreate(db);
    }


    public void addAllPressuresAverage(List<Pressure> list){

        SQLiteDatabase db = this.getWritableDatabase();

        for (Pressure pressure : list){



            ContentValues values = new ContentValues();
            values.put(KEY_DATE, DateUtils.dateToString(pressure.getDate(), DateUtils.DB_FORMAT));
            values.put(KEY_SYSTOLIC, pressure.getSystolic());
            values.put(KEY_DIASTOLIC, pressure.getDiastolic());
            values.put(KEY_PULSE, pressure.getPulse());
            values.put(KEY_SEMAPHORE, pressure.getSemaphore());
            db.insert(TABLE_PRESSURESAVERAGE, null, values);


        }
        db.close();
    }

    public void addPressureAverage(Pressure pressure) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, DateUtils.dateToString(pressure.getDate(), DateUtils.DB_FORMAT));
        values.put(KEY_SYSTOLIC, pressure.getSystolic());
        values.put(KEY_DIASTOLIC, pressure.getDiastolic());
        values.put(KEY_PULSE, pressure.getPulse());
        values.put(KEY_SEMAPHORE, pressure.getSemaphore());
        db.insert(TABLE_PRESSURESAVERAGE, null, values);
        db.close();

    }


    public boolean isPressuresTableEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_PRESSURESAVERAGE,null);

        if (cursor!=null && cursor.moveToFirst()){

            LogBP.writelog("Hola que ase, este es el count"+cursor.getCount());

            return true;
        }
        if (cursor!=null)  cursor.close();
        return false;
    }


    public void addYoutubeVideo(YoutubeVideo youtubeVideo) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_URL, youtubeVideo.getVideoId());
        values.put(KEY_DESCRIPTION,youtubeVideo.getText());

        db.insert(TABLE_YOUTUBE, null, values);

        db.close();

    }

    public List<YoutubeVideo> getAllYoutubeVideos() {

        List<YoutubeVideo> listpressures = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_YOUTUBE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        YoutubeVideo video= null;
        if (cursor.moveToFirst()) {
            do {
                video = new YoutubeVideo(cursor.getString(1),cursor.getString(2));
                video.setId(Integer.parseInt(cursor.getString(0)));
                listpressures.add(video);
            } while (cursor.moveToNext());
        }

        LogBP.writelog("DATABASE", "getAllYoutubeLinks()");

        db.close();
        return listpressures;

    }


    public List<Pressure> getAllPressureAverage() throws ParseException {

        List<Pressure> listpressures = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_PRESSURESAVERAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Pressure pressure = null;
        String[] dateparts=null;
        if (cursor.moveToFirst()) {
            do {
                pressure = new Pressure();
                pressure.setId(Integer.parseInt(cursor.getString(0)));
                dateparts = cursor.getString(1).split("-");
                pressure.setDate(DateUtils.stringToDate(dateparts[2]+"-"+dateparts[1]
                        +"-"+dateparts[0], DateUtils.DEFAULT_FORMAT));
                pressure.setSystolic(cursor.getString(2));
                pressure.setDiastolic(cursor.getString(3));
                pressure.setPulse(cursor.getString(4));
                pressure.setSemaphore(cursor.getInt(5));
                listpressures.add(pressure);
            } while (cursor.moveToNext());
        }

        LogBP.writelog("DATABASE", "getAllAfternoonPressures()");

        db.close();
        return listpressures;

    }
    public List<Pressure> getPressuresAverageBetweenTwoDates(String dateless,String datehight ) throws ParseException {

        List<Pressure> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.query(TABLE_PRESSURESAVERAGE, null, KEY_DATE + " BETWEEN ? AND ?", new String[] {dateless
                ,datehight }, null, null, null, null);

        Pressure pressure = null;
        String[] dateparts=null;
        if (cursor.moveToFirst()) {
            do {
                pressure = new Pressure();
                pressure.setId(Integer.parseInt(cursor.getString(0)));
                pressure.setSystolic(cursor.getString(2));
                dateparts = cursor.getString(1).split("-");
                pressure.setDate(DateUtils.stringToDate(dateparts[2]+"-"+dateparts[1]
                        +"-"+dateparts[0], DateUtils.DEFAULT_FORMAT));
                pressure.setDiastolic(cursor.getString(3));
                pressure.setPulse(cursor.getString(4));
                pressure.setSemaphore(cursor.getInt(5));
                list.add(pressure);
            } while (cursor.moveToNext());
        }

        db.close();

        return list;

    }


    private int pressureUpdate(String table, Pressure pressure) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        String date = DateUtils.dateToString(pressure.getDate(), DateUtils.DEFAULT_FORMAT);
        values.put(KEY_DATE, date);
        values.put(KEY_SYSTOLIC, pressure.getSystolic());
        values.put(KEY_DIASTOLIC, pressure.getDiastolic());
        values.put(KEY_PULSE, pressure.getPulse());

        int res = db.update(table, values, KEY_DATE + " = ?", new String[]{date});

        db.close();

        return res;

    }

    public int updatePressureAverage(Pressure pressure) {

        return pressureUpdate(TABLE_PRESSURESAVERAGE, pressure);
    }

    public int updateYoutubeVideo(YoutubeVideo youtubeVideo) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_URL, youtubeVideo.getVideoId());
        values.put(KEY_DESCRIPTION,youtubeVideo.getText());
        int res = db.update(TABLE_YOUTUBE, values, KEY_ID + " = ?", new String[]{String.valueOf(youtubeVideo.getID())});

        db.close();

        return res;
    }

    public void deletePressureAverage(Pressure pressure) {

        deletePressure(TABLE_PRESSURESAVERAGE, pressure);
    }

    public void deleteYoutubeLink(Pressure pressure) {

        deletePressure(TABLE_YOUTUBE, pressure);
    }

    private void deletePressure(String table, Pressure pressure) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table,
                KEY_ID + " = ?",
                new String[]{String.valueOf(pressure.getId())});

        db.close();
    }


}