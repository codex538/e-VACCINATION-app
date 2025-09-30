package com.android.vaccinationapp.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vaccinationapp.R;
import com.android.vaccinationapp.firestore.RequestsFirestoreManager;
import com.android.vaccinationapp.model.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseUser fb_user ;

    private Toolbar appbar;
    private TextView filledform;
    private Button request;

    private RequestsFirestoreManager requestsFirestoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        fb_user = getIntent().getExtras().getParcelable("currUser");

        request = findViewById(R.id.btnRegister);
        request.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String citizen = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));
                String request_date = dtf.format(now);

                String request_state = "";

                Request req = new Request();

                req.setCitizen(citizen);
                req.setRequest_date(request_date);
                req.setRequest_state(request_state);

                requestsFirestoreManager = RequestsFirestoreManager.newInstance();

                requestsFirestoreManager.createDocumentRequest(req);
                Toast.makeText(RequestActivity.this, "Demande déposée avec succès", Toast.LENGTH_LONG).show();
            }
        });

        filledform = findViewById(R.id.alreadyFilledForm);
        filledform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RequestActivity.this, RequestFollowupActivity.class));
            }
        });
    }
}