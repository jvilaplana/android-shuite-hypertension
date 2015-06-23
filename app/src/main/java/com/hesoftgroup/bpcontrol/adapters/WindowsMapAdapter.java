package com.hesoftgroup.bpcontrol.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hesoftgroup.bpcontrol.MapsActivity;
import com.hesoftgroup.bpcontrol.R;
import com.hesoftgroup.bpcontrol.customviews.RobotoTextView;
import com.hesoftgroup.bpcontrol.model.Center;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Adrian on 21/3/15.
 */
public class WindowsMapAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private Center center;
    private Bundle bundle;

    public WindowsMapAdapter(Context context, Center center, Bundle bundle){
        this.context = context;
        this.center = center;
        this.bundle = bundle;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = LayoutInflater.from(context).inflate(R.layout.mapswindows,null);

        ((RobotoTextView)view.findViewById(R.id.mapwindowtitle)).setText(center.getName());

        if (bundle !=null) {
            ((RobotoTextView)view.findViewById(R.id.direction)).setText(bundle.getString(MapsActivity.LOCATION_ADDRESS,""));
            ((RobotoTextView) view.findViewById(R.id.citypcode)).setText(bundle.getString(
                    MapsActivity.LOCATION_POSTALCODE,"")+" "+bundle.getString(MapsActivity.LOCATION_CITY,""));
            ((RobotoTextView) view.findViewById(R.id.provincecountry)).setText(bundle.getString(
                    MapsActivity.LOCATION_PROVINCE,"")+" ("+bundle.getString(MapsActivity.LOCATION_COUNTRY,"")+")");
        }

        ((RobotoTextView) view.findViewById(R.id.webpage)).setText(
                center.getWebpage()==null || center.getWebpage().equals("null")?"":center.getWebpage());
        ((RobotoTextView) view.findViewById(R.id.mailcontact)).setText(
                center.getWebpage()==null  || center.getEmail().equals("null")?"":center.getEmail());
        ((RobotoTextView) view.findViewById(R.id.tlf)).setText(
                center.getWebpage()==null  || center.getTlf().equals("null")?"":center.getTlf());

        ImageView image = (ImageView) view.findViewById(R.id.windowmapimage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ADAPTERINFOVIEW","PULSADO");
            }
        });


        return view;
    }
}
