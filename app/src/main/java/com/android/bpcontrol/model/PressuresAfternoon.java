package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.PressureOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Adrian on 14/02/2015.
 */
public class PressuresAfternoon extends Pressures {


    @Override
    public boolean areMorningPressures() {
        return false;
    }

    @Override
    public boolean areAfternoonPressures() {
        return true;
    }
}
