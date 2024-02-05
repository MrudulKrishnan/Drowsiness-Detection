package com.example.drowsiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    String IP;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editTextTextPersonName12);
        b1 = findViewById(R.id.button7);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IP = e1.getText().toString();

                if (IP.equalsIgnoreCase("")) {
                    e1.setError("Enter Your IP address");}
                else {
                    SharedPreferences.Editor edp = sh.edit();
                    edp.putString("ip", IP);
                    edp.commit();

                    Intent i1 = new Intent(getApplicationContext(), login.class);
                    startActivity(i1);
                    Toast.makeText(MainActivity.this, IP + " : please login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}