package com.android.bpcontrol.application;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customViews.BPcontrolProgressDialog;

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
        getActionBar().setCustomView(customView);
        getActionBarView().setFocusable(false);
    }

    protected View getActionBarView(){

        return getActionBar().getCustomView();
    }
}
