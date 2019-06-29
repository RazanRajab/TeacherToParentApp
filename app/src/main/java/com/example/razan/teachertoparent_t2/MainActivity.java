package com.example.razan.teachertoparent_t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener{

    private Session session;
    private ListView myList;
    private Spinner spinner;
    private ArrayList<Material> list;
    private String[] class_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this,MyFirebaseMessagingService.class));
        class_name= new String[]{"الصف الأول", "الصف الثاني", "الصف الثالث", "الصف الرابع", "الصف الخامس", "الصف السادس"};
        session = new Session(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

   /*     spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String day = parent.getItemAtPosition(position).toString();
               // Toast.makeText(getApplicationContext(),session.get_user_id()+"",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"hi:"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                getMaterialsFromServer(session.get_user_id(),day);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
*/
        myList = findViewById(R.id.list_view);
        myList.setOnItemClickListener(this);
        list = new ArrayList<>();

        //the code below is for action _ buttons

        ImageView settings = new ImageView(this);
        settings.setImageResource(R.drawable.ic_settings);

        final FloatingActionButton fab = new FloatingActionButton.Builder(this).setContentView(settings).build();
        SubActionButton.Builder builder = new SubActionButton.Builder(this);

        ImageView logout_icon = new ImageView(this);
        logout_icon.setImageResource(R.drawable.ic_logout_icon);
        SubActionButton logout = builder.setContentView(logout_icon).build();


        ImageView chat_icon = new ImageView(this);
        chat_icon.setImageResource(R.drawable.ic_chat_icon);
        SubActionButton chat = builder.setContentView(chat_icon).build();

        final FloatingActionMenu fam = new FloatingActionMenu.Builder(this)
                .addSubActionView(logout)
                .addSubActionView(chat)
                .attachTo(fab)
                .build();

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(MainActivity.this, LoadingActivity.class);
                startActivity(n);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
            session.setLoggedin(false);
            this.finish();
            startActivity(new Intent(MainActivity.this,Login.class));
    }

    private void getMaterialsFromServer(final int id,final String day) {
        // Tag used to cancel the request
        String tag_string_req = "req_select_materials";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Schedule, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "schedule Response: " + response.toString());

                try {
                    JSONObject jObject = new JSONObject(response);
                    JSONArray jArray = jObject.getJSONArray("materials");

                    System.out.println(jObject.toString());
                    boolean error = jObject.getBoolean("error");
                    // Check for error node in json
                    if (!error) {

                        for(int i=0;i<jArray.length();i++) {

                            JSONObject material = jArray.getJSONObject(i);//name of object returned from database
                            String name = material.getString("material_name"); //same names of json fields
                            int class_id = material.getInt("class_id");
                            int id = material.getInt("material_id");
                            int time = material.getInt("timee");
                            System.err.print("id:" + id + " +material_name:" + name + " +class:" + class_id + " +time:" + time);

                            list.add(new Material(id,name,class_name[class_id-1],time,class_id));
                        }
                        ListAdapter LA = new ListAdapter(getApplicationContext(), R.layout.card_view, list);
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
                params.put("day",day );

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),"hi:"+parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
        list.clear();
        String day = parent.getItemAtPosition(position).toString();
        getMaterialsFromServer(session.get_user_id(),day);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent n=new Intent(this,StudentsActivity.class);
        n.putExtra("class_id",list.get(position).getClassID());
        startActivity(n);
    }
}
