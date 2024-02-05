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
import android.widget.RadioButton;
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

public class registration extends AppCompatActivity {

    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
    RadioButton r1,r2;
    Button b1;
    String firstname, lastname,gender, place, post, pin, email,
            phone, username, password, url, vehicleNo;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        e1 = findViewById(R.id.editTextTextPersonName3);
        e2 = findViewById(R.id.editTextTextPersonName4);
        e3 = findViewById(R.id.editTextTextPersonName5);
        e4 = findViewById(R.id.editTextTextPersonName6);
        e5 = findViewById(R.id.editTextTextPersonName7);
        e6 = findViewById(R.id.editTextTextPersonName8);
        e7 = findViewById(R.id.editTextTextPersonName9);
        e8 = findViewById(R.id.editTextTextPersonName10);
        e9 = findViewById(R.id.editTextTextPassword);
        e10 = findViewById(R.id.editTextTextPersonName14);
        r1 = findViewById(R.id.radioButton);
        r2 = findViewById(R.id.radioButton2);

        b1 = findViewById(R.id.button3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstname = e1.getText().toString();
                lastname = e2.getText().toString();
                place = e3.getText().toString();
                post = e4.getText().toString();
                pin = e5.getText().toString();
                email = e6.getText().toString();
                phone = e7.getText().toString();
                username = e8.getText().toString();
                password = e9.getText().toString();
                vehicleNo = e10.getText().toString();
                gender = "";
                if (r1.isChecked()) {
                    gender = r1.getText().toString();
                } else {
                    gender = r2.getText().toString();
                }
                if (firstname.equalsIgnoreCase("")) {
                    e1.setError("Enter Your firstName");
                } else if (lastname.equalsIgnoreCase("")) {
                    e2.setError("Enter Your lastname");
                } else if (place.equalsIgnoreCase("")) {
                    e3.setError("Enter Your place");
                } else if (post.equalsIgnoreCase("")) {
                    e4.setError("Enter Your post");
                } else if (pin.equalsIgnoreCase("")) {
                    e5.setError("Enter Your pin");
                } else if (email.equalsIgnoreCase("")) {
                    e6.setError("Enter Your email");
                } else if (phone.equalsIgnoreCase("")) {
                    e7.setError("Enter Your phone");
                } else if (username.equalsIgnoreCase("")) {
                    e8.setError("Enter Your username");
                } else if (password.equalsIgnoreCase("")) {
                    e9.setError("Enter Your password");
                } else if (vehicleNo.equalsIgnoreCase("")) {
                    e10.setError("Enter Your vehicle number");
                }
                else {

                    RequestQueue queue = Volley.newRequestQueue(registration.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/registration";
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {
//                                String lid = json.getString("id");
//                                SharedPreferences.Editor edp = sh.edit();
//                                edp.putString("lid", lid);
//                                edp.commit();
                                    Intent ik = new Intent(getApplicationContext(), login.class);
                                    startActivity(ik);
                                } else {
                                    Toast.makeText(registration.this, "registration failed ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Firstname", firstname);
                            params.put("Lastname", lastname);
                            params.put("Place", place);
                            params.put("Post", post);
                            params.put("Pin", pin);
                            params.put("Vehicle", vehicleNo);
                            params.put("Phone", phone);
                            params.put("Gender", gender);
                            params.put("Email", email);
                            params.put("Username", username);
                            params.put("Password", password);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
//                Intent i=new Intent(getApplicationContext(),login.class);
//                startActivity(i);

                }
            }
        });

    }
}