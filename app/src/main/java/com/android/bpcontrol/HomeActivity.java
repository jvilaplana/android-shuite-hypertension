package com.android.bpcontrol;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.bpcontrol.adapters.HomeGridAdapter;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.customViews.RobotoTextView;
import com.android.bpcontrol.model.GridCellResources;
import com.android.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 22/1/15.
 */
public class HomeActivity extends BPcontrolMasterActivity {

    private GridCellResources[] resources;
    private final int num_grid_resources = 4;

    private DrawerLayout dwlayoutmenu;
    private LinearLayout menulayout;
    private boolean menuIsOpen=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
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

        View cell = getLayoutInflater().inflate(R.layout.logomenucell, null);
        ImageView image = (ImageView) cell.findViewById(R.id.line_separator);
        menulayout.addView(cell);
        View cell1 = getLayoutInflater().inflate(R.layout.lateralmenucell, null);
        RobotoTextView text = (RobotoTextView)cell1.findViewById(R.id.menucelltextview);
        text.setText("Adrian Carrera");
        ImageView image2 = (ImageView) cell1.findViewById(R.id.menucellimage);
        image2.setBackgroundResource(R.drawable.ic_action_dialog);
        menulayout.addView(cell1);
        //menulayout.addView(image);

    }



}
