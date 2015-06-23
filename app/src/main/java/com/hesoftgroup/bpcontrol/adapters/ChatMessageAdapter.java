package com.hesoftgroup.bpcontrol.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hesoftgroup.bpcontrol.R;
import com.hesoftgroup.bpcontrol.customviews.RobotoTextView;
import com.hesoftgroup.bpcontrol.model.Message;

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
        Message message = this.getItem(position);

    ViewHolder holder;
    if (convertView == null) {
        holder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.chatcell,null);
        holder.container = (LinearLayout) convertView.findViewById(R.id.containerchatmsg);
        holder.message = (RobotoTextView) convertView.findViewById(R.id.messagetext);
        holder.separator = (ImageView) convertView.findViewById(R.id.chatmsgseparator);
        holder.date = (RobotoTextView) convertView.findViewById(R.id.datemsg);
        convertView.setTag(holder);
    }else{
        holder = (ViewHolder) convertView.getTag();
    }
         holder.message.setText(message.getContent());
        if (message.isUser()) {
            RelativeLayout.LayoutParams paramsparent =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsparent.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsparent.setMargins((int) context.getResources().getDimension(R.dimen.chatparentmarginbig),0,(int) context.getResources().getDimension(R.dimen.chatparentmargin),0);
            holder.container.setLayoutParams(paramsparent);
            holder.container.setGravity(Gravity.RIGHT);
            holder.container.setPadding((int)context.getResources().getDimension(R.dimen.paddingchatlinear),
                    (int)context.getResources().getDimension(R.dimen.paddingchatlinear),
                    (int)context.getResources().getDimension(R.dimen.paddingmarginlinear),
                    (int)context.getResources().getDimension(R.dimen.paddingchatlinear));
            holder.container.setBackgroundResource(R.drawable.msg_out);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.separator.getLayoutParams();
            params.gravity = Gravity.RIGHT;
            params.leftMargin = (int)context.getResources().getDimension(R.dimen.imageviewseparatorchatleft);
            holder.separator.setLayoutParams(params);
            LinearLayout.LayoutParams paramsdate = (LinearLayout.LayoutParams) holder.date.getLayoutParams();
            paramsdate.gravity= Gravity.RIGHT;
            holder.date.setLayoutParams(paramsdate);
        } else {
            RelativeLayout.LayoutParams paramsparent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsparent.setMargins((int) context.getResources().getDimension(R.dimen.chatparentmargin),0,(int) context.getResources().getDimension(R.dimen.chatparentmarginbig),0);
            holder.container.setLayoutParams(paramsparent);
            holder.container.setGravity(Gravity.LEFT);
            holder.container.setPadding((int)context.getResources().getDimension(R.dimen.paddingmarginlinear),
                    (int)context.getResources().getDimension(R.dimen.paddingchatlinear),
                    (int)context.getResources().getDimension(R.dimen.paddingchatlinear),
                    (int)context.getResources().getDimension(R.dimen.paddingchatlinear));
            holder.container.setBackgroundResource(R.drawable.msg_in);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.separator.getLayoutParams();
            params.gravity = Gravity.LEFT;
            params.rightMargin = (int)context.getResources().getDimension(R.dimen.imageviewseparatorchatleft);
            holder.separator.setLayoutParams(params);
            LinearLayout.LayoutParams paramsdate = (LinearLayout.LayoutParams) holder.date.getLayoutParams();
            paramsdate.gravity= Gravity.LEFT;
            holder.date.setLayoutParams(paramsdate);

        }
        holder.date.setGravity(Gravity.BOTTOM);
        holder.date.setText(message.getDate());
        return convertView;
    }

    public static class ViewHolder{
        LinearLayout container;
        RobotoTextView message;
        ImageView separator;
        RobotoTextView date;

    }

    public void setList(List<Message> list){

        this.messages = list;
    }
}
