package com.example.razan.teachertoparent_t2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;

public class ChatRoom extends AppCompatActivity {

    private ListView myList;
    private EditText editText;
    public static ArrayList<Message> Messages;
    private Session session;
    private static int parentID;
    private static String parentName;
    private static int chat_id;
    public static MessagesAdapter LA;
    MyBroadCastReceiver myBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        myList = findViewById(R.id.messages_view);
        editText= findViewById(R.id.editText);
        Messages = new ArrayList<>();
        session =new Session(this);
        Bundle bd = getIntent().getExtras();
        if(bd != null)
        {
            chat_id=bd.getInt("chat_id");
            getChatHistory(chat_id);
            parentID = bd.getInt("parent_id");
            parentName=bd.getString("name");
        }

        myBroadCastReceiver = new MyBroadCastReceiver();
        registerMyReceiver();
    }


    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    public void sendMessage(View view) {
        Message m= new Message(chat_id,session.get_user_id(),editText.getText().toString(),getTime());
        Send(m);
    }

    private void getChatHistory(final int id) {
        // Tag used to cancel the request
        String tag_string_req = "req_chat_history";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Chat_History, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "history Response: " + response.toString());

                try {
                    JSONObject jObject = new JSONObject(response);
                    JSONArray jArray = jObject.getJSONArray("messages");

                    System.out.println(jObject.toString());
                    boolean error = jObject.getBoolean("error");
                    // Check for error node in json
                    if (!error) {

                        for(int i=0;i<jArray.length();i++) {

                            JSONObject s = jArray.getJSONObject(i);//name of object returned from database
                            String content = s.getString("content"); //same names of json fields
                            int chat_id = s.getInt("chat_id");
                            int sender_id = s.getInt("sender_id");
                            String time = s.getString("send_time");
                            System.err.print("chat_id:" + chat_id + " +content:" + content + " +sender:" + sender_id + " +time:" + time);
                            Message m=  new Message(chat_id,sender_id,content,time);
                            m.setSenderName(parentName);
                            Messages.add(m);
                        }
                        LA = new MessagesAdapter(getApplicationContext(), R.layout.my_message, Messages);
                        myList.setAdapter(LA);
                        myList.setSelection(LA.getCount() - 1);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg+": response", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage()+"", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //   Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage()+"", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("chat_id", id+"");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void Send(final Message m) {
        // Tag used to cancel the request
        String tag_string_req = "req_chat_send";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Chat_Send, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "send Response: " + response.toString());

                try {
                    JSONObject jObject = new JSONObject(response);

                    System.out.println(jObject.toString());
                    boolean error = jObject.getBoolean("error");
                    // Check for error node in json
                    if (!error) {

                        editText.setText("");
                        Messages.add(m);
                        LA.notifyDataSetChanged();
                        myList.setSelection(LA.getCount() - 1);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg+": response", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage()+"", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //   Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage()+"", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("chat_id", m.getChat_id()+"");
                params.put("sender_id", m.getSender_id()+"");
                params.put("content", m.getContent()+"");
                params.put("send_time", m.getTime()+"");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void registerMyReceiver() {

        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("service.to.activity.transfer");
            registerReceiver(myBroadCastReceiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    class MyBroadCastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            try
            {
                Log.e("Chat, Firebase Service", "onReceive() called");
                String message = intent.getStringExtra("message");
                String time = intent.getStringExtra("time");
                Message m= new Message(chat_id,parentID,message,time);
                m.setSenderName(ChatRoom.parentName);
                Toast.makeText(getApplicationContext(),"name:"+ChatRoom.parentName,Toast.LENGTH_LONG).show();
                Send(m);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myBroadCastReceiver);
        super.onStop();
    }
}
