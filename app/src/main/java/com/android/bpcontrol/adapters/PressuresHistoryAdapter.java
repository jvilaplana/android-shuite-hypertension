package com.android.bpcontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.interfaces.PHistoryAdapterItem;
import com.android.bpcontrol.model.PHistoryCell;
import com.android.bpcontrol.model.PHistoryHeader;

import java.util.List;



/**
 * Created by Adrian on 20/02/2015.
 */

public class PressuresHistoryAdapter extends BaseAdapter{


    private LayoutInflater inflater;
    private Context context;
    private List<PHistoryAdapterItem> items;

    public PressuresHistoryAdapter(Context context, List<PHistoryAdapterItem> items){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PHistoryAdapterItem getItem(int position) {
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

            convertView = inflater.inflate(R.layout.pressureshistorycell, null);

            viewholder = new ViewHolder();
            viewholder.fetle = (ImageView) convertView.findViewById(R.id.fetleimage);
            viewholder.systolic = (RobotoTextView) convertView.findViewById(R.id.systolictext);
            viewholder.diastolic = (RobotoTextView) convertView.findViewById(R.id.diastolictext);
            viewholder.pulse = (RobotoTextView) convertView.findViewById(R.id.pulsetext);
            viewholder.date = (RobotoTextView) convertView.findViewById(R.id.historialdate);
            viewholder.cell = (LinearLayout) convertView.findViewById(R.id.historialcell);


            convertView.setTag(viewholder);

        }else{
            viewholder = (ViewHolder)convertView.getTag();
        }


        switch (getItem(position).getTag()){


            case 1:
                PHistoryCell cell = (PHistoryCell)getItem(position);
                viewholder.date.setVisibility(View.GONE);
                viewholder.pulse.setVisibility(View.VISIBLE);
                viewholder.systolic.setVisibility(View.VISIBLE);
                viewholder.diastolic.setVisibility(View.VISIBLE);
                viewholder.pulse.setVisibility(View.VISIBLE);
                viewholder.cell.setVisibility(View.VISIBLE);
                viewholder.fetle.setVisibility(View.VISIBLE);
                viewholder.fetle.setBackgroundResource(
                        chooseSempahoreImage(cell.getSemaphore()));
                viewholder.systolic.setText(cell.getSystolic());
                viewholder.diastolic.setText(cell.getDiastolic());
                viewholder.pulse.setText(cell.getPulse());

                break;
            case 2:
                PHistoryHeader header = (PHistoryHeader)getItem(position);
                viewholder.pulse.setVisibility(View.GONE);
                viewholder.systolic.setVisibility(View.GONE);
                viewholder.diastolic.setVisibility(View.GONE);
                viewholder.pulse.setVisibility(View.GONE);
                viewholder.cell.setVisibility(View.GONE);
                viewholder.fetle.setVisibility(View.GONE);
                viewholder.date.setVisibility(View.VISIBLE);
                viewholder.date.setText(header.getDate());
                break;
        }

        return convertView;

    }


    static class ViewHolder{
        ImageView fetle;
        RobotoTextView systolic;
        RobotoTextView diastolic;
        RobotoTextView pulse;
        LinearLayout cell;
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