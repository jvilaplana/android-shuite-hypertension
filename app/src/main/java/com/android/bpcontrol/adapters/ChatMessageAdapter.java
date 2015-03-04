package com.android.bpcontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.android.bpcontrol.R;
import com.android.bpcontrol.customviews.RobotoTextView;
import com.android.bpcontrol.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 03/03/2015.
 */
public class ChatMessageAdapter extends BaseAdapter {

    private List<Message> messages;
    private Context context;

    public ChatMessageAdapter(Context context,List<Message> messages) {

        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message) this.getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            if (message.isUser()) {
                convertView = inflater.inflate(R.layout.chatcelluser, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.chatcelldoctor, parent, false);
            }

            holder.message = (RobotoTextView) convertView.findViewById(R.id.messagetext);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.message.setText(message.getMessage());


        return convertView;
    }

    public static class ViewHolder{
        RobotoTextView message;

    }
}
