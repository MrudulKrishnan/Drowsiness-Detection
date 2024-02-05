package com.example.drowsiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button b1, b2, b3, b4, b5, b6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        b1 = findViewById(R.id.button8);
        b2 = findViewById(R.id.button9);
        b3 = findViewById(R.id.button10);
//        b4 = findViewById(R.id.button11);
        b5 = findViewById(R.id.button12);
        b6 = findViewById(R.id.button13);

//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i2=new Intent(getApplicationContext(), manage_music.class);
//                startActivity(i2);
//
//            }
//        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(), manage_music.class);
                startActivity(i2);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(getApplicationContext(), send_complaints_view_reply.class);
                startActivity(i3);

            }
        });

//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(getApplicationContext(), feedback.class);
                startActivity(i5);

            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6 = new Intent(getApplicationContext(),login.class);
                startActivity(i6);

            }
        });

    }
}