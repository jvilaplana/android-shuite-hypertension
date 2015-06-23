package com.hesoftgroup.bpcontrol.controllers;

import android.content.Context;
import android.widget.ImageView;

import com.hesoftgroup.bpcontrol.R;
import com.hesoftgroup.bpcontrol.model.MenuItem;

import java.util.ArrayList;

/**
 * Created by Adrian Carrera on 27/1/15.
 */
public class LateralMenuController{

    public static enum MenuSections{

        HOME,
        MYPERFIL,
        PRINCIPAL,
        PRESSURES,
        EVOLUTION,
        HISTORIAL,
        MESSAGES,
        VIDEOS,
        HEALTHCENTERS,
        HEALTHCENTERS_SECONDLEVEL,
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
    private boolean elementsInit = false;

    private static LateralMenuController instance;

    private LateralMenuController(){}

    public static LateralMenuController getInstance(){

        if(instance == null) {

            instance = new LateralMenuController();
        }

        return instance;
    }

    public void initItems(Context context){

      if(!elementsInit) {

          perfil = new MenuItem.Builder("Adrian Carrera")
                  .setCategory_id(MenuItemCategory.MYPERFIL)
                  .build();

          initHeaders(context);
          initAppSections(context);
          initOthers(context);
          initSocial();
          elementsInit = true;
      }


    }

    private void initHeaders(Context context){

        headers.add(new MenuItem.Builder(context.getResources().getString(R.string.menuperfil))
                .setCategory_id(MenuItemCategory.MYPERFIL)
                .build());

        headers.add(new MenuItem.Builder(context.getResources().getString(R.string.menuheadersection))
                .setCategory_id(MenuItemCategory.APP_SECTIONS)
                .build());

        headers.add(new MenuItem.Builder(context.getResources().getString(R.string.menuheadersocial))
                .setCategory_id(MenuItemCategory.SOCIAL)
                .build());

        headers.add(new MenuItem.Builder(context.getResources().getString(R.string.menuheaderothers))
                .setCategory_id(MenuItemCategory.OTHERS)
                .build());
    }

    private void initAppSections(Context context){

        app_sections.add(new MenuItem.Builder(context.getResources().getString(R.string.menuprincipal))
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
                .setId(MenuSections.EVOLUTION)
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

        others.add(new MenuItem.Builder(context.getResources().getString(R.string.menuothersitem))
                .setId(MenuSections.ATTRIBUTIONS)
                .setCategory_id(MenuItemCategory.OTHERS)
                .setImageFromResource(R.drawable.ic_action_present)
                .build());

    }

    private void initSocial(){

        social.add(new MenuItem.Builder("Facebook")
                .setId(MenuSections.FACEBOOK)
                .setCategory_id(MenuItemCategory.SOCIAL)
                .setImageFromResource(R.drawable.ic_action_facebook)
                .build());


        social.add(new MenuItem.Builder("Twitter")
                .setId(MenuSections.TWITTER)
                .setCategory_id(MenuItemCategory.SOCIAL)
                .setImageFromResource(R.drawable.ic_action_twitter)
                .build());

        social.add(new MenuItem.Builder("Google +")
                .setId(MenuSections.GOOGLEPLUS)
                .setCategory_id(MenuItemCategory.SOCIAL)
                .setImageFromResource(R.drawable.ic_action_gplus)
                .build());

    }

    public ArrayList<MenuItem> getApp_sections() {
        return app_sections;
    }

    public ArrayList<MenuItem> getSocial() {
        return social;
    }

    public ArrayList<MenuItem> getOthers() {
        return others;
    }

    public ArrayList<MenuItem> getHeaders() {
        return headers;
    }

    public MenuItem getPerfil() {
        return perfil;
    }
}
