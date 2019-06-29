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
 * Created by Razan on 4/23/2017.
 */

public class ListAdapter extends ArrayAdapter {
    Context adapterContext;
    int adapterRecourse;
    ArrayList<Material> adapterMaterials;
     ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Material> objects) {
        super(context, resource, objects);
        adapterContext=context;
        adapterRecourse=resource;
        adapterMaterials=objects;
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        LayoutInflater rowInflater= LayoutInflater.from(adapterContext);
        row=rowInflater.inflate(adapterRecourse,parent,false);
        TextView MaterialName=(TextView)row.findViewById(R.id.material);
        TextView ClassName=(TextView)row.findViewById(R.id.class_id);
        TextView ClassTime=(TextView)row.findViewById(R.id.class_time);
        Material m=adapterMaterials.get(position);
        MaterialName.setText(m.getMaterial_name());
        ClassName.setText(m.getClass_name());
        ClassTime.setText(m.getTime()+"");
        return row;
    }
}
