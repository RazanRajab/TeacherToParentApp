package com.example.razan.teachertoparent_t2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppConfig;
import app.AppController;

public class Login extends AppCompatActivity {
private EditText username;
    private EditText pass;
    private User user;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session=new Session(this);
        username= findViewById(R.id.name);
        pass= findViewById(R.id.password);
        user=new User();

    }
    private void checkLoginFromServer(final User user) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("MyTAG", "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    System.out.println(jObj.toString());
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        // Now store the user in SQLite
                        // String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");//name of object returned from database
                        String name = user.getString("username"); //same names of json fields
                        String password=user.getString("pass");
                        int id=user.getInt("userID");
                        System.err.print("id:"+id+" name:"+name+" pass:"+password);

                        session.setLoggedin(true);
                        session.set_user_id(id);

                        // Launch main activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
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
                params.put("name", user.getUserName());
                params.put("pass", user.getPass());
                params.put("role",user.getRole()+"");

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void sginin(View view) {
        Toast.makeText(this,username.getText()+"+"+pass.getText(),Toast.LENGTH_LONG).show();
        user.setUserName(username.getText().toString());
        user.setPass(pass.getText().toString());
        user.setRole(1);
       // Toast.makeText(this,user.getUserName()+"+"+user.getPass(),Toast.LENGTH_LONG).show();

        checkLoginFromServer(user);
    }
}
