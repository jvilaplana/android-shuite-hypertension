package com.android.bpcontrol;


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
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.fragments.CentersListFragment;
import com.android.bpcontrol.fragments.HomeFragment;
import com.android.bpcontrol.fragments.InformationFragment;
import com.android.bpcontrol.fragments.InitialFragment;
import com.android.bpcontrol.interfaces.TabListener;

/**
 * Created by Adrian Carrera on 31/01/2015.
 */
public class InitialActivity extends BPcontrolMasterActivity
                             implements TabListener,LocationListener {


    public static enum InitialFragments{
        HOME,
        MAPS,
        INFO
    }

    private final int SETTINGS_ENABLED = 1;

    private Location currentLocation;
    private LocationManager manager;

    private boolean gpsregister = false;
    private boolean networkregister = false;

    @Override
    public void onCreate(Bundle onInstanceState){
        super.onCreate(onInstanceState);

        setContentView(R.layout.initactivitylayout);
        configureInitialActionBar();
        ImageView tab1 = (ImageView) findViewById(R.id.tab1);
        tab1.setBackgroundColor(getResources().getColor(R.color.menuseparator));
        selectFragment(InitialFragments.HOME);
    }

    @Override
    public void onResume(){
        super.onResume();

        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationConnection();

    }


    public Location getCurrentLocation(){
        return currentLocation;
    }

    private void locationConnection() {
        final boolean gpsenabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        final boolean networkenabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (networkenabled && gpsenabled) {

            LocationProvider gpsprovider = manager.getProvider(LocationManager.GPS_PROVIDER);
            LocationProvider networkprovider = manager.getProvider(LocationManager.NETWORK_PROVIDER);

            manager.requestLocationUpdates(gpsprovider.getName(), 3000, 100, this);
            manager.requestLocationUpdates(networkprovider.getName(), 3000, 100, this);

            gpsregister = true;
            networkregister = true;

        } else if (!networkenabled && !gpsenabled) {

            createGPSDisableDialog();

        } else if ((networkenabled && !gpsenabled) || (!networkenabled && gpsenabled)) {

            String activeprovider = manager.getBestProvider(new Criteria(), true);
            manager.requestLocationUpdates(activeprovider, 3000, 100, this);
            if (activeprovider == LocationManager.GPS_PROVIDER) {
                gpsregister = true;
            } else {
                networkregister = true;
            }

        }
    }

    private boolean checkIfLocationFound(){

//        final boolean gpsenabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        final boolean networkenabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location lastknowlocation = null;
        if (currentLocation==null){
            lastknowlocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (currentLocation==null && lastknowlocation==null){
            lastknowlocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (lastknowlocation!=null && currentLocation == null){
            currentLocation = lastknowlocation;
            //manager.removeUpdates(this);
            return true;

        }else if (lastknowlocation==null && currentLocation==null){
            return false;
        }
        //manager.removeUpdates(this);
        return true;
    }

    private void createGPSDisableDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.locationalert));
        builder.setMessage(getResources().getString(R.string.activategps));
        builder.setPositiveButton(getResources().getString(R.string.enabledialogloc), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, SETTINGS_ENABLED);

            }

        });

        builder.setNegativeButton(getResources().getString(R.string.canceldialogloc), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onClickTabListener(View v) {

        int numtab = Integer.parseInt((String)v.getTag());

        ImageView tab1 = (ImageView) findViewById(R.id.tab1);
        ImageView tab2 = (ImageView) findViewById(R.id.tab2);
        ImageView tab3 = (ImageView) findViewById(R.id.tab3);

        if (numtab == 1){
            tab1.setBackgroundColor(getResources().getColor(R.color.menuseparator));
            tab2.setBackgroundColor(getResources().getColor(R.color.graybp));
            tab3.setBackgroundColor(getResources().getColor(R.color.graybp));

            selectFragment(InitialFragments.HOME);

        }else if(numtab == 2){

            tab1.setBackgroundColor(getResources().getColor(R.color.graybp));
            tab2.setBackgroundColor(getResources().getColor(R.color.menuseparator));
            tab3.setBackgroundColor(getResources().getColor(R.color.graybp));

            selectFragment(InitialFragments.MAPS);
        }else{

            tab1.setBackgroundColor(getResources().getColor(R.color.graybp));
            tab2.setBackgroundColor(getResources().getColor(R.color.graybp));
            tab3.setBackgroundColor(getResources().getColor(R.color.menuseparator));

            selectFragment(InitialFragments.INFO);
        }


    }

    private void configureInitialActionBar(){

        View view = getActionBarView();
        view.findViewById(R.id.textviewbpcontrol).setVisibility(View.GONE);
        view.findViewById(R.id.secondActionBarButton).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.actionBarMenu).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.imageinit).setVisibility(View.VISIBLE);

    }

    private void selectFragment(InitialFragments type){


        switch (type){

            case HOME:
                InitialFragment initialFragment = InitialFragment.getNewInstance();
                loadFragment(initialFragment,true);
                break;

            case MAPS:
                CentersListFragment centersListFragment = CentersListFragment.getNewInstance();
                boolean location=checkIfLocationFound();
                if (location) {
                    loadFragment(centersListFragment, true);
                }else {
                    createGPSDisableDialog();

                }
                break;

            case INFO:
                InformationFragment informationFragment = InformationFragment.getNewInstance();
                loadFragment(informationFragment,true);
                break;
            default:
                break;

        }


    }

    private void loadFragment(Fragment fragment, boolean animation){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int id = R.id.initalfragments;

        if(animation) {
            transaction.setCustomAnimations(R.anim.slide_in_up,
                    R.anim.fade_out);
        }

        if (manager.findFragmentById(id) == null) {
            transaction.add(id, fragment);
            transaction.commit();
        } else {
            transaction.replace(id, fragment);
            transaction.commit();
        }

    }


    @Override
    public void onLocationChanged(Location location) {
        this.currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == SETTINGS_ENABLED){

            if (resultCode == RESULT_OK){
                locationConnection();
            }
        }

    }


}
