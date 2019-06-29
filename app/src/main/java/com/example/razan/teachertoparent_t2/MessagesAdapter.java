package com.example.razan.teachertoparent_t2;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Razan on 4/7/2019.
 */

public class MessagesAdapter extends ArrayAdapter {
    Context adapterContext;
    int adapterRecourse;
    ArrayList<Message> adapterMaterials;
    Session session;

    public MessagesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Message> objects) {
        super(context, resource, objects);
        adapterContext = context;
        adapterRecourse = resource;
        adapterMaterials = objects;
        session = new Session(context);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message m = adapterMaterials.get(position);
        if (m.getSender_id() == session.get_user_id()) {
            convertView = LayoutInflater.from(adapterContext).inflate(R.layout.my_message, parent, false);
        } else {
            convertView = LayoutInflater.from(adapterContext).inflate(R.layout.their_message, parent, false);
            TextView name= convertView.findViewById(R.id.name);
            name.setText(m.getSenderName());
        }
        TextView message = (TextView) convertView.findViewById(R.id.message_body);
        TextView time = (TextView) convertView.findViewById(R.id.time);

        message.setText(m.getContent());
        time.setText(m.getTime());
        return convertView;
    }
}
