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
 * Created by Razan on 4/1/2019.
 */

public class ListStudentAdapter  extends ArrayAdapter {
    Context adapterContext;
    int adapterRecourse;
    ArrayList<Student> adapterMaterials;
    ListStudentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Student> objects) {
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
        TextView StudentName=(TextView)row.findViewById(R.id.material);
        TextView ParentName=(TextView)row.findViewById(R.id.class_id);
        TextView student_id=(TextView)row.findViewById(R.id.class_time);
        Student adv=adapterMaterials.get(position);
        StudentName.setText(adv.getName());
        ParentName.setText(adv.getParent_name());
        student_id.setText(adv.getId()+"");
        return row;
    }
}
