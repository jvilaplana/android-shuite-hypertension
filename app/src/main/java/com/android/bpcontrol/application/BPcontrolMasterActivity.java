package com.android.bpcontrol.application;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.bpcontrol.customViews.BPcontrolProgressDialog;

/**
 * Created by Adrian on 22/01/2015.
 */
public class BPcontrolMasterActivity extends FragmentActivity {

    private BPcontrolProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle onInstanceState){
        super.onCreate(onInstanceState);
        getActionBar().hide();

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
}
