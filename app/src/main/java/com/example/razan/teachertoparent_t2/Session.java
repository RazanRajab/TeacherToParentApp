package com.example.razan.teachertoparent_t2;


import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    static SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }
    public void set_user_id(int id) {
        prefs.edit().putInt("id", id).commit();
    }

    public static int get_user_id() {
        int id = prefs.getInt("id",0);
        return id;
    }
    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

}
