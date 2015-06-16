package com.android.bpcontrol.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.BPEditText;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.databases.BPcontrolDB;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.Pressures;
import com.android.bpcontrol.model.PressuresAfternoon;
import com.android.bpcontrol.model.PressuresMorning;
import com.android.bpcontrol.model.YoutubeVideo;
import com.android.bpcontrol.utils.DateUtils;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.bpcontrol.webservice.WSManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian on 12/2/15.
 */

public class PressuresFragment extends Fragment
                               implements NumberPicker.OnValueChangeListener {

    private String systolic1n, systolic2n, systolic3n, systolic1m, systolic2m, systolic3m;
    private String diastolic1n, diastolic2n, diastolic3n, diastolic1m, diastolic2m, diastolic3m;
    private String pulse1n, pulse2n, pulse3n, pulse1m, pulse2m, pulse3m;
    private boolean bSystolic1n = false, bSystolic2n = false, bSystolic3n = false, bSystolic1m = false,
            bSystolic2m = false, bSystolic3m = false;
    private boolean bDiastolic1n = false, bDiastolic2n = false, bDiastolic3n = false, bDiastolic1m = false,
            bDiastolic2m = false, bDiastolic3m = false;
    private boolean bPulse1n = false, bPulse2n = false, bPulse3n = false, bPulse1m = false,
            bPulse2m = false, bPulse3m = false;

    private Button buttonsend,buttonsave,buttonsave2;

    private BPcontrolDB db;

    int choosedspinner = 0;

    private ArrayList<BPEditText> editTextsMorning = new ArrayList<>();
    private ArrayList<BPEditText> editTextsAfternoon = new ArrayList<>();

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new saveData().execute();
        }
    };

    public static PressuresFragment getNewInstace() {

        PressuresFragment pressuresFragment = new PressuresFragment();
        return pressuresFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.pressureslayout, null);
        ViewGroup group = (ViewGroup) view.findViewById(R.id.edittextspressures);
        ViewGroup group2 = (ViewGroup) view.findViewById(R.id.edittextspressures2);
        group.setTag(new Integer(0));
        group2.setTag(new Integer(1));
        buttonsend = (Button) view.findViewById(R.id.sendpressures);
        buttonsave2 = (Button) view.findViewById(R.id.save2);
        getEditTexts(group, editTextsMorning);
        getEditTexts(group2, editTextsAfternoon);


        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String lastupdatedate = getLastDateSent();

        try {
            if (!lastupdatedate.equals("") && DateUtils.isDateEqualsToTodayDate(lastupdatedate)) {

                showDialogPressuresIntroduced();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        buttonsave2.setOnClickListener(saveListener);

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastupdatedate = getLastDateSent();
                try {
                   if (!DateUtils.isDateEqualsToTodayDate(lastupdatedate)) {

                        if (isCorrectAfternoonMeassurament() && isCorrectMorningMeassurament()) {

                            new sendPressures().execute();

                        } else {

                            showDialog(getResources().getString(R.string.messagesend));
                        }
                   }else{
                        showDialogPressuresIntroduced();
                   }

                } catch (Exception ex) {
                    LogBP.printStackTrace(ex);
                }
            }
        });
        db = new BPcontrolDB(getActivity());

        checkIfDataSaved();
    }


    private void getEditTexts(ViewGroup group,List<BPEditText> editTexts) {
        final int tag = (Integer)group.getTag();
        BPEditText tmp;
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                tmp = (BPEditText) ((ViewGroup) group.getChildAt(i)).getChildAt(j);
                tmp.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == 1) {
                            BPEditText editText = (BPEditText) v;
                            if (tag==0){
                                choosedspinner=0;
                            }else{
                                choosedspinner=1;
                            }
                            Integer pos = (int) Integer.parseInt((String) v.getTag());
                            showPicker(pos);
                        }
                        return false;
                    }
                });
                editTexts.add(tmp);
                counter++;
            }
        }
        editTexts.get(0);
    }

    private void showPicker(final int position) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pickerdialog);
        dialog.setCancelable(false);
        Button buttonAccept = (Button) dialog.findViewById(R.id.buttonAcceptDialog);
        Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCanceldialog);
        RobotoTextView headertittle = (RobotoTextView) dialog.findViewById(R.id.headertextdialog);
        final NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker1);

        if (position == 1 || position == 2 || position == 3) {
            headertittle.setText(getResources().getString(R.string.dialogsystolic));
            numberPicker.setMaxValue(240);
            numberPicker.setMinValue(50);
            numberPicker.setValue(120);
        } else if (position == 4 || position == 5 || position == 6) {
            headertittle.setText(getResources().getString(R.string.dialogdiastolic));
            numberPicker.setMaxValue(140);
            numberPicker.setMinValue(30);
            numberPicker.setValue(75);
        } else {
            headertittle.setText(getResources().getString(R.string.dialogpulse));
            numberPicker.setMaxValue(220);
            numberPicker.setMinValue(10);
            numberPicker.setValue(80);
        }
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(this);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pickerOnResut(position, numberPicker.getValue());
                dialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    private void pickerOnResut(final int position, final int pickervalue) {

        switch (position) {

            case 1:
                if (isMorning()) {
                    systolic1m = String.valueOf(pickervalue);
                    bSystolic1m = true;

                } else {
                    systolic1n = String.valueOf(pickervalue);
                    bSystolic1n = true;
                }

                break;
            case 2:
                if (isMorning()) {
                    systolic2m= String.valueOf(pickervalue);
                    bSystolic2m = true;
                } else {
                    systolic2n = String.valueOf(pickervalue);
                    bSystolic2n = true;

                }
                break;
            case 3:
                if (isMorning()) {
                    systolic3m = String.valueOf(pickervalue);
                    bSystolic3m = true;

                } else {
                    systolic3n = String.valueOf(pickervalue);
                    bSystolic3n = true;
                }
                break;
            case 4:
                if (isMorning()) {
                    diastolic1m = String.valueOf(pickervalue);
                    bDiastolic1m = true;
                } else {
                    diastolic1n = String.valueOf(pickervalue);
                    bDiastolic1n = true;
                }
                break;
            case 5:
                if (isMorning()) {
                    diastolic2m = String.valueOf(pickervalue);
                    bDiastolic2m = true;
                } else {
                    diastolic2n = String.valueOf(pickervalue);
                    bDiastolic2n = true;

                }
                break;
            case 6:
                if (isMorning()) {
                    diastolic3m = String.valueOf(pickervalue);
                    bDiastolic3m = true;
                } else {
                    diastolic3n = String.valueOf(pickervalue);
                    bDiastolic3n = true;
                }
                break;
            case 7:
                if (isMorning()) {
                    pulse1m = String.valueOf(pickervalue);
                    bPulse1m = true;
                } else {
                    pulse1n= String.valueOf(pickervalue);
                    bPulse1n = true;
                }
                break;
            case 8:
                if (isMorning()) {
                    pulse2m = String.valueOf(pickervalue);
                    bPulse2m = true;
                } else {
                    pulse2n = String.valueOf(pickervalue);
                    bPulse2n= true;

                }
                break;
            case 9:
                if (isMorning()) {
                    pulse3m = String.valueOf(pickervalue);
                    bPulse3m = true;
                } else {
                    pulse3n = String.valueOf(pickervalue);
                    bPulse3n = true;
                }
                break;
            default:
                break;


        }
        if (isMorning()) {
            BPEditText editText = editTextsMorning.get(position - 1);
            editText.setText(String.valueOf(pickervalue));
        }else{
            BPEditText editText = editTextsAfternoon.get(position - 1);
            editText.setText(String.valueOf(pickervalue));
        }

    }

    private boolean isCorrectAfternoonMeassurament() {

        return bSystolic1n && bDiastolic1n && bPulse1n && bSystolic2n && bDiastolic2n
                && bPulse2n && bSystolic3n && bDiastolic3n && bPulse3n;
    }

    private boolean isCorrectMorningMeassurament() {

        return bSystolic1m && bDiastolic1m && bPulse1m && bSystolic2m && bDiastolic2m
                && bPulse2m && bSystolic3m && bDiastolic3m && bPulse3m;
    }

    private boolean isMorning() {

        return choosedspinner == 0;
    }

    private boolean isAfternoon() {

        return choosedspinner != 0;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }


    private class sendPressures extends AsyncTask<Void,Boolean,Void>{


        @Override
        protected void onPreExecute() {

            ((HomeActivity)getActivity()).showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {

            final PressuresMorning pMorning = new PressuresMorning();
            pMorning.add(new Pressure(systolic1m,diastolic1m,pulse1m));
            pMorning.add(new Pressure(systolic2m,diastolic2m,pulse2m));
            pMorning.add(new Pressure(systolic3m,diastolic3m,pulse3m));

            final PressuresAfternoon pAfternoon = new PressuresAfternoon();
            pAfternoon.add(new Pressure(systolic1n,diastolic1n,pulse1n));
            pAfternoon.add(new Pressure(systolic2n,diastolic2n,pulse2n));
            pAfternoon.add(new Pressure(systolic3n,diastolic3n,pulse3n));
            WSManager.getInstance().sendPressures(getActivity(), pMorning, pAfternoon, new WSManager.SendPressures() {
                @Override
                public void onSendPressures(YoutubeVideo youtubeVideo, int semaphore) {

                    boolean video = youtubeVideo != null ? true : false;
                    saveDataInDB(pMorning, pAfternoon, semaphore, youtubeVideo);
                    clearOldValues();
                    publishProgress(new Boolean(video));
                }
            });

            return null;
        }
        @Override
        protected void onProgressUpdate(Boolean...video){

            String linebreak = System.getProperty("line.separator");
            if (video[0].booleanValue()) {
                showDialog(getActivity().getResources().getString(R.string.sentpressuresOK)+linebreak+
                        getActivity().getResources().getString(R.string.videoavailable));
            }else {
                showDialog(getActivity().getResources().getString(R.string.sentpressuresOK));
            }
            ((HomeActivity)getActivity()).dissmissProgressDialog();
        }


    }

   private void clearOldValues(){


           bSystolic1n = false;
           bSystolic2n = false;
           bSystolic3n = false;
           bSystolic1m = false;
           bSystolic2m = false;
           bSystolic3m = false;
           bDiastolic1n = false;
           bDiastolic2n = false;
           bDiastolic3n = false;
           bDiastolic1m = false;
           bDiastolic2m = false;
           bDiastolic3m = false;
           bPulse1n = false;
           bPulse2n = false;
           bPulse3n = false;
           bPulse1m = false;
           bPulse2m = false;
           bPulse3m = false;

           SharedPreferences preferences = getActivity()
                   .getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, Context.MODE_PRIVATE);
           SharedPreferences.Editor editor = preferences.edit();

           editor.putString(SharedPreferenceConstants.SYSTOLICM, "");
           editor.putString(SharedPreferenceConstants.DIASTOLICM, "");
           editor.putString(SharedPreferenceConstants.PULSEM, "");
           editor.putString(SharedPreferenceConstants.SYSTOLICN, "");
           editor.putString(SharedPreferenceConstants.DIASTOLICN, "");
           editor.putString(SharedPreferenceConstants.PULSEN, "");
           editor.commit();

           for(BPEditText editText : editTextsMorning){
               editText.setText("");
           }
           for(BPEditText editText : editTextsAfternoon){
               editText.setText("");
           }

   }

    private void checkIfDataSaved() {

        SharedPreferences preferences = getActivity()
                .getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, Context.MODE_PRIVATE);


        if (!preferences.getString(SharedPreferenceConstants.SYSTOLICM, "").equals("")) {


            String[] systolicm = preferences.getString(SharedPreferenceConstants.SYSTOLICM, "").split(" ");
            systolic1m = systolicm[0];
            systolic2m = systolicm[1];
            systolic3m = systolicm[2];

            editTextsMorning.get(0).setText(systolic1m);
            editTextsMorning.get(1).setText(systolic2m);
            editTextsMorning.get(2).setText(systolic3m);

            bSystolic1m = true;
            bSystolic2m = true;
            bSystolic3m = true;


        }

        if (!preferences.getString(SharedPreferenceConstants.SYSTOLICN, "").equals("")) {

            String[] systolicn = preferences.getString(SharedPreferenceConstants.SYSTOLICN, "").split(" ");
            systolic1n = systolicn[0];
            systolic2n = systolicn[1];
            systolic3n = systolicn[2];

            editTextsAfternoon.get(0).setText(systolic1n);
            editTextsAfternoon.get(1).setText(systolic2n);
            editTextsAfternoon.get(2).setText(systolic3n);

            bSystolic1n = true;
            bSystolic2n = true;
            bSystolic3n = true;

        }
        if (!preferences.getString(SharedPreferenceConstants.DIASTOLICM, "").equals("")) {

            String[] diastolicm = preferences.getString(SharedPreferenceConstants.DIASTOLICM, "").split(" ");
            diastolic1m = diastolicm[0];
            diastolic2m = diastolicm[1];
            diastolic3m = diastolicm[2];

            editTextsMorning.get(3).setText(diastolic1m);
            editTextsMorning.get(4).setText(diastolic2m);
            editTextsMorning.get(5).setText(diastolic3m);

            bDiastolic1m = true;
            bDiastolic2m = true;
            bDiastolic3m = true;

        }

        if (!preferences.getString(SharedPreferenceConstants.DIASTOLICN, "").equals("")) {

            String[] diastolicn = preferences.getString(SharedPreferenceConstants.DIASTOLICN, "").split(" ");
            diastolic1n = diastolicn[0];
            diastolic2n = diastolicn[1];
            diastolic3n = diastolicn[2];

            editTextsAfternoon.get(3).setText(diastolic1n);
            editTextsAfternoon.get(4).setText(diastolic2n);
            editTextsAfternoon.get(5).setText(diastolic3n);

            bDiastolic1n = true;
            bDiastolic2n = true;
            bDiastolic3n = true;
        }
        if (!preferences.getString(SharedPreferenceConstants.PULSEM, "").equals("")) {

            String[] pulsem = preferences.getString(SharedPreferenceConstants.PULSEM, "").split(" ");
            pulse1m = pulsem[0];
            pulse2m = pulsem[1];
            pulse3m = pulsem[2];

            editTextsMorning.get(6).setText(pulse1m);
            editTextsMorning.get(7).setText(pulse2m);
            editTextsMorning.get(8).setText(pulse3m);

            bPulse1m = true;
            bPulse2m = true;
            bPulse3m = true;
        }

        if (!preferences.getString(SharedPreferenceConstants.PULSEN, "").equals("")) {

            String[] pulsen = preferences.getString(SharedPreferenceConstants.PULSEN, "").split(" ");
            pulse1n = pulsen[0];
            pulse2n = pulsen[1];
            pulse3n = pulsen[2];

            editTextsAfternoon.get(6).setText(pulse1n);
            editTextsAfternoon.get(7).setText(pulse2n);
            editTextsAfternoon.get(8).setText(pulse3n);

            bPulse1n = true;
            bPulse2n = true;
            bPulse3n = true;
        }


    }

    private class saveData extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {

            ((HomeActivity) getActivity()).showProgressDialog();

        }

        @Override
        protected Void doInBackground(Void... par) {

            if (isCorrectAfternoonMeassurament() || isCorrectMorningMeassurament()) {

                savePressures();
                publishProgress(getResources().getString(R.string.saveddata));
            }else{

                publishProgress(getResources().getString(R.string.emptysavemessage));
            }

           ((HomeActivity)getActivity()).dissmissProgressDialog();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... par){

            showDialog(par[0]);
        }
    }

    private void showDialog(String msg){


      AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMessage(msg);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.show();
        TextView msgText = (TextView)dialog.findViewById(android.R.id.message);
        msgText.setGravity(Gravity.CENTER);

        dialog.show();



    }

    private void savePressures(){
        SharedPreferences preferences = getActivity()
                .getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (isCorrectMorningMeassurament()) {
            editor.putString(SharedPreferenceConstants.SYSTOLICM, systolic1m + " " + systolic2m + " " + systolic3m);
            editor.putString(SharedPreferenceConstants.DIASTOLICM, diastolic1m + " " + diastolic2m + " " + diastolic3m);
            editor.putString(SharedPreferenceConstants.PULSEM, pulse1m + " " + pulse2m + " " + pulse3m);
        }
        if (isCorrectAfternoonMeassurament()) {
            editor.putString(SharedPreferenceConstants.SYSTOLICN, systolic1n + " " + systolic2n + " " + systolic3n);
            editor.putString(SharedPreferenceConstants.DIASTOLICN, diastolic1n + " " + diastolic2n + " " + diastolic3n);
            editor.putString(SharedPreferenceConstants.PULSEN, pulse1n + " " + pulse2n + " " + pulse3n);

        }
        editor.commit();

        ((HomeActivity)getActivity()).dissmissProgressDialog();
    }

    private void saveDataInDB(PressuresMorning morning, PressuresAfternoon afternoon, int semaphore,
                              YoutubeVideo youtubeVideo){

        Pressure average = Pressures.obtainPressuresDayAverage(morning,afternoon);
        average.setSemaphore(semaphore);
        try {
            average.setDate(DateUtils.stringToDate(DateUtils.dateToString(new Date(),
                    DateUtils.DEFAULT_FORMAT),DateUtils.DEFAULT_FORMAT));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, Context.MODE_PRIVATE);

        String date = DateUtils.dateToString(average.getDate(),DateUtils.DEFAULT_FORMAT);



        if ((sharedPreferences.getString(SharedPreferenceConstants.LASTSENDPRESSURE, "").equals(date))) {

            //db.updatePressureAverage(average);
        }else{

            db.addPressureAverage(average);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SharedPreferenceConstants.LASTSENDPRESSURE,date);
            editor.commit();
        }

            if (youtubeVideo != null){
                db.addYoutubeVideo(youtubeVideo);
            }

        }


    private void showDialogPressuresIntroduced() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getResources().getString(R.string.pressuresintroduced));
        builder.setPositiveButton(getActivity().getResources().getString(R.string.noconnectiondialogpositiveWS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

        dialog.show();
    }


    private String getLastDateSent(){

        SharedPreferences preferences = getActivity()
                .getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return preferences.getString(SharedPreferenceConstants.LASTSENDPRESSURE, "");

    }


}
