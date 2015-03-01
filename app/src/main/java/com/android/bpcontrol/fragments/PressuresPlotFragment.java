package com.android.bpcontrol.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.ChartDataAdapter;
import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.databases.DataStore;
import com.android.bpcontrol.model.ChartItem;
import com.android.bpcontrol.model.LineChartItem;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.utils.DatabasePlotMock;
import com.android.bpcontrol.utils.DateUtils;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.bpcontrol.webservice.WSManager;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Adrian on 22/02/2015.
 */
public class PressuresPlotFragment extends Fragment {

    public static enum PressuresPlot{

        ONE_MONTH,
        THREE_MONTH,
        SIX_MONTH,
        CUSTOM

    }

    private BPcontrolDB db;
    private SharedPreferences preference;
    private Handler dbmanger;
    private Handler dboutdatedmanager;
    private List<Pressure> dboutdated = new ArrayList<>();
    private boolean updated = false;

    private int custom_days = 0;

    private ListView lv;

    public static PressuresPlotFragment getNewInstance(){

        PressuresPlotFragment  pressuresPlotFragment = new PressuresPlotFragment();
        return pressuresPlotFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.pressuresplotlayout, null);
        lv = (ListView) view.findViewById(R.id.listView1);
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        db = new BPcontrolDB(getActivity());
        showDialog();

    }

    private LineData generateDataLine(int cnt,Map<String,Integer> pressurevalues,PressuresPlot type) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<String> dates;

        switch (type) {
            case ONE_MONTH:
                dates = DateUtils.getDates(30);
                break;
            case THREE_MONTH:
                dates = DateUtils.getDates(90);
                break;
            case SIX_MONTH:
                dates = DateUtils.getDates(180);
                break;
            case CUSTOM:
                dates = DateUtils.getDates(custom_days);
                break;
            default:
                dates = new ArrayList<>();
                break;
        }

        String tmpdate;
        for (int i = 0; i < dates.size(); i++) {
            tmpdate = dates.get(i);
            if (pressurevalues.containsKey(tmpdate)) {
                values.add(new Entry(pressurevalues.get(tmpdate), i));
            }
        }
        ArrayList<LineDataSet> sets = prepareChartStyle(cnt, values);
        LineData cd = new LineData(dates, sets);
        return cd;
    }

    private void graphicChart(List<Pressure> pressures,PressuresPlot type){

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        list.add(new LineChartItem(generateDataLine(1, getSystolicPressures(pressures), type), getActivity(),0));
        list.add(new LineChartItem(generateDataLine(2, getDiastolicPressures(pressures), type), getActivity(),1));
        list.add(new LineChartItem(generateDataLine(3, getPulse(pressures), type), getActivity(),2));


        ChartDataAdapter cda = new ChartDataAdapter(getActivity().getApplicationContext(), list);
        lv.setAdapter(cda);

    }

    private Map<String,Integer> getSystolicPressures(List<Pressure> list){

        Map<String,Integer> values = new HashMap<>();
        if (list.size()>0) {
            for (Pressure pressure: list) {
                values.put(pressure.getStringDate(),Integer.parseInt(pressure.getSystolic()));
            }
        }

        return values;

    }

    private Map<String,Integer> getDiastolicPressures(List<Pressure> list){

        Map<String,Integer> values = new HashMap<>();
        if (list.size()>0) {
            for (Pressure pressure: list) {
                values.put(pressure.getStringDate(),Integer.parseInt(pressure.getDiastolic()));
            }
        }

        return values;

    }

    private Map<String,Integer>getPulse(List<Pressure> list){

        Map<String,Integer> values = new HashMap<>();
        if (list.size()>0) {
            for (Pressure pressure: list) {
                if (pressure.getPulse().equals("null")){
                    pressure.setPulse("70");
                }
                values.put(pressure.getStringDate(),Integer.parseInt(pressure.getPulse()));
            }
        }
        return values;
    }


    private void showDialog(){


        final CharSequence[] items = {getResources().getString(R.string.plotmenucase1),
                getResources().getString(R.string.plotmenucase2), getResources().getString(R.string.plotmenucase3),
                getResources().getString(R.string.plotmenucase4),getResources().getString(R.string.plotcancel)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.plotcasestitle));
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String[] dates;
                List<Pressure> pressures=null;

                switch(item){

                    case 0:
                        pressures = DatabasePlotMock.getOneMonthPressures();
                        graphicChart(pressures,PressuresPlot.ONE_MONTH);
//                        dates = initAndEndDate(30);
//                        try {
//                            pressures = db.getPressuresAverageBetweenTwoDates(dates[0],dates[1]);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        if (pressures!=null){
//                            graphicChart(pressures);
//                        }
                        break;
                    case 1:
                        pressures = DatabasePlotMock.getThreeMonthPressures();
                        graphicChart(pressures,PressuresPlot.THREE_MONTH);
//                        dates = initAndEndDate(90);
//                        try {
//                            pressures = db.getPressuresAverageBetweenTwoDates(dates[0],dates[1]);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        if (pressures!=null){
//                            graphicChart(pressures);
//                        }
                        //show three month graphic
                        break;
                    case 2:
                        pressures = DatabasePlotMock.getThreeMonthPressures();
                        graphicChart(pressures,PressuresPlot.SIX_MONTH);
//                        dates = initAndEndDate(180);
//                        try {
//                            pressures = db.getPressuresAverageBetweenTwoDates(dates[0],dates[1]);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        if (pressures!=null){
//                            graphicChart(pressures);
//                        }
                        //show siz month graphic
                        break;
                    case 3:
                        //show custom graphic
                        break;
                    case 4:
                        //back
                        break;

                }
            }
        });
        builder.show();

    }

    private ArrayList<LineDataSet> prepareChartStyle(int position,ArrayList<Entry> values){

        LineDataSet d1;
        switch (position){

            case 1:
                d1 = new LineDataSet(values,getResources().getString(R.string.graphdescriptionsyctolic));
                d1.setColor(Color.RED);
                d1.setCircleColor(Color.RED);

                break;
            case 2:
                d1 = new LineDataSet(values,getResources().getString(R.string.graphdescriptiondiastolic));
                d1.setColor(Color.BLUE);
                d1.setCircleColor(Color.BLUE);

                break;
            case 3:
                d1 = new LineDataSet(values,getResources().getString(R.string.graphdescriptionpulse));
                d1.setColor(Color.GREEN);
                d1.setCircleColor(Color.GREEN);

                break;
            default:
                d1 = new LineDataSet(values,"");


                break;
        }

        d1.setHighLightColor(Color.rgb(0, 0, 0));
        d1.setLineWidth(10f);
        d1.setCircleSize(15f);

        d1.setDrawValues(true);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);


        return sets;
    }

    private String[] initAndEndDate(int days){

        String[] dates = new String[2];
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        dates[1] = DateUtils.dateToString(d1,DateUtils.DB_FORMAT);
        calendar.add(Calendar.DAY_OF_MONTH,-days);
        Date d2 = calendar.getTime();
        dates[0] = DateUtils.dateToString(d2,DateUtils.DB_FORMAT);

        return dates;
    }

    @Override
    public void onDestroyView(){
        lv.clearChoices();
        super.onDestroyView();

    }

    @Override
    public void onDestroy(){
        lv.clearChoices();
        super.onDestroy();


    }


}


