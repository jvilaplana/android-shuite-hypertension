package com.android.bpcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.fragments.InitialFragment;
import com.android.bpcontrol.model.User;
import com.android.bpcontrol.utils.SharedPreferenceConstants;


public class SplashActivity extends BPcontrolMasterActivity {

    private String TEST_UUID="a5683026-0f3b-4ea5-a129-0aec2c36c1eb";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActionBar().hide();

        final SharedPreferences sharedPreferences = getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, MODE_PRIVATE);
        sharedPreferences.edit().putString(SharedPreferenceConstants.USERUUID,TEST_UUID).commit();
        final boolean isRegistered = sharedPreferences.getBoolean(SharedPreferenceConstants.ISREGISTERED_KEY,false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (false) {
                    User.getInstance().setUUID(TEST_UUID);

                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, InitialActivity.class));
                }
                finish();
            }
        },2000);
    }




}
