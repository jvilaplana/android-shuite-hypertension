package com.android.bpcontrol.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.bpcontrol.R;

/**
 * Created by Adrian on 23/01/2015.
 */
public class HomeGridAdapter extends BaseAdapter {

    Context context;
    int[] resources;

    public HomeGridAdapter(Context context, int[] resources){

        this.context = context;
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.homegridcell,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.icongrid_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.homegridtext);
            convertView.setBackgroundResource(resources[position]);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    public static class ViewHolder{
        ImageView image;
        TextView textView;

    }
}
