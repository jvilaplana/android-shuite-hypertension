package com.android.bpcontrol.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.BPEditText;

import java.net.URI;

/**
 * Created by Adrian on 10/02/2015.
 */
public class ContactFragment extends Fragment {

    private Button sendMail;
    private BPEditText subject;
    private BPEditText body;




    public static ContactFragment getNewInstace(){

        ContactFragment contactFragment = new ContactFragment();
        return contactFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.contactfragment,null);
        sendMail = (Button) view.findViewById(R.id.sendEmail);
        subject = (BPEditText) view.findViewById(R.id.subject);
        body = (BPEditText) view.findViewById(R.id.body);
       return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(subject.getText().toString().equals("") && body.getText().toString().equals(""))){

                    sendEmail();

                }else{
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.emptyemail))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });

    }


   private void sendEmail() {

          Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));

          email.setType("message/rfc822");

          String[] recipients = {"info@hesoftgroup.eu"};

          email.putExtra(Intent.EXTRA_EMAIL, recipients);
          email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
          email.putExtra(Intent.EXTRA_TEXT, body.getText().toString());
          subject.setText("");
          body.setText("");

          try {
                 startActivity(Intent.createChooser(email, ""));

              } catch (android.content.ActivityNotFoundException ex) {
                new AlertDialog.Builder(getActivity())
                      .setTitle("Opss..")
                      .setMessage(getResources().getString(R.string.dialogcontact))
                      .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      })
                      .setIcon(android.R.drawable.ic_dialog_alert)
                      .show();
              }
       }
}
