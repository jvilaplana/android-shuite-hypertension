package com.android.bpcontrol.model;

import com.android.bpcontrol.interfaces.PressureOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Adrian on 14/02/2015.
 */
public class PressuresMorning extends Pressures {

    @Override
    public boolean areMorningPressures() {
        return true;
    }

    @Override
    public boolean areAfternoonPressures() {
        return false;
    }
}
