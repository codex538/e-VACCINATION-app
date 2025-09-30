package com.android.vaccinationapp.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.vaccinationapp.R;

public class DownloadCertificateActivity extends AppCompatActivity {

    private Toolbar appbar;
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_certificate);

        appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        download = findViewById(R.id.downloadBtn);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DownloadCertificateActivity.this, "Téléchargement en cours ...", Toast.LENGTH_LONG).show();
            }
        });
    }
}