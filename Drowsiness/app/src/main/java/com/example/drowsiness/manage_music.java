package com.example.drowsiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class manage_music extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView l1;
    Button b1;
    String url,Mid;
    SharedPreferences sh;
    ArrayList<String> musics, details1, emotions1, mid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_music);

        l1 = findViewById(R.id.List1);
        b1 = findViewById(R.id.button5);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        url ="http://"+sh.getString("ip", "") + ":5000/music_app";
        RequestQueue queue = Volley.newRequestQueue(manage_music.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                        JSONArray ar=new JSONArray(response);
                        musics= new ArrayList<>();
                        mid= new ArrayList<>();
                        details1 = new ArrayList<>();
                        emotions1 = new ArrayList<>();

                        for(int i=0;i<ar.length();i++)
                        {
                            JSONObject jo=ar.getJSONObject(i);
                            musics.add(jo.getString("Musics"));
                            mid.add(jo.getString("mid"));
                            details1.add(jo.getString("Details"));
                            emotions1.add(jo.getString("Emotions"));


                        }

                        // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                        //lv.setAdapter(ad);

                        l1.setAdapter(new custom2(manage_music.this,details1,musics,emotions1));
                        l1.setOnItemClickListener(manage_music.this);

                    }
                catch (Exception e)
                {
                    Log.d("=========", e.toString());
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(manage_music.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);


        ///////////////////////////////////////////


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(),AddMusic.class);
                startActivity(i1);

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Mid=mid.get(i);
        AlertDialog.Builder ald=new AlertDialog.Builder(manage_music.this);
        ald.setTitle("title")
                .setPositiveButton(" Edit ", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        try
                        {
                            Intent i=new Intent(getApplicationContext(),EditMusic.class);
                            i.putExtra("mid",Mid);
                            startActivity(i);
//                            SharedPreferences.Editor ed=sh.edit();
//                            ed.putString("orginal",fname.get(pos));
//                            ed.commit();
//                            startDownload(fname.get(pos));
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton(" Delete ", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        RequestQueue queue = Volley.newRequestQueue(manage_music.this);
                        url = "http://"+sh.getString("ip","")+":5000/delete_app";
                        Toast.makeText(manage_music.this,url , Toast.LENGTH_SHORT).show();

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

                                    if (res.equalsIgnoreCase("success"))
                                    {
//                                        String lid = json.getString("id");     // getting login id
//                                        SharedPreferences.Editor edp = sh.edit();
//                                        edp.putString("lid", lid);
//                                        edp.commit();
                                        Intent ik = new Intent(getApplicationContext(), manage_music.class);
                                        startActivity(ik);
                                    }
                                    else
                                    {
                                        Toast.makeText(manage_music.this, "success", Toast.LENGTH_SHORT).show();
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
                                params.put("m_id",mid.get(i));
//                                params.put("uname", username);
//                                params.put("pass", password);

                                return params;
                            }
                        };
                        queue.add(stringRequest);

//                        i.putExtra("imgid", fid.get(pos));
//                        startActivity(i);
                    }
                });
        AlertDialog al=ald.create();
        al.show();




    }
}