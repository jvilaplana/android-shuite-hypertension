package com.android.bpcontrol.fragments;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.PressuresHistoryAdapter;
import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.databases.DataStore;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.utils.DateUtils;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.bpcontrol.webservice.WSManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian on 22/02/2015.
 */
public class PressuresPlotFragment extends Fragment {

    private BPcontrolDB db;
    private PressuresHistoryAdapter adapter;
    private SharedPreferences preference;
    private Handler dbmanger;
    private Handler dboutdatedmanager;
    private List<Pressure> dboutdated = new ArrayList<>();
    private boolean updated = false;



    private int viewpagerposition=0;


    public static PressuresPlotFragment getNewInstance(int position){

        PressuresPlotFragment  pressuresPlotFragment = new PressuresPlotFragment().setFragmentPosition(position);
        return pressuresPlotFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pressuresplotlayout, null);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

      //updatePressures();

    }

    public PressuresPlotFragment setFragmentPosition(int position){

        this.viewpagerposition = position;

        return this;
    }





























    private void updatePressures(){

        if (viewpagerposition == 0 && !updated) {
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
    }







}
