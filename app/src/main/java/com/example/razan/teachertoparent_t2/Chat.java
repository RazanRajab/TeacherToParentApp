package com.example.razan.teachertoparent_t2;

/**
 * Created by Razan on 4/7/2019.
 */

public class Chat {
    private int chat_id;
    private int parent_id;
    private String parent_name;
    private String last_message;

    public Chat(int chat_id, int parent_id, String parent_name) {
        this.chat_id = chat_id;
        this.parent_id = parent_id;
        this.parent_name = parent_name;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
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

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }
}
