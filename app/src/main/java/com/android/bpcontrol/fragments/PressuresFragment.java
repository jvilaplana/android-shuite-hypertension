package com.android.bpcontrol.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.BPEditText;

import java.util.ArrayList;

/**
 * Created by Adrian on 12/2/15.
 */
public class PressuresFragment extends Fragment
                               implements AdapterView.OnItemSelectedListener,
                                          NumberPicker.OnValueChangeListener{

        private String systolic1n,systolic2n,systolic3n,systolic1m,systolic2m,systolic3m;
        private String diastolic1n,diastolic2n,diastolic3n,diastolic1m,diastolic2m,diastolic3m;
        private Button buttonenviar;

        private Button buttonsave;


        private ArrayList<BPEditText> editTexts = new ArrayList<>();

        private Spinner spinner;


    public static PressuresFragment getNewInstace(){

        PressuresFragment pressuresFragment = new PressuresFragment();
        return pressuresFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.pressureslayout,null);
        spinner = (Spinner) view.findViewById(R.id.spinnerpressures);
        ViewGroup group = (ViewGroup)view.findViewById(R.id.edittextspressures);
        getEditTexts(group);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.spinnerpressures));
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        configureView();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    private void configureView(){


    }

    private void getEditTexts(ViewGroup group){

        BPEditText tmp;
        int counter = 0;
        for (int i=0;i<3;i++) {
            for (int j = 1; j < 4; j++) {
                tmp = (BPEditText)((ViewGroup) group.getChildAt(i)).getChildAt(j);
                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPicker();
                    }
                });
                editTexts.add(tmp);
                counter++;
            }
        }
    }

    private void showPicker(){



    }


    private class EditextPressureListener implements View.OnClickListener{





        @Override
        public void onClick(View v) {

        }
    }
}
