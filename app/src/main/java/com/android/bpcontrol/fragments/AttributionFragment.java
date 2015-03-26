package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bpcontrol.R;

/**
 * Created by Adrian on 26/3/15.
 */
public class AttributionFragment extends Fragment {


    public static AttributionFragment getNewInstance(int position){

        AttributionFragment attributionFragment = new AttributionFragment();
        return attributionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = null;

        view = inflater.inflate(R.layout.attributionlayout, null);
       return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
