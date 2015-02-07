package com.android.bpcontrol.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.HomeGridAdapter;
import com.android.bpcontrol.model.GridCellResources;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.webservice.WSManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.WriteAbortedException;
import java.nio.charset.Charset;
import java.text.BreakIterator;

/**
 * Created by Adrian Carrera on 29/01/2015.
 */
public class HomeFragment extends Fragment {

    private GridCellResources[] resources;
    private final int num_grid_resources = 4;
    private GridView grid;
    private ImageView imageView;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.homefragment, null);
        grid = (GridView) view.findViewById(R.id.homegridview);
        imageView = (ImageView) view.findViewById(R.id.prueba);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //new getUserImage().execute();
        final String url = "http://app2.hesoftgroup.eu/hypertensionPatient/restDownloadProfileImage/a5683026-0f3b-4ea5-a129-0aec2c36c1eb";

        initGridCellResources();
        HomeGridAdapter adapter = new HomeGridAdapter(getActivity(),resources);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                LogBP.writelog("Tocado el de la posicion " + position);
            }
        });
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

    private class getUserImage extends AsyncTask<Void,Void,Bitmap>{


        @Override
        protected Bitmap doInBackground(Void... params) {

            try {
                return WSManager.getInstance().getUserImage();


            }catch (Exception ex){

                LogBP.printStackTrace(ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){

            if (bitmap != null) {

                LogBP.writelog("BITMAP");
                imageView.setImageBitmap(bitmap);

            }

        }



    }


}
