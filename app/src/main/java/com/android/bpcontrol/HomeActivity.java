package com.android.bpcontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.controllers.HomeFragmentManager;
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.customViews.RobotoTextView;
import com.android.bpcontrol.fragments.HomeFragment;
import com.android.bpcontrol.model.MenuItem;

/**
 * Created by Adrian Carrera on 22/1/15.
 */
public class HomeActivity extends BPcontrolMasterActivity {

    private DrawerLayout dwlayoutmenu;
    private LinearLayout menulayout;
    private LinearLayout menuItemsLayout;
    private boolean menuIsOpen=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ((RobotoTextView)getActionBarView().findViewById(R.id.textviewbpcontrol))
                                           .setText(getResources().getString(R.string.principalmenutext).toUpperCase());


        dwlayoutmenu = (DrawerLayout) findViewById(R.id.menuDrawer);
        dwlayoutmenu.setDrawerListener(new LateralMenuListeners());
        menulayout = (LinearLayout) findViewById(R.id.menuinclude);

        LateralMenuController.getInstance().initItems(this);
        configureLateralMenu();
        configureActionBar();

        HomeFragment homeFragment = HomeFragment.newInstance();
        loadFragment(homeFragment, false, false);


    }

    private void configureActionBar(){

        final ImageButton menu = (ImageButton) getActionBarView().findViewById(R.id.actionBarMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIsOpen){
                    dwlayoutmenu.closeDrawer(menulayout);

                }else{
                    dwlayoutmenu.openDrawer(menulayout);

                }
            }
        });
    }



    private void configureLateralMenu() {

        menuItemsLayout = (LinearLayout) findViewById(R.id.layoutsections);
        menuItemsLayout.removeAllViews();
        View cell = getLayoutInflater().inflate(R.layout.logomenucell, null);
        menuItemsLayout.addView(cell);

        for(MenuItem item : LateralMenuController.getInstance().getHeaders()){

            switch (item.getCategory_id()){

                case MYPERFIL:
                    addHeaderView(item.getTextView().toUpperCase());
                    configureMenuPerfil();
                    break;

                case APP_SECTIONS:
                    addHeaderView(item.getTextView().toUpperCase());
                    configureMenuSections();
                    break;

                case SOCIAL:
                    addHeaderView(item.getTextView().toUpperCase());
                    configureMenuSocial();
                    break;

                case OTHERS:
                    addHeaderView(item.getTextView().toUpperCase());
                    configureMenuOthers();
                    break;

                default:
                    break;
            }
        }
    }

    private void addHeaderView(String text){

        View cell = getLayoutInflater().inflate(R.layout.headerinmenu, null);
        ((RobotoTextView)cell.findViewById(R.id.textviewheader)).setText(text);
        menuItemsLayout.addView(cell);

    }

    private void configureMenuPerfil(){

        View cell = getLayoutInflater().inflate(R.layout.perfilmenulayout, null);
        MenuItem item = LateralMenuController.getInstance().getPerfil();
        ((RobotoTextView) cell.findViewById(R.id.user_name)).setText(item.getTextView());
        menuItemsLayout.addView(cell);

    }


    private void configureMenuSections(){

        MenuItem item;
        for (int i = 0;i<LateralMenuController.getInstance().getApp_sections().size();i++){

            item = LateralMenuController.getInstance().getApp_sections().get(i);
            configCell(item,i == LateralMenuController.getInstance().getApp_sections().size() - 1);

        }
    }

    private void configureMenuSocial() {

        MenuItem item;
        for (int i = 0; i < LateralMenuController.getInstance().getSocial().size(); i++) {

            item = LateralMenuController.getInstance().getSocial().get(i);
            configCell(item, i == LateralMenuController.getInstance().getSocial().size() - 1);

        }
    }
    private void configureMenuOthers(){

        MenuItem item;
        for (int i=0;i<LateralMenuController.getInstance().getOthers().size();i++){

            item = LateralMenuController.getInstance().getOthers().get(i);
            configCell(item,false);

        }

    }

    private void configCell(final MenuItem item, boolean isLast){

        View cell = getLayoutInflater().inflate(R.layout.lateralmenucell, null);
        ((RobotoTextView)cell.findViewById(R.id.menucelltextview)).setText(item.getTextView());
        ((ImageView)cell.findViewById(R.id.menucellimage)).setBackgroundResource(item.getImageid());
        if (isLast) cell.findViewById(R.id.image_separator).setVisibility(View.INVISIBLE);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenuItem(item.getId());
            }
        });
        menuItemsLayout.addView(cell);
    }

    public void selectMenuItem(LateralMenuController.MenuSections type){

        switch (type){

            case MYPERFIL:
                break;
            case PRINCIPAL:
                break;
            case PRESSURES:
                break;
            case EVOLUTION:
                break;
            case HISTORIAL:
                break;
            case MESSAGES:
                break;
            case VIDEOS:
                break;
            case HEALTHCENTERS:
                break;
            case CONTACT:
                break;
            case HELP:
                break;
            case FACEBOOK:
                break;
            case TWITTER:
                break;
            case GOOGLEPLUS:
                break;
            case ATTRIBUTIONS:
                break;
            default:
                break;

        }
    }

    private void loadFragment(Fragment fragment,boolean back, boolean display){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int id = R.id.menu_frame;

        if(display){

            if(back){

                transaction.setCustomAnimations(R.anim.fade_in,
                        R.anim.slide_out_down);
            }else{
                transaction.setCustomAnimations(R.anim.slide_in_up,
                        R.anim.fade_out);
            }
        }
        if (fragment instanceof HomeFragment){
            while (HomeFragmentManager.getInstance(this).getHomeFragmentStack().size() > 1){
                HomeFragmentManager.getInstance(this).getHomeFragmentStack().pop();
            }
            HomeFragmentManager.getInstance(this).setHomeFragment(fragment);
        }
        Fragment lastFragment = this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        if (!back && lastFragment!=null)
            HomeFragmentManager.getInstance(this).getHomeFragmentStack().push(lastFragment);

        if (manager.findFragmentById(id) == null) {
            transaction.add(id, fragment);
            transaction.commit();
        } else {
            transaction.replace(id, fragment);
            transaction.commit();
        }
    }

    public void goBack(){
        Fragment lastFragment = this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        if (lastFragment instanceof HomeFragment) {
            finish();
            return;
        }
        if (HomeFragmentManager.getInstance(this).getHomeFragmentStack().isEmpty()){
            finish();
            return;
        }
        Fragment fragment = HomeFragmentManager.getInstance(this).getHomeFragmentStack().pop();
        if (fragment == null){
            fragment = new HomeFragment();
        }

        loadFragment(fragment, true, true);

    }


   private class LateralMenuListeners implements DrawerLayout.DrawerListener{

       @Override
       public void onDrawerSlide(View drawerView, float slideOffset) {}

       @Override
       public void onDrawerOpened(View drawerView) {
           menuIsOpen = true;
       }

       @Override
       public void onDrawerClosed(View drawerView) {
           menuIsOpen = false;
       }

       @Override
       public void onDrawerStateChanged(int newState) { }
   }
    }

