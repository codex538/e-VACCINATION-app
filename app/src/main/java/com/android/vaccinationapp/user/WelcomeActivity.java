package com.android.vaccinationapp.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.vaccinationapp.MainActivity;
import com.android.vaccinationapp.R;

public class WelcomeActivity extends AppCompatActivity {

    private final static int SECOND_CALL_ID = 1234;
    private Button btnLogin;
    private Button btnRegister;
    private ImageButton previous;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        previous = findViewById(R.id.imageButton);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });


        btnLogin = findViewById(R.id.button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        WelcomeActivity.this,
                        LoginActivity.class
                );
                startActivityForResult(intent, SECOND_CALL_ID);

            }
        });

        btnRegister = findViewById(R.id.button2);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        WelcomeActivity.this,
                        RegisterActivity.class
                );
                startActivityForResult(intent, SECOND_CALL_ID);

            }
        });
    }
}