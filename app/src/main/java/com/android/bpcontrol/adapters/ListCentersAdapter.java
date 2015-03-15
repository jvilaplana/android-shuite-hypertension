package com.android.bpcontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.fragments.CentersListFragment;
import com.android.bpcontrol.model.Center;

import java.util.List;

/**
 * Created by adrian on 15/3/15.
 */
public class ListCentersAdapter extends BaseAdapter {

    private Context context;
    private List<Center> centers;

    public ListCentersAdapter(final Context context, List<Center> centers){

        this.context = context;
        this.centers = centers;
 ;
    }

    @Override
    public int getCount() {
        return centers.size();
    }

    @Override
    public Center getItem(int position) {
        return centers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Center center = getItem(position);
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.centercell,null);
            holder.name = (RobotoTextView)convertView.findViewById(R.id.centername);
            holder.city = (RobotoTextView)convertView.findViewById(R.id.centercity);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(center.getName());
        holder.city.setText(center.getCity());

        return convertView;
    }

    static class ViewHolder{

        public RobotoTextView name;
        public RobotoTextView city;

    }

}
