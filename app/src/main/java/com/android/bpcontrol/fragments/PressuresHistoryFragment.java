package com.android.bpcontrol.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.PressuresHistoryAdapter;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.databases.DataStore;
import com.android.bpcontrol.interfaces.PHistoryAdapterItem;
import com.android.bpcontrol.model.PHistoryCell;
import com.android.bpcontrol.model.PHistoryHeader;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.utils.DateUtils;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.bpcontrol.webservice.WSManager;
import com.android.volley.toolbox.StringRequest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian on 20/02/2015.
 */
public class PressuresHistoryFragment extends Fragment {

     private ListView listView;
     private BPcontrolDB db;
     private PressuresHistoryAdapter adapter;
     private SharedPreferences preference;
     private Handler dbmanger;
     private Handler dboutdatedmanager;
     private List<Pressure> dboutdated = new ArrayList<>();


    public static PressuresHistoryFragment getNewInstace(){

        PressuresHistoryFragment pressuresHistoryFragment = new PressuresHistoryFragment();
        return pressuresHistoryFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pressureshistorylayout,null);
        listView = (ListView)view.findViewById(R.id.resultados_list);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PHistoryHeader.MONTHS = getActivity().getResources().getStringArray(R.array.months);
        preference = getActivity().getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY,
                                                        Context.MODE_PRIVATE);
        db = new BPcontrolDB(getActivity());

        if (!db.isPressuresTableEmpty()){ //the first time

                new getPressuresHistory().execute();

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


        }else if (DataStore.getInstance().getPressures().size()> 0){ //today

            new getPressuresHistoryByDate().execute();
        }else{ //pressures pushed in other device

            new readDataBase().execute();

            dboutdatedmanager = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    Runnable saveInDB = new Runnable() {
                        @Override
                        public void run() {
                            if (dboutdated.size()>0) {
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

    private class getPressuresHistory extends AsyncTask<Void,Void,Void> {


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

                configureView(DataStore.getInstance().getPressures());

                dbmanger.sendEmptyMessage(0);

                SharedPreferences.Editor editor = preference.edit();
                editor.putString(SharedPreferenceConstants.LASTUPDATEHISTORY,
                        DateUtils.dateToString(new Date(),DateUtils.DEFAULT_FORMAT));
                editor.commit();

                ((HomeActivity) getActivity()).dissmissProgressDialog();

            }else{
                showDialog();
                ((HomeActivity) getActivity()).dissmissProgressDialog();
            }
        }
    }


    private class getPressuresHistoryByDate extends AsyncTask<Void,Void,Void>{

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
            configureView(DataStore.getInstance().getPressures());
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

            new getPressuresHistoryByDate().execute();
        }
    }

    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getResources().getString(R.string.nopressurehistory));
        builder.setPositiveButton(getActivity().getResources().getString(R.string.noconnectiondialogpositiveWS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

        dialog.show();
    }

    private void configureView(List<Pressure> list){
        adapter = new PressuresHistoryAdapter(getActivity());
        adapter.addPressures(list);
        listView.setAdapter(adapter);
    }
}
