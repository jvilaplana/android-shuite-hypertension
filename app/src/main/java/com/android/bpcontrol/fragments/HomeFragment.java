package com.android.bpcontrol.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.databases.DataStore;
import com.android.bpcontrol.model.GridCellResources;
import com.android.bpcontrol.model.Message;
import com.android.bpcontrol.model.User;
import com.android.bpcontrol.utils.DateUtils;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.bpcontrol.webservice.WSManager;
import com.readystatesoftware.viewbadger.BadgeView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian Carrera on 29/01/2015.
 */
public class HomeFragment extends Fragment {

    private GridCellResources[] resources;
    private final int num_grid_resources = 4;
    private GridView grid;
    private RobotoTextView welcome;
    private RobotoTextView description;
    private ImageView semaphore;
    private HomeGridAdapter adapter;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.homefragment, null);
        welcome = (RobotoTextView) view.findViewById(R.id.welcometext);
        semaphore = (ImageView) view.findViewById(R.id.homesemaphore);
        description = (RobotoTextView) view.findViewById(R.id.homedescriptiontext);
        grid = (GridView) view.findViewById(R.id.homegridview);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initGridCellResources();



        SharedPreferences preferences = getActivity().getSharedPreferences
                (SharedPreferenceConstants.SHARE_PREFERENCE_KEY,Context.MODE_PRIVATE);
        String lastUpdated = preferences.getString(SharedPreferenceConstants.LASTSENDPRESSURE,"");

        welcome.setText(getResources().getString(R.string.welcome)+" "+User.getInstance()
                .getName()+ " "+User.getInstance().getFirstSurname()+"!");
        semaphore.setBackgroundResource(R.drawable.semafor_green);
        if (!lastUpdated.equals("")){

            Date lastdate = null;
            try {
                lastdate = DateUtils.stringToDate(lastUpdated, DateUtils.DEFAULT_FORMAT);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int days = -1;
            if (lastdate != null) {
                days = DateUtils.differenceInDays(new Date(), lastdate);
            }

            if (days == -1) {
                description.setText(getResources().getString(R.string.notregister));
            }else if (days == 0){
                description.setText(getResources().getString(R.string.homedescription2));
            }else{

                if (days == 1){

                    description.setText(getResources().getString(R.string.homedescription1)+ " "+""+days+" "
                            +getResources().getString(R.string.singularday)+" "+getResources()
                            .getString(R.string.homedescription12));
                }else{
                    description.setText(getResources().getString(R.string.homedescription1)+ " "+""+days+" "
                            +getResources().getString(R.string.pluralday)+" "+getResources()
                            .getString(R.string.homedescription12));
                    if (days>7){

                        semaphore.setBackgroundResource(R.drawable.semafor_yellow);
                    }else if (days>30){

                        semaphore.setBackgroundResource(R.drawable.semafor_red);
                    }


                }


            }
        }else {
            description.setText(getResources().getString(R.string.notregister));
        }

        adapter = new HomeGridAdapter(getActivity(),resources);
        grid.setAdapter(adapter);
        new getMessages().execute();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {

                switch (position){

                    case 0:
                        ((HomeActivity)getActivity()).selectMenuItem(LateralMenuController.MenuSections.EVOLUTION);
                        break;
                    case 1:
                        ((HomeActivity)getActivity()).selectMenuItem(LateralMenuController.MenuSections.PRESSURES);
                        break;
                    case 2:
                        ((HomeActivity)getActivity()).selectMenuItem(LateralMenuController.MenuSections.MESSAGES);
                        break;
                    case 3:
                        ((HomeActivity)getActivity()).selectMenuItem(LateralMenuController.MenuSections.VIDEOS);
                        break;

                }
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
    private class getMessages extends AsyncTask<Void,List<Message>,Void> {

        @Override
        public Void doInBackground(Void... params) {

            WSManager.getInstance().getUserMessagesChat(getActivity(),null,new WSManager.GetMessages() {
                @Override
                public void onUserMessagesReceived(List<Message> listmenssages) {
                    publishProgress(listmenssages);
                }
            });
            return null;
        }
        @Override
        public void onProgressUpdate(List<Message>... result){
            try {
                BadgeView badgeView = adapter.getBadgeRef();
                if (badgeView != null) {
                    int unreadMessages = DataStore.getInstance().getUnreadMessages();
                    if (unreadMessages > 0){
                        badgeView.setText(unreadMessages+"");
                        badgeView.setVisibility(View.VISIBLE);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    }

