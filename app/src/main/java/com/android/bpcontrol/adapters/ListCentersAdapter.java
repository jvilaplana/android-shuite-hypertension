package com.android.bpcontrol.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.fragments.CentersListFragment;
import com.android.bpcontrol.model.Center;

import java.util.List;

/**
 * Created by adrian on 15/3/15.
 */
public class ListCentersAdapter extends BaseAdapter {

    private List<Center> centers;
    private CentersListFragment.CentersView type;
    private View.OnClickListener mapClickListener;

    public ListCentersAdapter(List<Center> centers,CentersListFragment.CentersView type ){

        this.centers = centers;
        this.type = type;
    }

    @Override
    public int getCount() {
        return centers.size();
    }

    @Override
    public Object getItem(int position) {
        return centers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    static class ViewHolder{

        public RobotoTextView name;
        public RobotoTextView direction;
        public ImageView map;

    }

    public void setMapClickListener(View.OnClickListener mapClickListener){
        this.mapClickListener = mapClickListener;
    }
}
