package com.android.bpcontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.bpcontrol.application.BPcontrolApplication;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.controllers.HomeFragmentManager;
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.customViews.RobotoTextView;
import com.android.bpcontrol.fragments.HomeFragment;
import com.android.bpcontrol.model.MenuItem;
import com.android.bpcontrol.model.User;
import com.android.bpcontrol.webservice.WSManager;

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

        ImageView image = (ImageView) cell.findViewById(R.id.prueba2);
        getApplicationContext().loadPerfilImageView(User.getInstance().getUUID(),image);
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
                if(!isFacebookAppInstalled()){
                    connectToSocialNetwork("https://www.facebook.com/");
                }else{
                    connectToApp("fb://profile/107718629298104");
                }
                break;
            case TWITTER:
                connectToSocialNetwork("https://twitter.com/elgourmet");
                break;
            case GOOGLEPLUS:
                connectToSocialNetwork("https://plus.google.com/+elgourmet/posts");
                break;
            case ATTRIBUTIONS:
                break;
            default:
                break;

        }

        dwlayoutmenu.closeDrawer(menulayout);
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
            
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.exitHome));
            builder.setPositiveButton(getResources().getString(R.string.exitokbutton), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.exitCancelButton,new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {


                }


            });



        }

        Fragment fragment = new HomeFragment();

        loadFragment(fragment, false, false);

    }


    public void connectToSocialNetwork(final String url){

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public boolean isFacebookAppInstalled(){

        try{
            getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            return true;
        }catch( PackageManager.NameNotFoundException e ){
            return false;
        }
    }

    public void connectToApp(final String uri){

        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(uri)));
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

