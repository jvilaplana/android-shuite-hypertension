package com.android.bpcontrol.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.interfaces.HistorialAdapterItem;
import com.android.bpcontrol.model.HistorialCell;
import com.android.bpcontrol.model.HistorialHeader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Adrian on 20/02/2015.
 */
public class HistorialPressuresAdapter extends BaseAdapter{


    private LayoutInflater inflater;
    private Context context;
    private List<HistorialAdapterItem> items;

    public HistorialPressuresAdapter(Context context, List<HistorialAdapterItem> items){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewholder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.historialpressurecell, null);

            viewholder = new ViewHolder();
            viewholder.fetle = (ImageView) convertView.findViewById(R.id.fetletext);
            viewholder.systolic = (RobotoTextView) convertView.findViewById(R.id.systolictext);
            viewholder.diastolic = (RobotoTextView) convertView.findViewById(R.id.diastolictext);
            viewholder.pulse = (RobotoTextView) convertView.findViewById(R.id.pulsetext);
            viewholder.item = (HistorialAdapterItem)items.get(position);
            viewholder.date = (RobotoTextView) convertView.findViewById(R.id.historialdate);
            convertView.setTag(viewholder);

        }else{
                  viewholder = (ViewHolder)convertView.getTag();
         }


            switch (viewholder.item.getTag()){

                case 1:
                    HistorialCell cell = (HistorialCell)viewholder.item;
                    viewholder.fetle.setBackgroundResource(
                            chooseSempahoreImage(Integer.parseInt(cell.getSemaphore())));
                    viewholder.systolic.setText(cell.getSystolic());
                    viewholder.diastolic.setText(cell.getDiastolic());
                    viewholder.pulse.setText(cell.getPulse());
                    viewholder.date.setVisibility(View.GONE);
                    break;
                case 2:
                    HistorialHeader header = (HistorialHeader)viewholder.item;
                    viewholder.date.setText(header.getDate());
                    viewholder.date.setVisibility(View.VISIBLE);
                    viewholder.fetle.setVisibility(View.GONE);
                    viewholder.systolic.setVisibility(View.GONE);
                    viewholder.diastolic.setVisibility(View.GONE);
                    viewholder.pulse.setVisibility(View.GONE);
                    break;
            }


        return convertView;
    }


    static class ViewHolder{

        HistorialAdapterItem item;
        ImageView fetle;
        RobotoTextView systolic;
        RobotoTextView diastolic;
        RobotoTextView pulse;
        RobotoTextView date;

    }

    private int chooseSempahoreImage(int fetle){

        int resource=R.drawable.semafor_yellow;

        switch (fetle){

            case 0:
                resource = R.drawable.semafor_green;
               break;

            case 1:
                resource = R.drawable.semafor_yellow;
                break;

            case 2:
                resource = R.drawable.semafor_red;
                break;
        }

        return resource;
    }


}
