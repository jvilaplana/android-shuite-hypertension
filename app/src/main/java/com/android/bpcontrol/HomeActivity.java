package com.android.bpcontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.bpcontrol.adapters.ViewPagerAdapter;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.controllers.HomeFragmentManager;
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.fragments.HomeFragment;
import com.android.bpcontrol.fragments.PerfilFragment;
import com.android.bpcontrol.fragments.PressuresFragment;
import com.android.bpcontrol.model.MenuItem;
import com.android.bpcontrol.model.User;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.bpcontrol.webservice.WSManager;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Adrian Carrera on 22/1/15.
 */
public class HomeActivity extends BPcontrolMasterActivity{

    private DrawerLayout dwlayoutmenu;
    private LinearLayout menulayout;
    private LinearLayout menuItemsLayout;
    private boolean menuIsOpen=false;

    private RobotoTextView headertext;
    private RobotoTextView perfilName;

    private FrameLayout frameLayout;
    private LinearLayout viewpager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);


        dwlayoutmenu = (DrawerLayout) findViewById(R.id.menuDrawer);
        dwlayoutmenu.setDrawerListener(new LateralMenuListeners());
        menulayout = (LinearLayout) findViewById(R.id.menuinclude);
        frameLayout =(FrameLayout) findViewById(R.id.menu_frame);
        viewpager = (LinearLayout) findViewById(R.id.viewpager);

        LateralMenuController.getInstance().initItems(this);

        if (isNetworkAvailable()){
            configureViewAndGetData();

        }else{

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.noconnectiondialog));
            builder.setPositiveButton(getResources().getString(R.string.noconnectiondialogpositive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (isNetworkAvailable()){
                        configureViewAndGetData();

                    }else{
                        finish();
                    }

                }
            });
            builder.setNegativeButton(getResources().getString(R.string.noconnectiondialognegative),new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });

            builder.show();


        }
    }

    private void configureViewAndGetData(){

        configureLateralMenu();
        if (!User.getInstance().isInSession()) {
            getUserInfo();
        }
        configureActionBar();
        selectMenuItem(LateralMenuController.MenuSections.HOME);

    }

    private void configureActionBar(){

        headertext = ((RobotoTextView) getActionBarView().findViewById(R.id.textviewbpcontrol));

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
        ImageView image = (ImageView) cell.findViewById(R.id.perfilImage);

        final SharedPreferences preferences = getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY,MODE_PRIVATE);
        String user_uuid = preferences.getString(SharedPreferenceConstants.USERUUID,"");
        getApplicationContext().loadPerfilImageView(user_uuid,image);

        perfilName = ((RobotoTextView) cell.findViewById(R.id.user_name));

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMenuItem(LateralMenuController.MenuSections.MYPERFIL);
            }
        });

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

        viewpager.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);

        switch (type){

            case MYPERFIL:
                headertext.setText(getResources().getString(R.string.perfilheaderbar).toUpperCase());
                PerfilFragment perfilFragment = PerfilFragment.getNewInstance();
                loadFragment(perfilFragment,false,false);
                break;
            case PRESSURES:
                headertext.setText(getResources().getString(R.string.headerpressures).toUpperCase());
                PressuresFragment pressuresFragment = PressuresFragment.getNewInstace();
                loadFragment(pressuresFragment,false,false);
                break;
            case EVOLUTION:
                break;
            case HISTORIAL:
                break;
            case MESSAGES:
                break;
            case VIDEOS:
               startActivity(new Intent(this, YoutubeActivity.class));
                break;
            case HEALTHCENTERS:
                break;
            case CONTACT:
                frameLayout.setVisibility(View.GONE);
                viewpager.setVisibility(View.VISIBLE);
                FragmentPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                ViewPager pager = (ViewPager)findViewById(R.id.pager);
                headertext.setText(getResources().getString(R.string.menusection_contact).toUpperCase());
                pager.setAdapter(adapter);
                CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.pagerindicator);
               	indicator.setViewPager(pager);
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
                HomeFragment homeFragment = HomeFragment.newInstance();
                loadFragment(homeFragment, false, false);
                headertext.setText(getResources().getString(R.string.principalmenutext).toUpperCase());
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

                transaction.setCustomAnimations(R.anim.slide_out_down,
                        R.anim.fade_in);
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

        if (viewpager.getVisibility() == View.VISIBLE){

            viewpager.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);

        }
        else if (lastFragment instanceof HomeFragment) {
            
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.exitHome));
            builder.setPositiveButton(getResources().getString(R.string.exitokbutton), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HomeFragmentManager.getInstance(HomeActivity.this).getHomeFragmentStack().clear();
                    finish();

                }
            });
            builder.setNegativeButton(getResources().getString(R.string.exitCancelButton),new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();

            return;
        }

        Fragment fragment = new HomeFragment();
        headertext.setText(getResources().getString(R.string.principalmenutext).toUpperCase());
        loadFragment(fragment, true, false);

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

    @Override
    public void onBackPressed(){

        goBack();

    }

   private void getUserInfo(){

       showProgressDialog();
       new AsyncTask<Void, Void, Void>() {

           protected Void doInBackground(Void... params ) {

               final SharedPreferences preferences = getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY,MODE_PRIVATE);
               String user_uuid = preferences.getString(SharedPreferenceConstants.USERUUID,"");
               WSManager.getInstance().getUserInfo(HomeActivity.this,user_uuid,new WSManager.GetUserInfoWithUUID() {
                   @Override
                   public void onUserInfoObtained() {

                       perfilName.setText(User.getInstance().getName()+" "+User.getInstance().getFirstSurname());
                       HomeActivity.this.dissmissProgressDialog();

                   }
               });
               User.getInstance().setInSession(true);
               return null;
           }
       }.execute();


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