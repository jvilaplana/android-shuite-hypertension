package com.android.bpcontrol.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.bpcontrol.fragments.ContactFragment;

/**
 * Created by Adrian on 11/02/2015.
 */
public class ViewPagerContactAdapter extends FragmentPagerAdapter {

    public ViewPagerContactAdapter(FragmentManager fragmentManager) {
             super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
       return  ContactFragment.getNewInstace(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
