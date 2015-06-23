package com.hesoftgroup.bpcontrol.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hesoftgroup.bpcontrol.R;
import com.hesoftgroup.bpcontrol.model.GridCellResources;
import com.readystatesoftware.viewbadger.BadgeView;

/**
 * Created by Adrian on 23/01/2015.
 */
public class HomeGridAdapter extends BaseAdapter {

    private Context context;
    private GridCellResources[] resources;
    private BadgeView ref;

    public HomeGridAdapter(Context context, GridCellResources[] resources){

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
            viewHolder.badge  = (BadgeView) convertView.findViewById(R.id.messageBadge);
            convertView.setBackgroundResource(resources[position].getBackground_id());
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(context.getResources().getString(resources[position].getText_id()));
        viewHolder.image.setImageResource(resources[position].getIcon_id());
        if (position == 2){
            ref = viewHolder.badge;
        }
        viewHolder.badge.setVisibility(View.GONE);


        return convertView;
    }

    public BadgeView getBadgeRef(){
        return ref;
    }

    public static class ViewHolder{
        ImageView image;
        TextView textView;
        BadgeView badge;

    }
}