//
//
//    private void updatePressures(){
//
//
//        db = new BPcontrolDB(getActivity());
//
//        if (!db.isPressuresTableEmpty()) { //the first time
//
//            new getPressuresPlot().execute();
//
//            dbmanger = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    Runnable saveInDB = new Runnable() {
//                        @Override
//                        public void run() {
//                            db.addAllPressuresAverage(DataStore.getInstance().getPressures());
//                        }
//                    };
//                    saveInDB.run();
//                }
//            };
//
//
//        } else if (DataStore.getInstance().getPressures().size() > 0) { //today
//
//            new getPressuresPlot().execute();
//        } else { //pressures pushed in other device
//
//            new readDataBase().execute();
//
//            dboutdatedmanager = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//
//                    Runnable saveInDB = new Runnable() {
//                        @Override
//                        public void run() {
//                            if (dboutdated.size() > 0) {
//                                db.addAllPressuresAverage(dboutdated);
//                            }
//                            dboutdated.clear();
//                        }
//                    };
//                    saveInDB.run();
//
//                }
//            };
//
//
//        }
//    }
//
//private class getPressuresPlot extends AsyncTask<Void,Void,Void> {
//
//
//    @Override
//    protected void onPreExecute() {
//
//        ((HomeActivity) getActivity()).showProgressDialog();
//
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//
//
//        try {
//            WSManager.getInstance().getUserPressures(getActivity(), null, new WSManager.GetUserPressures() {
//                @Override
//                public void onUserPressuresReceived(ArrayList<Pressure> pressures) {
//
//                    DataStore.getInstance().setPressures(pressures);
//                    publishProgress();
//                }
//            });
//        } catch (ParseException e) {
//            LogBP.printStackTrace(e);
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onProgressUpdate(Void... params) {
//
//
//        if (DataStore.getInstance().getPressures().size()!=0) {
//
//            // configureView(DataStore.getInstance().getPressures());
//
//            dbmanger.sendEmptyMessage(0);
//
//            SharedPreferences.Editor editor = preference.edit();
//            editor.putString(SharedPreferenceConstants.LASTUPDATEHISTORY,
//                    DateUtils.dateToString(new Date(), DateUtils.DEFAULT_FORMAT));
//            editor.commit();
//
//            ((HomeActivity) getActivity()).dissmissProgressDialog();
//
//        }else{
//            // showDialog();
//            ((HomeActivity) getActivity()).dissmissProgressDialog();
//        }
//    }
//}
//
//
//private class getPressuresPlotByDate extends AsyncTask<Void,Void,Void>{
//
//    @Override
//    protected void onPreExecute(){
//
//        ((HomeActivity)getActivity()).showProgressDialog();
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//
//        String last_update = preference.getString(SharedPreferenceConstants.LASTUPDATEHISTORY,"");
//
//        if (!last_update.equals("")){
//
//            try {
//                WSManager.getInstance().getUserPressures(getActivity(), last_update, new WSManager.GetUserPressures() {
//                    @Override
//                    public void onUserPressuresReceived(ArrayList<Pressure> pressures) {
//
//                        if (DataStore.getInstance().getPressures().size()>0 && pressures.size()>0){
//
//                            if (DataStore.getInstance().getPressures().get(DataStore.getInstance()
//                                    .pressuresSize()-1).getStringDate().equals(pressures.get(0).getStringDate())){
//
//                                DataStore.getInstance().getPressures().remove(DataStore.getInstance()
//                                        .pressuresSize()-1);
//
//                            }
//
//
//                        }else {
//                            DataStore.getInstance().setPressures((ArrayList<Pressure>) dboutdated);
//
//                            if (dboutdated.get(dboutdated.size() - 1).getStringDate().equals(pressures.get(0).getStringDate())) {
//                                pressures.remove(0);
//
//                            }
//                            dboutdated.clear();
//                            dboutdated.addAll(pressures);
//                            dboutdatedmanager.sendEmptyMessage(0);
//                        }
//                        DataStore.getInstance().setPressures(pressures);
//                        publishProgress();
//                    }
//                });
//            } catch (ParseException e) {
//                LogBP.printStackTrace(e);
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onProgressUpdate(Void... params) {
//        //  configureView(DataStore.getInstance().getPressures());
//        ((HomeActivity)getActivity()).dissmissProgressDialog();
//    }
//}
//
//private class readDataBase extends AsyncTask<Void,Void,List<Pressure>>{
//
//    @Override
//    protected void onPreExecute(){
//        ((HomeActivity)getActivity()).showProgressDialog();
//    }
//
//    @Override
//    protected List<Pressure> doInBackground(Void... params) {
//
//
//        try {
//            return db.getAllPressureAverage();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList<>();
//    }
//
//    @Override
//    protected void onPostExecute(List<Pressure> list){
//
//        if (list.size()>0){
//            dboutdated.addAll((ArrayList)list);
//        }
//
//        //  new getPressuresHistoryByDate().execute();
//    }
//}