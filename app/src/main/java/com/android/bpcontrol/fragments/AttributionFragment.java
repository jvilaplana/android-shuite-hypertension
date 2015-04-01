package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.RobotoTextView;

/**
 * Created by Adrian on 26/3/15.
 */
public class AttributionFragment extends Fragment {

    private RobotoTextView attributions;
    private RobotoTextView attributions2;
    private RobotoTextView attributions3;
    private RobotoTextView attributions4;
    public static AttributionFragment getNewInstance(){

        AttributionFragment attributionFragment = new AttributionFragment();
        return attributionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.attributionlayout, null);
        attributions = (RobotoTextView) view.findViewById(R.id.attributions);
        attributions2 = (RobotoTextView) view.findViewById(R.id.attributions2);
        attributions3 = (RobotoTextView) view.findViewById(R.id.attributions3);
        attributions4 = (RobotoTextView) view.findViewById(R.id.attributions4);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attributions.setText(Html.fromHtml( "<a href=\"http://www.google.com\">google</a>"));
        attributions.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
