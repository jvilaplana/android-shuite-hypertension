package com.android.bpcontrol.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.bpcontrol.R;

import org.w3c.dom.Text;

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

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout relativeLayout =(RelativeLayout) inflater.inflate(R.layout.homegridcell,null);
            relativeLayout.setBackgroundResource(resources[position]);
            //TextView text =(TextView) relativeLayout.findViewById(R.id.homegridtext);
            return relativeLayout;
        }else{

            return convertView;
        }
    }
}
