package com.example.razan.teachertoparent_t2;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Razan on 4/2/2019.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("Tag","Notification :"+remoteMessage.getNotification().getTitle()+" "+remoteMessage.getNotification().getBody());
       /* Message m= new Message(ChatRoom.chat_id,ChatRoom.parentID,remoteMessage.getNotification().getBody(),
                remoteMessage.getSentTime()+"");
        Messages.add(m);
        LA.notifyDataSetChanged();*/
        String time= Calendar.getInstance().getTimeInMillis()+"";
        String time1=remoteMessage.getSentTime()+"";
        String t= new SimpleDateFormat("HH:mm:ss").format(new Date());

        sendMyBroadCast(remoteMessage.getNotification().getBody(), t);
    }
    private static void sendMessageToActivity(String message,String time) {
        Intent intent = new Intent("FirebaseMessage");
        // You can also include some extra data.
        intent.putExtra("message", message);
        intent.putExtra("time", time);
        //LocalBroadcastManager.getInstance().sendBroadcast(intent);
    }
    private void sendMyBroadCast(String message,String time)
    {
        try
        {
            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction("service.to.activity.transfer");
            broadCastIntent.putExtra("message", message);
            broadCastIntent.putExtra("time", time);

            sendBroadcast(broadCastIntent);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
