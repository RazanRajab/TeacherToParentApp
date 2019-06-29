package com.example.razan.teachertoparent_t2;

/**
 * Created by Razan on 4/6/2019.
 */

public class Message {
    private int mesaage_id;
    private int chat_id;
    private int sender_id;
    private String senderName;
    private String content;
    private String time;

    public Message(int chat_id, int sender_id, String content, String time) {
        this.chat_id = chat_id;
        this.sender_id = sender_id;
        this.content = content;
        this.time = time;
    }

    public int getMesaage_id() {
        return mesaage_id;
    }

    public void setMesaage_id(int mesaage_id) {
        this.mesaage_id = mesaage_id;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
