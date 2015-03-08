package com.android.bpcontrol.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.ChatMessageAdapter;
import com.android.bpcontrol.model.Message;
import com.android.bpcontrol.test.DatabasePlotMock;
import com.android.bpcontrol.webservice.WSManager;
import com.android.bpcontrol.customviews.BPEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 03/03/2015.
 */


public class ChatMessagesFragment extends Fragment{

    private ListView list;
    private Button button;
    private BPEditText editText;
    private List<Message> messages;
    private  ChatMessageAdapter adapter;

    private ImageButton secondbarbutton;

    public static ChatMessagesFragment getNewInstace(final Context context){

        final ImageButton barbutton =((HomeActivity)context).getSecondActionBarButton();
        barbutton.setImageResource(R.drawable.updateselector);
        barbutton.setVisibility(View.VISIBLE);
        barbutton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ChatMessagesFragment chatMessagesFragment = new ChatMessagesFragment().setSecondBarButton(barbutton);
        return chatMessagesFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.chatlayoutfragment,null);
        list = (ListView) view.findViewById(R.id.listviewmessages);
        button = (Button) view.findViewById(R.id.enviarbutton);
        editText = (BPEditText)view.findViewById(R.id.message);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // messages = DatabasePlotMock.getFakeMessages();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editText.getText().toString().equals("")) {
                    Message newmessage = new Message();
                    newmessage.setUser(true);
                    newmessage.setContent(editText.getText().toString());
                    new sendMessage().execute(newmessage);

                }
            }
        });

        secondbarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new getMessages().execute();
            }
        });

       new getMessages().execute();
    }

    private class getMessages extends AsyncTask<Void,List<Message>,Void> {


        @Override
        public void onPreExecute(){

            ((HomeActivity)getActivity()).showProgressDialog();
        }
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

            messages = result[0];
            configureView();
            ((HomeActivity)getActivity()).dissmissProgressDialog();

        }
    }

    private class sendMessage extends AsyncTask<Message,List<Message>,Void> {


        @Override
        public void onPreExecute(){

            ((HomeActivity)getActivity()).showProgressDialog();
        }
        @Override
        public Void doInBackground(Message... params) {
            Message msg = params[0];
            WSManager.getInstance().sendMessage(getActivity(),params[0]);
            return null;
        }
        @Override
        public void onProgressUpdate(List<Message>... result){

            //messages = result[0];
            configureViewSendMessage();
            ((HomeActivity)getActivity()).dissmissProgressDialog();
        }
    }

    private void configureView(){

        adapter = new ChatMessageAdapter(getActivity(),messages);
        list.setAdapter(adapter);
        list.setSelection(messages.size()-1);
    }

    private void configureViewSendMessage(){
        editText.setText(null);
        adapter.setList(messages);
        adapter.notifyDataSetChanged();
        list.setSelection(messages.size()-1);
    }
    public ChatMessagesFragment setSecondBarButton(ImageButton button){

        this.secondbarbutton = button;
        return this;
    }
}
