package com.android.bpcontrol.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.InitialActivity;
import com.android.bpcontrol.MapsActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.ListCentersAdapter;
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.model.Center;
import com.android.bpcontrol.model.User;
import com.android.bpcontrol.test.DatabaseAndWSMock;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.bpcontrol.webservice.WSManager;
import com.google.android.gms.maps.model.LatLng;


import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Adrian on 02/02/2015.
 */
public class CentersListFragment extends Fragment{


    public static enum CentersView{

       CENTERS_LIST,
       CENTERS_DETAIL
    }

    private LocationManager manager;
    private ListView listView;
    private ListCentersAdapter adapter;
    private Handler handler;

    public static String CENTER = "center";
    public static String MYLOCATION = "mylocation";


    public static CentersListFragment getNewInstance(){

        CentersListFragment  centersListFragment = new CentersListFragment();
        return centersListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =inflater.inflate(R.layout.centerslistfragmentlayout, null);
        listView = (ListView) view.findViewById(R.id.listcenters);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... params ) {



                    WSManager.getInstance().getAssociateHealthCenters(getActivity(),new WSManager.GetHealthCenters() {
                        @Override
                        public void onCentersReceived(List<Center> centers){
                            if (getActivity() instanceof HomeActivity){
                                adapter = new ListCentersAdapter(getActivity(),centers, ListCentersAdapter.ListCenterPlace.HOME_ACTIVITY);

                            }else {
                                adapter = new ListCentersAdapter(getActivity(),centers, ListCentersAdapter.ListCenterPlace.INITIAL_ACTIVITY);
                            }

                            listView.setAdapter(adapter);
                        }
                    });


                return null;
            }
        }.execute();


        //List<Center> centers = DatabaseAndWSMock.getFakeCenters();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Center center = (Center) parent.getItemAtPosition(position);
        Bundle bundle = new Bundle();

        bundle.putParcelable(CENTER,center);

        if (getActivity() instanceof HomeActivity){
            if (!(((HomeActivity)getActivity()).getCurrentLocation()==null)){
                bundle.putParcelable(MYLOCATION,((HomeActivity)getActivity()).getCurrentLocation());
            }

        }else {
            if (!(((InitialActivity)getActivity()).getCurrentLocation()==null)){
                bundle.putParcelable(MYLOCATION,((InitialActivity)getActivity()).getCurrentLocation());
            }
        }

        Intent intent = new Intent(getActivity(),MapsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


            }
        });
    }




}



