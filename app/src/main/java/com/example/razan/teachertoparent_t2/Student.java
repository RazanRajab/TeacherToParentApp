package com.example.razan.teachertoparent_t2;

/**
 * Created by Razan on 4/1/2019.
 */

public class Student {
    private int id;
    private String name;
    private int class_id;
    private int parent_id;
    private  String parent_name;

    public Student(int id, String name, int class_id, int parent_id, String parent_name) {
        this.id = id;
        this.name = name;
        this.class_id = class_id;
        this.parent_id = parent_id;
        this.parent_name = parent_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }
}
