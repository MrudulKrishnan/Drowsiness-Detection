package com.example.drowsiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity
{

    EditText e1, e2;
    Button b1, b2;
    String username, password,url;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1 = findViewById(R.id.editTextTextPersonName);
        e2 = findViewById(R.id.editTextTextPersonName2);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                username=e1.getText().toString();
                password=e2.getText().toString();


//                Intent i1 = new Intent(getApplicationContext(), Home.class);
//                startActivity(i1);
                //////////////////////////////////////
                if (username.equalsIgnoreCase(""))
                {
                    e1.setError("Enter Username");
                }
                else if (password.equalsIgnoreCase(""))
                {
                    e2.setError("Enter Password");
                }
                else{
                    RequestQueue queue = Volley.newRequestQueue(login.this);
                    url = "http://"+sh.getString("ip","")+":5000/login_code";
                    Toast.makeText(login.this,url , Toast.LENGTH_SHORT).show();

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try
                            {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid"))
                                {
                                    String lid = json.getString("id");     // getting login id
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();
                                    Intent ik = new Intent(getApplicationContext(), Home.class);
                                    startActivity(ik);
                                }
                                else
                                {
                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uname", username);
                            params.put("pass", password);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                    ////////////////////////////////////

                    }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i2 = new Intent(getApplicationContext(), registration.class);
                startActivity(i2);

            }
        });
    }
}