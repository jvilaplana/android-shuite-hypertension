package com.hesoftgroup.bpcontrol.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.hesoftgroup.bpcontrol.fragments.ContactFragment;

import java.util.Objects;

/**
 * Created by Adrian on 11/02/2015.
 */
public class ViewPagerContactAdapter extends FragmentPagerAdapter {

    private FragmentManager manager;

    public ViewPagerContactAdapter(FragmentManager fragmentManager) {
             super(fragmentManager);
             manager = fragmentManager;
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
