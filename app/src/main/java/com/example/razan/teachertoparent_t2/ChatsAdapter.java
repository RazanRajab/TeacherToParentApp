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

public class ChatsAdapter extends ArrayAdapter {
    Context adapterContext;
    int adapterRecourse;
    ArrayList<Chat> adapterMaterials;
    Session session;

    public ChatsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Chat> objects) {
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
        Chat chat = adapterMaterials.get(position);
        convertView = LayoutInflater.from(adapterContext).inflate(adapterRecourse, parent, false);


        TextView name = convertView.findViewById(R.id.name);
        TextView message = convertView.findViewById(R.id.message);

        name.setText(chat.getParent_name());
        message.setText(chat.getLast_message());

       /* int imageId=adapterContext.getResources().getIdentifier(adv.pic,"drawable",adapterContext.getPackageName());
        imJava.setImageResource(imageId);*/
        return convertView;
    }
}