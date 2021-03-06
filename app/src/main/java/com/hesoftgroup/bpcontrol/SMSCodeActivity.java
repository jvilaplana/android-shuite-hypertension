package com.hesoftgroup.bpcontrol;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;


import com.hesoftgroup.bpcontrol.application.BPcontrolMasterActivity;
import com.hesoftgroup.bpcontrol.customviews.BPEditText;
import com.hesoftgroup.bpcontrol.customviews.RobotoTextView;
import com.hesoftgroup.bpcontrol.model.User;
import com.hesoftgroup.bpcontrol.utils.SharedPreferenceConstants;
import com.hesoftgroup.bpcontrol.webservice.WSManager;


/**
 * Created by Adrian Carrera on 04/02/2015.
 */

public class SMSCodeActivity extends BPcontrolMasterActivity{

    private String phoneNumber="";
    private String phonePrefix="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smscodelayout);

        configureActionBar();

        if(getIntent() != null){

            phoneNumber = getIntent().getStringExtra(SendTlfRegisterActivity.INTENTKEY_TLFNUMBER);
            phonePrefix = getIntent().getStringExtra(SendTlfRegisterActivity.INTENTKEY_TLFPREFIX);

        }

        Button button = (Button) findViewById(R.id.accesToHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BPEditText editText = (BPEditText) SMSCodeActivity.this.findViewById(R.id.codeedittext);

                if (editText.length()==4) {
                    WSManager.getInstance().sendSMScode(SMSCodeActivity.this, phonePrefix, phoneNumber,editText.getText().toString(),new WSManager.CorrectSMSCode() {
                        @Override
                        public void checkSMScode() {

                            checkEnteredCode();
                        }
                    });


                }else{

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SMSCodeActivity.this);
                    alertDialog.setMessage(getResources().getString(R.string.codeLengthError));
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton(getResources().getString(R.string.acceptError), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }


            }
        });
    }

   private boolean isCorrectCode(){

    if (User.getInstance().getUUID().equals("")){

        return false;
    }else{

        return true;
    }

   }

    private void showErrorDialog(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getResources().getString(R.string.codeError));
        dialog.setCancelable(false);
        dialog.setPositiveButton(getResources().getString(R.string.acceptError), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void checkEnteredCode(){

        if(isCorrectCode()){

            showLicense();

        }else{

            showErrorDialog();
        }

    }

   private void showLicense(){

       final Dialog dialog = new Dialog(this);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       LayoutInflater layoutInflater = getLayoutInflater();
       View view = layoutInflater.inflate(R.layout.dialoglicense,null);
       Button accept = (Button) view.findViewById(R.id.licenseAccept);
       Button cancel = (Button) view.findViewById(R.id.licenseCancel);
       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

       accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();

               final SharedPreferences sharedPreferences = SMSCodeActivity.this
                       .getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putBoolean(SharedPreferenceConstants.ISREGISTERED_KEY,true);
               editor.commit();
               editor.putString(SharedPreferenceConstants.USERUUID,User.getInstance().getUUID());
               editor.commit();

               Intent intent = new Intent(SMSCodeActivity.this,HomeActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
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
        startActivity(new Intent(this,SendTlfRegisterActivity.class));
        finish();
    }

    @Override
    public void onBackPressed(){
        goBack();
    }




}


