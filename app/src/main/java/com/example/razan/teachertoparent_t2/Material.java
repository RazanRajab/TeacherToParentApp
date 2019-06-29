package com.example.razan.teachertoparent_t2;

/**
 * Created by Razan on 3/27/2019.
 */

public class Material {
    private int material_id;
    private String materialName;
    private int classID;
    private String className;
    private int time;

    public Material(int id, String material_name, String class_name, int time, int classID) {
        this.material_id = id;
        this.materialName = material_name;
        this.className = class_name;
        this.time = time;
        this.classID = classID;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_name() {
        return materialName;
    }

    public void setMaterial_name(String material_name) {
        this.materialName = material_name;
    }

    public String getClass_name() {
        return className;
    }

    public void setClass_name(String class_name) {
        this.className = class_name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }
}
