package com.android.bpcontrol.fragments;

import android.app.Dialog;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.BPEditText;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.utils.LogBP;

import java.util.ArrayList;

/**
 * Created by Adrian on 12/2/15.
 */
public class PressuresFragment extends Fragment
                               implements AdapterView.OnItemSelectedListener,
                                          NumberPicker.OnValueChangeListener {

    private String systolic1n, systolic2n, systolic3n, systolic1m, systolic2m, systolic3m;
    private String diastolic1n, diastolic2n, diastolic3n, diastolic1m, diastolic2m, diastolic3m;
    private boolean bSystolic1n = false, bSystolic2n = false, bSystolic3n = false, bSystolic1m = false, bSystolic2m = false, bSystolic3m = false;
    private boolean bDiastolic1n = false, bDiastolic2n = false, bDiastolic3n = false, bDiastolic1m = false, bDiastolic2m = false, bDiastolic3m = false;
    private Button buttonenviar;
    private Button buttonsave;

    int choosedspinner = 0;

    private ArrayList<BPEditText> editTexts = new ArrayList<>();

    private Spinner spinner;


    public static PressuresFragment getNewInstace() {

        PressuresFragment pressuresFragment = new PressuresFragment();
        return pressuresFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.pressureslayout, null);
        spinner = (Spinner) view.findViewById(R.id.spinnerpressures);
        ViewGroup group = (ViewGroup) view.findViewById(R.id.edittextspressures);
        getEditTexts(group);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinnerpressures));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        choosedspinner = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //nothing
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        //nothing

    }

    private void getEditTexts(ViewGroup group) {

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
                            if (editText.getText().toString().equals("")) {

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

        if (position == 1 || position == 4 || position == 7) {
            headertittle.setText(getResources().getString(R.string.dialogsystolic));
            numberPicker.setMaxValue(240);
            numberPicker.setMinValue(50);
            numberPicker.setValue(120);
        } else if (position == 2 || position == 5 || position == 8) {
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
                if (choosedspinner == 0) {
                    bSystolic1m = true;

                } else {

                }

                break;
            case 2:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            case 3:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            case 4:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            case 5:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            case 6:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            case 7:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            case 8:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            case 9:
                if (choosedspinner == 0) {

                } else {

                }
                break;
            default:
                break;


        }
        BPEditText editText = editTexts.get(position - 1);
        editText.setText(String.valueOf(pickervalue));

    }

    private boolean isCorrectAfternoonMeassurament() {


    }

    private boolean isCorrectAfternoonMeassurament1() {

    }




}
