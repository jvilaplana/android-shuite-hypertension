package com.android.bpcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.android.bpcontrol.application.BPcontrolMasterActivity;

/**
 * Created by Adrian on 22/1/15.
 */
public class HomeActivity extends BPcontrolMasterActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        showProgressDialog();
        Handler handler = new Handler();
        showProgressDialog();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                dissmissProgressDialog();
            }
        },4000);

    }

}
