package com.android.bpcontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.customViews.RobotoTextView;

/**
 * Created by Adrian Carrera on 03/02/2015.
 */
public class SendTlfRegister extends BPcontrolMasterActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendtlfregisterayout);

        configureActionBar();

        Button button = (Button) findViewById(R.id.accesToSMScode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    private void configureActionBar(){

        View view = getActionBarView();
        ImageView image = (ImageView) view.findViewById(R.id.actionBarMenu);
        image.setImageResource(R.drawable.arrow_buttonselector);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goBack();
            }
        });

        ((RobotoTextView)view.findViewById(R.id.textviewbpcontrol)).setText(getResources().getString(R.string.sendtlfregisterheader));

    }


    public void goBack(){

        finish();
    }

    @Override
    public void onBackPressed(){
        goBack();
    }


}
