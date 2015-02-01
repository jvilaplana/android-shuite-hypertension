package com.android.bpcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.bpcontrol.application.BPcontrolMasterActivity;


public class SplashActivity extends BPcontrolMasterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActionBar().hide();

        Handler handler = new Handler();



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,InitActivity.class));
                finish();
            }
        },2000);
    }




}
