package com.android.bpcontrol.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bpcontrol.R;

/**
 * Created by Adrian on 22/02/2015.
 */
public class PressuresPlotFragment extends Fragment {

    private int viewpagerposition=0;


    public static PressuresPlotFragment getNewInstance(int position){

        PressuresPlotFragment  pressuresPlotFragment = new PressuresPlotFragment().setFragmentPosition(position);
        return pressuresPlotFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pressuresplotlayout, null);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    public PressuresPlotFragment setFragmentPosition(int position){

        this.viewpagerposition = position;

        return this;
    }







}
