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

public class StudentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView myList;
    private ArrayList<Student> Students;
    private Session session;
    private boolean exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        myList = findViewById(R.id.student_view);
        myList.setOnItemClickListener(this);
        Students = new ArrayList<>();
        session =new Session(this);
        Bundle bd = getIntent().getExtras();
        if(bd != null)
        {
            Toast.makeText(this,"class_id:"+bd.getInt("class_id"),Toast.LENGTH_LONG).show();
            getStudents(bd.getInt("class_id"));
        }
    }

    private void getStudents(final int id) {
        // Tag used to cancel the request
        String tag_string_req = "req_select_students";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Students, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "students Response: " + response.toString());

                try {
                    JSONObject jObject = new JSONObject(response);
                    JSONArray jArray = jObject.getJSONArray("students");

                    System.out.println(jObject.toString());
                    boolean error = jObject.getBoolean("error");
                    // Check for error node in json
                    if (!error) {

                        for(int i=0;i<jArray.length();i++) {

                            JSONObject s = jArray.getJSONObject(i);//name of object returned from database
                            String name = s.getString("student_name"); //same names of json fields
                            int class_id = s.getInt("class_id");
                            int id = s.getInt("student_id");
                            int parent_id = s.getInt("parent_id");
                            String parent_name = s.getString("user_name");
                            System.err.print("id:" + id + " name:" + name + " +class:" + class_id + " +parent:" + parent_id);

                            Students.add(new Student(id,name,class_id,parent_id,parent_name));
                        }
                        ListStudentAdapter LA = new ListStudentAdapter(getApplicationContext(), R.layout.card_view, Students);
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
                params.put("class_id", id+"");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int parentID=Students.get(position).getParent_id();
        String name=Students.get(position).getParent_name();
        ChatRoomExist(session.get_user_id(),parentID,name);
    }

    private void createChatRoom(final int id, final int parent_id,final String name) {
        // Tag used to cancel the request
        String tag_string_req = "req_open_chat_room";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Chat_Create, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "Create Chat Room Response: " + response.toString());

                try {
                    JSONObject jObject = new JSONObject(response);

                    System.out.println(jObject.toString());
                    boolean error = jObject.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        JSONObject chat = jObject.getJSONObject("chat");
                            int chat_id = chat.getInt("chat_id");
                            int teacher_id = chat.getInt("teacher");
                            int parent_id = chat.getInt("parent");
                            System.err.print("chat_id:" + chat_id + " +teacher_id:" + teacher_id + " +parent:" + parent_id);
                        Intent intent = new Intent(getApplicationContext(), ChatRoom.class);
                        intent.putExtra("name",name);
                        intent.putExtra("chat_id",chat_id);
                        intent.putExtra("parent_id",parent_id);

                        startActivity(intent);
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
                params.put("user_id", id+"");
                params.put("parent_id", parent_id+"");
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void ChatRoomExist(final int id, final int parent_id,final String name) {
        // Tag used to cancel the request
        String tag_string_req = "req_isExist_chat_room";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Chat_Exist, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "Chat Room Exist Response: " + response.toString());

                try {
                    JSONObject jObject = new JSONObject(response);

                    System.out.println(jObject.toString());
                    boolean error = jObject.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        JSONObject chat=jObject.getJSONObject("chat");
                        int chat_id=chat.getInt("chat_id");
                        Intent n=new Intent(getApplicationContext(),ChatRoom.class);
                        n.putExtra("name",name);
                        n.putExtra("chat_id",chat_id);
                        n.putExtra("parent_id",parent_id);

                        startActivity(n);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg+": response", Toast.LENGTH_LONG).show();
                        createChatRoom(session.get_user_id(),parent_id,name);
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
                params.put("user_id", id+"");
                params.put("parent_id", parent_id+"");
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
