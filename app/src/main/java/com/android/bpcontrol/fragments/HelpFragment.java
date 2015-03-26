package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.BPEditText;
import com.android.bpcontrol.customviews.RobotoTextView;

/**
 * Created by Adrian on 26/3/15.
 */
public class HelpFragment extends Fragment {

    private int viewpagerposition;

    public static HelpFragment getNewInstance(int position){

        HelpFragment helpFragment = new HelpFragment().setPosition(position);
        return helpFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = null;

            view = inflater.inflate(R.layout.pressurehelplayout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private HelpFragment setPosition(int position){
        this.viewpagerposition = position;

        return this;
    }

    }
