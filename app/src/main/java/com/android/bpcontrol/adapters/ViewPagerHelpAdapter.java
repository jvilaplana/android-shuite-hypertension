package com.android.bpcontrol.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.android.bpcontrol.fragments.ContactFragment;
import com.android.bpcontrol.fragments.HelpFragment;

/**
 * Created by Adrian on 26/3/15.
 */
public class ViewPagerHelpAdapter extends FragmentPagerAdapter {

    private FragmentManager manager;

    public ViewPagerHelpAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        manager = fragmentManager;
    }

    @Override
    public Fragment getItem(int position) {
        return  HelpFragment.getNewInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    }
