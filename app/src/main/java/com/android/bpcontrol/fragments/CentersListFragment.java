package com.android.bpcontrol.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
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

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.MapsActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.ListCentersAdapter;
import com.android.bpcontrol.model.Center;
import com.android.bpcontrol.test.DatabaseAndWSMock;
import com.android.bpcontrol.utils.LogBP;



import java.util.List;

/**
 * Created by Adrian on 02/02/2015.
 */
public class CentersListFragment extends Fragment
                                 {


    public static enum CentersView{

       CENTERS_LIST,
       CENTERS_DETAIL
    }

    private LocationManager manager;
    private ListView listView;
    private ListCentersAdapter adapter;
    private final String TAG = "LOCATION";
    private HomeActivity activity;
    private Handler handler;

    private boolean gpsregister = false;
    private boolean networkregister = false;

    public static String  MYLOCATION = "myloc";
    public static String CENTER = "center";




    private final int SETTINGS_ENABLED = 1;


    public static CentersListFragment getNewInstance(){

        CentersListFragment  centersListFragment = new CentersListFragment();
        return centersListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        manager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        activity = (HomeActivity) getActivity();

        locationConnection();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =inflater.inflate(R.layout.centerslistfragmentlayout, null);
        listView = (ListView) view.findViewById(R.id.listcenters);
        return view;

    }


    @Override
    public void onPause(){
        super.onPause();
        LogBP.writelog(TAG,"LOCATION DISABLE");
        manager.removeUpdates((HomeActivity)getActivity());
        handler = new Handler();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Center> centers = DatabaseAndWSMock.getFakeCenters();
        adapter = new ListCentersAdapter(getActivity(),centers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Center center = (Center)  parent.getItemAtPosition(position);

                final boolean gpsenabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                final boolean networkenabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                Location location = activity.getCurrentLocation();
                Location lastknowlocation = null;
                if (gpsenabled && location==null){
                    lastknowlocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }else if (location==null && networkenabled && lastknowlocation==null){
                    lastknowlocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (lastknowlocation!=null && location == null){
                    location = lastknowlocation;
                }

                Bundle bundle = new Bundle();

                if (location != null){

                    bundle.putParcelable(CENTER,center);
                    bundle.putParcelable(MYLOCATION,location);
                    LogBP.writelog(TAG,location.toString());
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else{
                    bundle.putParcelable(CENTER,center);
                    LogBP.writelog(TAG,"LOCATION NULL");
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SETTINGS_ENABLED){

            if (resultCode == getActivity().RESULT_OK){
                locationConnection();
            }
        }
    }

     private void locationConnection(){
         final boolean gpsenabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
         final boolean networkenabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

         if(networkenabled && gpsenabled){

             LocationProvider gpsprovider = manager.getProvider(LocationManager.GPS_PROVIDER);
             LocationProvider networkprovider = manager.getProvider(LocationManager.NETWORK_PROVIDER);

             manager.requestLocationUpdates(gpsprovider.getName(), 3000, 100,(HomeActivity) getActivity());
             manager.requestLocationUpdates(networkprovider.getName(), 3000, 100,(HomeActivity) getActivity());

             gpsregister = true;
             networkregister = true;

         }else if (!networkenabled && !gpsenabled){

             AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

             builder.setTitle(getResources().getString(R.string.locationalert));
             builder.setPositiveButton(getResources().getString(R.string.enabledialogloc),new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                     startActivityForResult(intent,SETTINGS_ENABLED);

                 }

             });

             builder.setNegativeButton(getResources().getString(R.string.canceldialogloc),new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             });

             AlertDialog dialog = builder.create();
             TextView text = (TextView) dialog.findViewById(android.R.id.message);
             text.setGravity(Gravity.CENTER);
             dialog.show();


         }else if ((networkenabled && !gpsenabled) || (!networkenabled && gpsenabled)){

             String activeprovider = manager.getBestProvider(new Criteria(),true);
             manager.requestLocationUpdates(activeprovider, 3000, 100,(HomeActivity) getActivity());
             if (activeprovider==LocationManager.GPS_PROVIDER){
                 gpsregister = true;
             }else{
                 networkregister = true;
             }

         }
     }


}
