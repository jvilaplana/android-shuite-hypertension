package com.android.bpcontrol;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.bpcontrol.adapters.HomeGridAdapter;
import com.android.bpcontrol.application.BPcontrolMasterActivity;

/**
 * Created by Adrian on 22/1/15.
 */
public class HomeActivity extends BPcontrolMasterActivity {

    private final int[] gridelements = new int[]{R.drawable.graphics_image,R.drawable.graphics_image,
                                                 R.drawable.graphics_image,R.drawable.graphics_image};
    private final int numgridelemets = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        showProgressDialog();
        Handler handler = new Handler();
//        showProgressDialog();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                dissmissProgressDialog();
//            }
//        },4000);
        GridView grid = (GridView) findViewById(R.id.homegridview);
//        ViewGroup.LayoutParams layoutParams = grid.getLayoutParams();
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//       layoutParams.height = size.y/2;
//        grid.setLayoutParams(layoutParams);
        HomeGridAdapter adapter = new HomeGridAdapter(this,gridelements);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                System.out.println("Tocado el de la posicion "+position);
            }
        });






    }

}
