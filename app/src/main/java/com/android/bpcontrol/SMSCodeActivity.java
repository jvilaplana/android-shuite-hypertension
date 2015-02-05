package com.android.bpcontrol;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.customViews.RobotoTextView;

/**
 * Created by Adrian Carrera on 04/02/2015.
 */
public class SMSCodeActivity extends BPcontrolMasterActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smscodelayout);

        configureActionBar();

        Button button = (Button) findViewById(R.id.accesToHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCorrectCode();

            }
        });
    }

   private void checkCorrectCode(){

        showLicense();
   }

   private void showLicense(){

       final Dialog dialog = new Dialog(this);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       LayoutInflater layoutInflater = getLayoutInflater();
       View view = layoutInflater.inflate(R.layout.dialoglicense,null);
       Button accept = (Button) view.findViewById(R.id.licenseAccept);
       Button cancel = (Button) view.findViewById(R.id.licenseCancel);
       accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

       accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
               startActivity(new Intent(SMSCodeActivity.this,HomeActivity.class));
           }
       });
       dialog.setContentView(view);
       dialog.show();

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

        ((RobotoTextView)view.findViewById(R.id.textviewbpcontrol)).setText(getResources()
                                                                .getString(R.string.sendcoderegisterheader));
    }

    public void goBack(){
        finish();
    }

    @Override
    public void onBackPressed(){
        goBack();
    }


}


