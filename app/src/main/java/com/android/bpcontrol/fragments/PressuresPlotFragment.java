package com.android.bpcontrol.fragments;

import android.content.Context;
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
import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.databases.DataStore;
import com.android.bpcontrol.model.ChartItem;
import com.android.bpcontrol.model.LineChartItem;
import com.android.bpcontrol.model.Pressure;
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
import java.util.List;

/**
 * Created by Adrian on 22/02/2015.
 */
public class PressuresPlotFragment extends Fragment {

    private BPcontrolDB db;
    private SharedPreferences preference;
    private Handler dbmanger;
    private Handler dboutdatedmanager;
    private List<Pressure> dboutdated = new ArrayList<>();
    private boolean updated = false;

    private ListView lv;

    public static PressuresPlotFragment getNewInstance(int position){

        PressuresPlotFragment  pressuresPlotFragment = new PressuresPlotFragment().setFragmentPosition(position);
        return pressuresPlotFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View view = inflater.inflate(R.layout.pressuresplotlayout, null);
        lv = (ListView) view.findViewById(R.id.listView1);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {


        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }
        @Override
        public int getItemViewType(int position) {

            return getItem(position).getItemType();
        }
        @Override
        public int getViewTypeCount() {
            return 1;
        }
    }

    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < 30; i++) {
            values.add(new Entry((int) (Math.random() * 65) + 40, i));
        }
        LineDataSet d1;
        switch (cnt){

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
        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }



    private ArrayList<String> getMonths() {
                Calendar calendar = Calendar.getInstance();
        String[] dates = new String[30];
        for (int i=0;i<30;i++){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            Date date = calendar.getTime();
            dates[i]=DateUtils.dateToString(date,DateUtils.DEFAULT_FORMAT);
        }
        return new ArrayList<String>(Arrays.asList(dates));
    }




    public PressuresPlotFragment setFragmentPosition(int position){

       // this.viewpagerposition = position;

        return this;
    }

    private void updatePressures(){


            db = new BPcontrolDB(getActivity());

            if (!db.isPressuresTableEmpty()) { //the first time

                new getPressuresPlot().execute();

                dbmanger = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Runnable saveInDB = new Runnable() {
                            @Override
                            public void run() {
                                db.addAllPressuresAverage(DataStore.getInstance().getPressures());
                            }
                        };
                        saveInDB.run();
                    }
                };


            } else if (DataStore.getInstance().getPressures().size() > 0) { //today

                new getPressuresPlot().execute();
            } else { //pressures pushed in other device

                new readDataBase().execute();

                dboutdatedmanager = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {

                        Runnable saveInDB = new Runnable() {
                            @Override
                            public void run() {
                                if (dboutdated.size() > 0) {
                                    db.addAllPressuresAverage(dboutdated);
                                }
                                dboutdated.clear();
                            }
                        };
                        saveInDB.run();

                    }
                };


        }
    }

    private class getPressuresPlot extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPreExecute() {

            ((HomeActivity) getActivity()).showProgressDialog();

        }

        @Override
        protected Void doInBackground(Void... params) {


            try {
                WSManager.getInstance().getUserPressures(getActivity(), null, new WSManager.GetUserPressures() {
                    @Override
                    public void onUserPressuresReceived(ArrayList<Pressure> pressures) {

                        DataStore.getInstance().setPressures(pressures);
                        publishProgress();
                    }
                });
            } catch (ParseException e) {
                LogBP.printStackTrace(e);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {


            if (DataStore.getInstance().getPressures().size()!=0) {

               // configureView(DataStore.getInstance().getPressures());

                dbmanger.sendEmptyMessage(0);

                SharedPreferences.Editor editor = preference.edit();
                editor.putString(SharedPreferenceConstants.LASTUPDATEHISTORY,
                        DateUtils.dateToString(new Date(), DateUtils.DEFAULT_FORMAT));
                editor.commit();

                ((HomeActivity) getActivity()).dissmissProgressDialog();

            }else{
               // showDialog();
                ((HomeActivity) getActivity()).dissmissProgressDialog();
            }
        }
    }


    private class getPressuresPlotByDate extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){

            ((HomeActivity)getActivity()).showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String last_update = preference.getString(SharedPreferenceConstants.LASTUPDATEHISTORY,"");

            if (!last_update.equals("")){

                try {
                    WSManager.getInstance().getUserPressures(getActivity(), last_update, new WSManager.GetUserPressures() {
                        @Override
                        public void onUserPressuresReceived(ArrayList<Pressure> pressures) {

                            if (DataStore.getInstance().getPressures().size()>0 && pressures.size()>0){

                                if (DataStore.getInstance().getPressures().get(DataStore.getInstance()
                                        .pressuresSize()-1).getStringDate().equals(pressures.get(0).getStringDate())){

                                    DataStore.getInstance().getPressures().remove(DataStore.getInstance()
                                            .pressuresSize()-1);

                                }


                            }else {
                                DataStore.getInstance().setPressures((ArrayList<Pressure>) dboutdated);

                                if (dboutdated.get(dboutdated.size() - 1).getStringDate().equals(pressures.get(0).getStringDate())) {
                                    pressures.remove(0);

                                }
                                dboutdated.clear();
                                dboutdated.addAll(pressures);
                                dboutdatedmanager.sendEmptyMessage(0);
                            }
                            DataStore.getInstance().setPressures(pressures);
                            publishProgress();
                        }
                    });
                } catch (ParseException e) {
                    LogBP.printStackTrace(e);
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {
          //  configureView(DataStore.getInstance().getPressures());
            ((HomeActivity)getActivity()).dissmissProgressDialog();
        }
    }

    private class readDataBase extends AsyncTask<Void,Void,List<Pressure>>{

        @Override
        protected void onPreExecute(){
            ((HomeActivity)getActivity()).showProgressDialog();
        }

        @Override
        protected List<Pressure> doInBackground(Void... params) {


            try {
                return db.getAllPressureAverage();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Pressure> list){

            if (list.size()>0){
                dboutdated.addAll((ArrayList)list);
            }

          //  new getPressuresHistoryByDate().execute();
        }

        private void graphicChart(){

            ArrayList<ChartItem> list = new ArrayList<ChartItem>();
            list.add(new LineChartItem(generateDataLine(1), getActivity()));
            list.add(new LineChartItem(generateDataLine(2), getActivity()));
            list.add(new LineChartItem(generateDataLine(3), getActivity()));


            ChartDataAdapter cda = new ChartDataAdapter(getActivity().getApplicationContext(), list);
            lv.setAdapter(cda);

        }





    }

}
