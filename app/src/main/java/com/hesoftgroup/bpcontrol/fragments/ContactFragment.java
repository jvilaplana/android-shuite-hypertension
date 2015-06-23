package com.hesoftgroup.bpcontrol.fragments;

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

import com.hesoftgroup.bpcontrol.R;
import com.hesoftgroup.bpcontrol.customviews.BPEditText;
import com.hesoftgroup.bpcontrol.customviews.RobotoTextView;

import java.net.URI;

/**
 * Created by Adrian on 10/02/2015.
 */
public class ContactFragment extends Fragment {

    private Button sendMail;
    private BPEditText subject;
    private BPEditText body;

    private RobotoTextView weblink;

    private int viewpagerposition;



    public static ContactFragment getNewInstace(int position){

        ContactFragment contactFragment = new ContactFragment().setPosition(position);
        return contactFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = null;
        if (viewpagerposition != 0) {
            view = inflater.inflate(R.layout.contactfragment, null);
            sendMail = (Button) view.findViewById(R.id.sendEmail);
            subject = (BPEditText) view.findViewById(R.id.subject);
            body = (BPEditText) view.findViewById(R.id.body);
        }else{
            view = inflater.inflate(R.layout.contactfragment2, null);
            weblink = (RobotoTextView)view.findViewById(R.id.web);
        }
       return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(viewpagerposition != 0) {
            sendMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!(subject.getText().toString().equals("") && body.getText().toString().equals(""))) {

                        sendEmail();

                    } else {
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
    }else{

            weblink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(weblink.getText().toString()));
                    startActivity(browserIntent);
                }
            });
        }
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

    public ContactFragment setPosition(int position){

        this.viewpagerposition = position;
        return this;
    }
}
