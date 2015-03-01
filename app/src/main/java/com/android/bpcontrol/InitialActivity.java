package com.android.bpcontrol;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.fragments.CentersListFragment;
import com.android.bpcontrol.fragments.InformationFragment;
import com.android.bpcontrol.fragments.InitialFragment;
import com.android.bpcontrol.interfaces.TabListener;

/**
 * Created by Adrian Carrera on 31/01/2015.
 */
public class InitialActivity extends BPcontrolMasterActivity
                             implements TabListener {

    public static enum InitialFragments{
        HOME,
        MAPS,
        INFO
    }

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
                loadFragment(centersListFragment,true);
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


}
