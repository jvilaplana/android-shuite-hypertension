package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.BPEditText;
import com.android.bpcontrol.customviews.RobotoTextView;

import java.util.List;

/**
 * Created by Adrian on 20/02/2015.
 */
public class HistorialPressuresFragment extends Fragment {

     private ListView listView;


    public static HistorialPressuresFragment getNewInstace(){

        HistorialPressuresFragment historialPressuresFragment = new HistorialPressuresFragment();
        return historialPressuresFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.historialpressureslayout,null);
        listView = (ListView)view.findViewById(R.id.resultados_list);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
