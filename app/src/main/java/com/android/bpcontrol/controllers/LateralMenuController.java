package com.android.bpcontrol.controllers;

import android.widget.ImageView;

import com.android.bpcontrol.customViews.RobotoTextView;
import com.android.bpcontrol.model.MenuItem;

import java.util.ArrayList;

/**
 * Created by Adrian on 27/1/15.
 */
public class LateralMenuController{

    public static enum MenuSections{

        MYPERFIL,
        PREASSURES,
        EVOLUCION,
        HISTORIAL,
        MESSAGES,
        HEALTHCENTERS,
        CONTACT,
        HELP,
        ATTRIBUTIONS,
        FACEBOOK,
        TWITTER,
        GOOGLE+
    }

    public static enum MenuItemCategory{

        MYPERFIL,
        APP_SECTIONS,
        SOCIAL,
        OTHERS
    }

    private ArrayList<MenuItem> menuitem;


}
