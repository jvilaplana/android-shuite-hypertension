package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.ChatMessageAdapter;
import com.android.bpcontrol.customviews.BPEditText;
import com.android.bpcontrol.model.Message;

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

    public static ChatMessagesFragment getNewInstace(){

        ChatMessagesFragment chatMessagesFragment = new ChatMessagesFragment();
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


       messages= new ArrayList<>();

        Message ms1 = new Message();
        ms1.setUser(true);
        ms1.setMessage("Hola, que tal?");
        Message ms2 = new Message();
        ms2.setUser(false);
        ms2.setMessage("Bien, y tu?");
        Message ms3 = new Message();
        ms3.setUser(false);
        ms3.setMessage("Como ha ido el verano?");
        Message ms4 = new Message();
        ms4.setUser(true);
        ms4.setMessage("Bien bien, y a ti que tal?");
        Message ms5 = new Message();
        ms5.setMessage("Guay tio");
        ms5.setUser(false);

        messages.add(ms1);
        messages.add(ms2);
        messages.add(ms3);
        messages.add(ms4);
        messages.add(ms5);


        Message ms6 = new Message();
        ms6.setUser(true);
        ms6.setMessage("Hola, que tal?");
        Message ms7 = new Message();
        ms7.setUser(false);
        ms7.setMessage("Bien, y tu?");
        Message ms8 = new Message();
        ms8.setUser(false);
        ms8.setMessage("Como ha ido el verano?");
        Message ms9 = new Message();
        ms9.setUser(true);
        ms9.setMessage("Bien bien, y a ti que tal?");
        Message ms10 = new Message();
        ms10.setMessage("Guay tio");
        ms10.setUser(false);

        messages.add(ms6);
        messages.add(ms7);
        messages.add(ms8);
        messages.add(ms9);
        messages.add(ms10);

        final ChatMessageAdapter adapter = new ChatMessageAdapter(getActivity(), messages);

        list.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editText.getText().toString().equals("")) {
                    Message newmessage = new Message();
                    newmessage.setUser(true);
                    newmessage.setMessage(editText.getText().toString());
                    messages.add(newmessage);
                    editText.setText(null);
                    adapter.notifyDataSetChanged();
                    list.setSelection(messages.size()-1);
                }
            }
        });

        list.setSelection(messages.size()-1);
    }
}
