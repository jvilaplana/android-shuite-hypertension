package com.android.bpcontrol;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.bpcontrol.adapters.HomeGridAdapter;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 22/1/15.
 */
public class HomeActivity extends BPcontrolMasterActivity {

    private final int[] gridelements = new int[]{R.drawable.graphic_background,R.drawable.graphic_background,
            R.drawable.graphic_background,R.drawable.graphic_background};
    private final int numgridelemets = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
//        showProgressDialog();
//        Handler handler = new Handler();
//        showProgressDialog();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                dissmissProgressDialog();
//            }
//        },4000);
        GridView grid = (GridView) findViewById(R.id.homegridview);
        ViewGroup.LayoutParams layoutParams = grid.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
       // layoutParams.width = size.x;
       // layoutParams.height = size.x;
       // grid.setLayoutParams(layoutParams);
        grid.setBackgroundColor(Color.RED);
        grid.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }

        });
        HomeGridAdapter adapter = new HomeGridAdapter(this,gridelements);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                LogBP.writelog("Tocado el de la posicion " + position);
            }
        });

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
