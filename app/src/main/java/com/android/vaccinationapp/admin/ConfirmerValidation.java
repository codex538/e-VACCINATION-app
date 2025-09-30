package com.android.vaccinationapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.android.vaccinationapp.MainActivity;
import com.android.vaccinationapp.R;
import com.android.vaccinationapp.firestore.DAO;
import com.android.vaccinationapp.model.CitizenRequest;
import com.android.vaccinationapp.user.WelcomeActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmerValidation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_confirmer_validation);

            menu();

            Intent i = getIntent();
            CitizenRequest c = i.getParcelableExtra("message");

            DatePicker dp = (DatePicker) this.findViewById(R.id.date);

            dp.setMinDate(new Date().getTime());

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

            try {
                dp.setMaxDate(format.parse("31-12-2022").getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }



            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_activated_1,
                new String[]{"Centre Ibn Baitar Fès", "Centre de conférence Nahda Rabat", "Complexe Idéal Bourgogne Casablanca", "Centre Zertouni Marrakech"});
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);








            Button confirm = (Button) this.findViewById(R.id.confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                     new DAO().ajouterVaccination(c.id_request, true, (String) spinner.getSelectedItem(),
                             String.valueOf(dp.getDayOfMonth()) +"-"+  String.valueOf(dp.getMonth()) +"-"+ String.valueOf(dp.getYear()));




                     Toast.makeText(ConfirmerValidation.this, "La demande de vaccination a été bien validée.", Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(ConfirmerValidation.this, ListeDemande.class);
                     startActivity(intent);


                }
            } ) ;




            Button annuler = (Button) this.findViewById(R.id.annuler);
            annuler.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    finish();
                }
            } ) ;
    }


















    private DrawerLayout drawerLayout;

    public void menu() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        this.setSupportActionBar(toolbar);

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ( (NavigationView) findViewById(R.id.nav_view) ).setNavigationItemSelectedListener(this);





    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.acceuil :
                startActivity(new Intent(this, AccueilAdmin.class));
                break;
            case R.id.demande:
                startActivity(new Intent(this, ListeDemande.class));
                break;
            case R.id.citoyen:
                startActivity(new Intent(this, ListeVaccin.class));
                break;
            case R.id.stat:
                startActivity(new Intent(this, Stat.class));
                break;
            case R.id.deconnecter:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }




}