package com.hesoftgroup.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hesoftgroup.bpcontrol.R;

/**
 * Created by Adrian on 02/02/2015.
 */
public class InformationFragment extends Fragment{

    public static InformationFragment getNewInstance(){

        InformationFragment  initialFragment = new InformationFragment();
        return initialFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.infofragmentlayout, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
