package com.android.bpcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.fragments.InitialFragment;
import com.android.bpcontrol.utils.SharedPreferenceConstants;


public class SplashActivity extends BPcontrolMasterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActionBar().hide();

        final SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, MODE_PRIVATE);
        final boolean isRegistered = sharedPreferences.getBoolean(SharedPreferenceConstants.ISREGISTERED_KEY,false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRegistered) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, InitialActivity.class));
                }
                finish();
            }
        },2000);
    }




}
