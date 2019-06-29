package com.example.razan.teachertoparent_t2;

/**
 * Created by Razan on 3/24/2019.
 */

public class User {
    private String userName;
    private String Pass;
    private int role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
