package com.android.bpcontrol;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.bpcontrol.adapters.HomeGridAdapter;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.customViews.RobotoTextView;
import com.android.bpcontrol.model.GridCellResources;
import com.android.bpcontrol.model.MenuItem;
import com.android.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 22/1/15.
 */
public class HomeActivity extends BPcontrolMasterActivity {

    private GridCellResources[] resources;
    private final int num_grid_resources = 4;

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

        initGridCellResources();
        GridView grid = (GridView) findViewById(R.id.homegridview);
        HomeGridAdapter adapter = new HomeGridAdapter(this,resources);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                LogBP.writelog("Tocado el de la posicion " + position);
            }
        });
        dwlayoutmenu = (DrawerLayout) findViewById(R.id.menuDrawer);
        menulayout = (LinearLayout) findViewById(R.id.menuinclude);

        LateralMenuController.getInstance().initItems(this);
        configureLateralMenu();
        configureActionBar();


    }

    private void initGridCellResources(){

         final int[] background_resources = new int[]{R.drawable.graphic_background,R.drawable.presiones_background,
                R.drawable.message_background,R.drawable.video_background};

         final int[] icon_resources = new int[]{ R.drawable.home_graphic_icon,R.drawable.presiones_icon,
                                                 R.drawable.conversation_icon, R.drawable.video_icon};

         final int[] text_resources = new int[]{R.string.homegrid_graphics,R.string.homegrid_pressure,
                                                   R.string.homegrid_messages,R.string.homegrid_videos};

         resources = new GridCellResources[num_grid_resources];

         for (int i=0;i<num_grid_resources;i++){

             resources[i] = new GridCellResources(background_resources[i],icon_resources[i],text_resources[i]);

         }
    }
    private void configureActionBar(){

        final ImageButton menu = (ImageButton) getActionBarView().findViewById(R.id.actionBarMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIsOpen){
                    dwlayoutmenu.closeDrawer(menulayout);
                    menuIsOpen = false;
                }else{
                    dwlayoutmenu.openDrawer(menulayout);
                    menuIsOpen = true;
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
            switch(item.getId()){

                case PRINCIPAL:

                    break;
                case PRESSURES:
                    break;

            }
        }
    }

    private void configureMenuSocial(){

        MenuItem item;
        for (int i=0;i< LateralMenuController.getInstance().getSocial().size();i++){

            item = LateralMenuController.getInstance().getSocial().get(i);
            configCell(item,i == LateralMenuController.getInstance().getSocial().size() - 1 );

            switch(item.getId()){

                case PRINCIPAL:

                    break;
                case PRESSURES:
                    break;

            }
        }

    }

    private void configureMenuOthers(){

        MenuItem item;
        for (int i=0;i<LateralMenuController.getInstance().getOthers().size();i++){

            item = LateralMenuController.getInstance().getOthers().get(i);
            configCell(item,false);

        }

    }

    private void configCell(MenuItem item, boolean isLast){

        View cell = getLayoutInflater().inflate(R.layout.lateralmenucell, null);
        ((RobotoTextView)cell.findViewById(R.id.menucelltextview)).setText(item.getTextView());
        ((ImageView)cell.findViewById(R.id.menucellimage)).setBackgroundResource(item.getImageid());
        if (isLast) cell.findViewById(R.id.image_separator).setVisibility(View.INVISIBLE);
        menuItemsLayout.addView(cell);
    }




}
