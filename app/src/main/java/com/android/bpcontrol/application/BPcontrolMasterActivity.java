package com.android.bpcontrol.application;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.BPcontrolProgressDialog;

/**
 * Created by Adrian Carrera on 22/01/2015.
 */
public class BPcontrolMasterActivity extends FragmentActivity {

    private BPcontrolProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle onInstanceState){
        super.onCreate(onInstanceState);
        configActionBar();
    }

    public void showProgressDialog(){

        showProgressDialog("");
    }

    public void showProgressDialog(String text){
            if(progressDialog != null){

                progressDialog.dismiss();
                progressDialog = null;
            }
        progressDialog = new BPcontrolProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(text);
        progressDialog.show();

    }

    public void dissmissProgressDialog(){
        if(progressDialog != null)
            progressDialog.dismiss();
        progressDialog = null;
    }

    private void configActionBar(){

        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.graybp)));
        LayoutInflater mInflater = LayoutInflater.from(this);
        View customView = mInflater.inflate(R.layout.actionbar_layout,null);
        customView.findViewById(R.id.secondActionBarButton).setVisibility(View.INVISIBLE); //margin balance textview
        getActionBar().setCustomView(customView);
        getActionBarView().setFocusable(false);
    }

    protected View getActionBarView(){

        return getActionBar().getCustomView();
    }

    @Override
    public BPcontrolApplication getApplicationContext(){

        return (BPcontrolApplication) super.getApplicationContext();
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
