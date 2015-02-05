package com.android.bpcontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.customViews.BPEditText;
import com.android.bpcontrol.customViews.RobotoTextView;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.webservice.WSManager;

/**
 * Created by Adrian Carrera on 03/02/2015.
 */
public class SendTlfRegister extends BPcontrolMasterActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendtlfregisterayout);

        configureActionBar();

        final BPEditText editPrefix = (BPEditText) findViewById(R.id.tlfprefix);
        final BPEditText editNumber = (BPEditText) findViewById(R.id.tlfnumber);


        Button button = (Button) findViewById(R.id.accesToSMScode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editNumber.length() == 9 && editPrefix.length() == 2){
                    showProgressDialog();
                    WSManager.getInstance().sendPhoneNumber(SendTlfRegister.this,editPrefix.getText().toString(),editNumber.getText().toString(), new WSManager.SendPhoneNumber() {
                        @Override
                        public void onRegisterPhone() {

                            //LogBP.writelog("Callback response WS in activity");
                            dissmissProgressDialog();
                            startActivity(new Intent(SendTlfRegister.this, SMSCodeActivity.class));


                        }
                    });

                }else{

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendTlfRegister.this);
                                                            alertDialog.setMessage(getResources().getString(R.string.errorprefixnumber));
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
