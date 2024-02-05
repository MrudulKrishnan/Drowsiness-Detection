package com.example.drowsiness;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class send_complaints_view_reply extends AppCompatActivity {

    ListView l1;
    Button b1;
    SharedPreferences sh;
    String url;
    ArrayList<String> Complaints, Reply, Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_complaints_view_reply);

        l1 = findViewById(R.id.list2);

        b1 = findViewById(R.id.button4);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url = "http://" + sh.getString("ip", "") + ":5000/reply_app";
        RequestQueue queue = Volley.newRequestQueue(send_complaints_view_reply.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(send_complaints_view_reply.this, "=="+response, Toast.LENGTH_SHORT).show();

                    JSONArray ar=new JSONArray(response);
                    Complaints = new ArrayList<>();
                    Reply = new ArrayList<>();
                    Date= new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        Complaints.add(jo.getString("Complaint"));
                        Reply.add(jo.getString("Reply"));
                        Date.add(jo.getString("Date"));
                    }

//                     ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    lv.setAdapter(ad);

                    l1.setAdapter(new custom1(send_complaints_view_reply.this,Complaints,Date,Reply));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(send_complaints_view_reply.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);
///////////////////////////////////////////////////////////////////////////////////////////

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), send_complaint.class);
                startActivity(i1);
            }
        });
    }
}