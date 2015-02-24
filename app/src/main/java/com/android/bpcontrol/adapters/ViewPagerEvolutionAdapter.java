package com.android.bpcontrol.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.bpcontrol.fragments.PressuresPlotFragment;

/**
 * Created by Adrian on 23/02/2015.
 */
public class ViewPagerEvolutionAdapter extends FragmentPagerAdapter {

    public ViewPagerEvolutionAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return PressuresPlotFragment.getNewInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
