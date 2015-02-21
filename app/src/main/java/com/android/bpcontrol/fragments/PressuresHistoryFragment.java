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

        preference = getActivity().getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY,
                Context.MODE_PRIVATE);
        db = new BPcontrolDB(getActivity());

        if (db.isPressuresTableEmpty()){

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


        }else if (DataStore.getInstance().getPressures().size()> 0){
            //only check date
        }else{
            //getdatabaseitems
            //getlastupdate

        }

    }

    private class getPressuresHistory extends AsyncTask<Void,Void,Void> {


        @Override
        protected void onPreExecute() {

            ((HomeActivity) getActivity()).showProgressDialog();

        }

        @Override
        protected Void doInBackground(Void... params) {


            WSManager.getInstance().getUserPressures(getActivity(), null, new WSManager.GetUserPressures() {
                @Override
                public void onUserPressuresReceived(ArrayList<Pressure> pressures) {

                    DataStore.getInstance().setPressures(pressures);
                    publishProgress();
                }
            });

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {


            List<PHistoryAdapterItem> adapteritems = convertWSresultToAdapterItems();
            if (adapteritems.size() != 0) {
                adapter = new PressuresHistoryAdapter(getActivity(), adapteritems);


                listView.setAdapter(adapter);

                dbmanger.sendEmptyMessage(0);

                ((HomeActivity) getActivity()).dissmissProgressDialog();
//
//                SharedPreferences.Editor editor = preference.edit();
//                editor.putString(SharedPreferenceConstants.LASTUPDATEHISTORY,
//                        DateUtils.dateToString(new Date(),DateUtils.DEFAULT_FORMAT));
//                editor.commit();
//
            }else{
                showDialog();
                ((HomeActivity) getActivity()).dissmissProgressDialog();
            }
        }
    }


    private class getPressuresHistoryByDate extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {

            WSManager.getInstance().getUserPressures(getActivity(), null, new WSManager.GetUserPressures() {
                @Override
                public void onUserPressuresReceived(ArrayList<Pressure> pressures) {

                    DataStore.getInstance().setPressures(pressures);
                    publishProgress();
                }
            });

            return null;
        }
    }

    private List<PHistoryAdapterItem> convertWSresultToAdapterItems(){

        List<PHistoryAdapterItem> itemsadpter = new ArrayList<>();
        List<Pressure> pressures = DataStore.getInstance().getPressures();
        Pressure tmp;
        for (int i = pressures.size()-1; i>-1;i--){
            tmp = pressures.get(i);
            itemsadpter.add(new PHistoryHeader(DateUtils.dateToString(tmp.getDate(),DateUtils.DEFAULT_FORMAT)));
            itemsadpter.add(new PHistoryCell(tmp));
        }

        return itemsadpter;
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
}
