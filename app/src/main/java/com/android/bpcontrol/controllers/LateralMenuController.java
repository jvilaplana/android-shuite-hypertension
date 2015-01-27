package com.android.bpcontrol.controllers;

import android.content.Context;
import android.widget.ImageView;

import com.android.bpcontrol.R;
import com.android.bpcontrol.model.MenuItem;

import java.util.ArrayList;

/**
 * Created by Adrian Carrera on 27/1/15.
 */
public class LateralMenuController{

    public static enum MenuSections{

        MYPERFIL,
        PRINCIPAL,
        PRESSURES,
        EVOLUCION,
        HISTORIAL,
        MESSAGES,
        VIDEOS,
        HEALTHCENTERS,
        CONTACT,
        HELP,
        ATTRIBUTIONS,
        FACEBOOK,
        TWITTER,
        GOOGLEPLUS

    }

    public static enum MenuItemCategory{

        MYPERFIL,
        APP_SECTIONS,
        SOCIAL,
        OTHERS
    }

    private ArrayList<MenuItem> app_sections =  new ArrayList<>();
    private ArrayList<MenuItem>  social  = new ArrayList<>();
    private ArrayList<MenuItem> others = new ArrayList<>();
    private ArrayList<MenuItem> headers = new ArrayList<>();
    private MenuItem perfil;

    public static LateralMenuController instance;

    private LateralMenuController(){}

    public LateralMenuController getInstance(){

        if(instance == null) {

            instance = new LateralMenuController();
        }

        return instance;
    }

    public void initItems(Context context){

       ImageView image =  new ImageView(context);

       perfil =  new MenuItem.Builder("Adrian Carrera")
                             .setCategory_id(MenuItemCategory.MYPERFIL)
                             .build();

       initHeaders(context);
       initAppSections(context);
       initOthers(context);
       initSocial();

    }

    private void initHeaders(Context context){


        headers.add(new MenuItem.Builder(context.getResources().getString(R.string.menuheadersection))
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .build());

        headers.add(new MenuItem.Builder(context.getResources().getString(R.string.menuheadersocial))
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .build());

        headers.add(new MenuItem.Builder(context.getResources().getString(R.string.menuheaderothers))
                .setCategory_id(MenuItemCategory.SOCIAL)
                .build());
    }

    private void initAppSections(Context context){

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menusection_preassures))
                .setId(MenuSections.PRINCIPAL)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_home)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menusection_preassures))
                .setId(MenuSections.PRESSURES)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_clock)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.homegrid_graphics))
                .setId(MenuSections.EVOLUCION)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_line_chart)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menusection_history))
                .setId(MenuSections.HISTORIAL)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_folder_open)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.homegrid_messages))
                .setId(MenuSections.MESSAGES)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_dialog)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.homegrid_videos))
                .setId(MenuSections.VIDEOS)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_youtube)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menusection_centers))
                .setId(MenuSections.HEALTHCENTERS)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_location)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menusection_contact))
                .setId(MenuSections.CONTACT)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_phone_start)
                .build());

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menusection_help))
                .setId(MenuSections.HELP)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_help)
                .build());

    }

    private void initOthers(Context context){

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menusection_help))
                .setId(MenuSections.ATTRIBUTIONS)
                .setCategory_id(MenuItemCategory.OTHERS)
                .setImageFromResource(R.drawable.ic_action_present)
                .build());

    }

    private void initSocial(){

        app_sections.add(new MenuItem.Builder("Facebook")
                .setId(MenuSections.FACEBOOK)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_facebook)
                .build());


        app_sections.add(new MenuItem.Builder("Twitter")
                .setId(MenuSections.TWITTER)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_twitter)
                .build());

        app_sections.add(new MenuItem.Builder("Google +")
                .setId(MenuSections.GOOGLEPLUS)
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .setImageFromResource(R.drawable.ic_action_gplus)
                .build());

    }



}
