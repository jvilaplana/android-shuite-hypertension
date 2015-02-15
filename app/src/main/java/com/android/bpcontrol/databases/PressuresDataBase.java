package com.android.bpcontrol.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.utils.LogBP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 14/2/15.
 */
public class PressuresDataBase extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PressuresDB";

    private static final String TABLE_PRESSURESMORNING = "PressuresMorning";
    private static final String TABLE_PRESSURESAFTERNOON = "PressuresAfternoon";

    private static final String KEY_ID = "id";
    private static final String KEY_SYSTOLIC = "systolic";
    private static final String KEY_DIASTOLIC = "diastolic";
    private static final String KEY_PULSE = "pulse";

    public PressuresDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        LogBP.writelog("DATABASE","Database created");

        String CREATE_PRESSURESMORNING_TABLE = "CREATE TABLE PressuresMorning ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "systolic TEXT, "+
                "diastolic TEXT, "+
                "pulse TEXT )";

        String CREATE_PRESSURESAFTERNOON_TABLE = "CREATE TABLE PressuresAfternoon ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "systolic TEXT, "+
                "diastolic TEXT, "+
                "pulse TEXT )";


        db.execSQL(CREATE_PRESSURESMORNING_TABLE);
        db.execSQL(CREATE_PRESSURESAFTERNOON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogBP.writelog("DATABASE","Database upgrade");
        db.execSQL("DROP TABLE IF EXISTS books");
        this.onCreate(db);
    }


    public void addMorningPreasure(Pressure pressure){

        addPressure(TABLE_PRESSURESMORNING,pressure);
    }

    public void addAfternoonPreasure(Pressure pressure){

        addPressure(TABLE_PRESSURESAFTERNOON,pressure);
    }


    private void addPressure(String table,Pressure pressure){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SYSTOLIC, pressure.getSystolic());
        values.put(KEY_DIASTOLIC, pressure.getDiastolic());
        values.put(KEY_PULSE, pressure.getPulse());

        db.insert(table, null, values);

        db.close();

    }

    public List<Pressure> getAllMorningPressures(){

        return getAllPressure(TABLE_PRESSURESMORNING);
    }

    public List<Pressure> getAllAfternoonPressures(){

        return getAllPressure(TABLE_PRESSURESAFTERNOON);
    }

    private List<Pressure> getAllPressure(String table){

        List<Pressure> listpressures = new ArrayList<>();

        String query = "SELECT  * FROM " + table;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Pressure pressure = null;
        if (cursor.moveToFirst()) {
            do {
                pressure = new Pressure();
                pressure.setId(Integer.parseInt(cursor.getString(0)));
                pressure.setSystolic(cursor.getString(1));
                pressure.setDiastolic(cursor.getString(2));
                pressure.setPulse(cursor.getString(3));
                listpressures.add(pressure);
            } while (cursor.moveToNext());
        }

        LogBP.writelog("DATABASE","getAllAfternoonPressures()");

        return listpressures;

    }

    public int updatePressureMorning(Pressure pressure) {

        return pressureUpdate(TABLE_PRESSURESMORNING,pressure);
    }


    public int updatePressureAfternoon(Pressure pressure) {

        return pressureUpdate(TABLE_PRESSURESAFTERNOON,pressure);

    }

    private int pressureUpdate(String table,Pressure pressure){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SYSTOLIC, pressure.getSystolic() );
        values.put(KEY_DIASTOLIC, pressure.getDiastolic());
        values.put(KEY_PULSE, pressure.getPulse());

        int res = db.update(table, values, KEY_ID+" = ?",new String[] { String.valueOf(pressure.getId())});

        db.close();

        return res;

    }

    public void deletePressureMorning(Pressure pressure) {

        deletePressure(TABLE_PRESSURESMORNING,pressure);
    }


    public void deletePressureAfternoon(Pressure pressure) {

        deletePressure(TABLE_PRESSURESAFTERNOON,pressure);
    }

    private void deletePressure(String table, Pressure pressure){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(table,
                 KEY_ID+" = ?",
                new String[] { String.valueOf(pressure.getId()) });

        db.close();
    }


}
