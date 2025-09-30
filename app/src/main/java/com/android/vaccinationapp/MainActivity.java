package com.android.vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.vaccinationapp.user.LoginActivity;
import com.android.vaccinationapp.user.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    private final static int SECOND_CALL_ID = 1234;
    private Button admin;
    private Button user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MainActivity.this,
                        LoginActivity.class
                );
                startActivityForResult(intent, SECOND_CALL_ID);

            }
        });

        user = findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MainActivity.this,
                        WelcomeActivity.class
                );
                startActivityForResult(intent, SECOND_CALL_ID);

            }
        });
    }
}