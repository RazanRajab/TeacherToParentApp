package com.example.razan.teachertoparent_t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;

public class MyChats extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public  ArrayList<Chat> Chats;
    private Session session;
    private ListView myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats);
        session=new Session(this);
        Chats = new ArrayList<>();
        myList = findViewById(R.id.chats_view);
        myList.setOnItemClickListener(this);
        getChats(session.get_user_id());
    }

    //didn't work !!!!!!!!!!
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int chat_id=Chats.get(position).getChat_id();
        int parentID=Chats.get(position).getParent_id();
        String name=Chats.get(position).getParent_name();
        Intent intent = new Intent(getApplicationContext(), ChatRoom.class);
        intent.putExtra("name",name);
        intent.putExtra("chat_id",chat_id);
        intent.putExtra("parent_id",parentID);

        startActivity(intent);

    }

    private void getChats(final int id) {
        // Tag used to cancel the request
        String tag_string_req = "req_my_chats";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Chat_MyChats, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "my chats Response: " + response.toString());

                try {
                    JSONObject jObject = new JSONObject(response);
                    JSONArray jArray = jObject.getJSONArray("chats");

                    System.out.println(jObject.toString());
                    boolean error = jObject.getBoolean("error");
                    // Check for error node in json
                    if (!error) {

                        for(int i=0;i<jArray.length();i++) {

                            JSONObject s = jArray.getJSONObject(i);//name of object returned from database
                            String parent_name = s.getString("parent_name"); //same names of json fields
                            int chat_id = s.getInt("chat_id");
                            int parent_id = s.getInt("parent_id");
                            String last_m = s.getString("last_message");
                            System.err.print("chat_id:" + chat_id + " +parent_name:" + parent_name + " +parent:" + parent_id + " +last_message:" + last_m);
                            Chat c= new Chat(chat_id,parent_id,parent_name);
                            System.err.print("chat_id:: "+c.getChat_id());
                            c.setLast_message(last_m);
                            Chats.add(c);
                        }
                       ChatsAdapter LA = new ChatsAdapter(getApplicationContext(), R.layout.single_chat_view, Chats);
                        myList.setAdapter(LA);
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
                params.put("teacher_id", id+"");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
