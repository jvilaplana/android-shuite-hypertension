package com.android.bpcontrol.controllers;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.Stack;


public class HomeFragmentManager {

    private static HomeFragmentManager instance;

    public static HomeFragmentManager getInstance(Context context) {
        if (instance == null)
            instance = new HomeFragmentManager(context);
        return instance;
    }

    private HomeFragmentManager(Context context) {
        this.ctx = context;
    }

    private Stack<Fragment> homeFragmentStack;
    private Fragment lastFragment;
    private Fragment homeFragment;
    private Context ctx;

    public Stack<Fragment> getHomeFragmentStack() {
        if (homeFragmentStack == null)
            homeFragmentStack = new Stack<Fragment>();
        return homeFragmentStack;
    }

    public void setHomeFragmentStack(Stack<Fragment> homeFragmentStack) {
        this.homeFragmentStack = homeFragmentStack;
    }

    public Fragment getLastFragment() {
        return lastFragment;
    }

    public void setLastFragment(Fragment lastFragment) {
        this.lastFragment = lastFragment;
    }

    public boolean isSameAsLastFragment(Fragment newFragment) {
        return lastFragment.equals(newFragment);
    }

    public void removeFromStack(Fragment fragment) {

        Fragment removable = null;
        if (getHomeFragmentStack() != null && !getHomeFragmentStack().isEmpty()) {
            for (Fragment current : getHomeFragmentStack()) {
                if (current!= null && current.getClass() == fragment.getClass()) {
                    removable = current;
                }
            }
            if (removable != null) {
                getHomeFragmentStack().remove(removable);
            }
        }
    }

    public boolean contains(Fragment fragment) {
        Stack<Fragment> fragmentStack = getHomeFragmentStack();
        boolean contains = false;
        if (fragmentStack != null) {

            for (Fragment current : fragmentStack) {
                if (current.getClass() == fragment.getClass()) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    public Fragment getHomeFragment() {
        return homeFragment;
    }

    public void setHomeFragment(Fragment homeFragment) {
        this.homeFragment = homeFragment;
    }
}