package com.android.vaccinationapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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


public class ListeDemande extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_demande);

        menu();

        TableLayout table = (TableLayout) this.findViewById(R.id.table);

        /*for(int i=0; i<10; i++) {

            CitizenRequest c = new CitizenRequest();

            c.id = "2";
            c.full_name = "Ahmed Fakhir" + String.valueOf(i);
            c.email = "blabla@email";
            c.password = "pass";
            c.phone_number = "0612345678";
            c.cin = "CD5555";
            c.age = 19;
            c.address = "Mon adresse, Rue Abdelhdai Skalli, Quartier Adarissa, Fes";

            c.id_request = "2";
            c.request_date = "20/2/2021";
            c.request_state = "l";

            TableRow r = (TableRow)(LayoutInflater.from(this).inflate(R.layout.temp, null, false));

            ((TextView) r.getChildAt(0)).setText(c.full_name);
            ((Button) r.getChildAt(1)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, Demande.class);
                    intent.putExtra("message", c);
                    startActivity(intent);
                }
            } ) ;

            table.addView(r);
        }*/
        DAO d = new DAO();

        for(CitizenRequest c : d.listeDemande(true)) {

            TableRow r = (TableRow)(LayoutInflater.from(this).inflate(R.layout.temp, null, false));

            TableRow rv = (TableRow)(LayoutInflater.from(this).inflate(R.layout.tempv, null, false));
            View vi = (View) rv.getChildAt(0);
            rv.removeView(vi);

            ((TextView) r.getChildAt(0)).setText(c.full_name);
            ((Button) r.getChildAt(1)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(ListeDemande.this, Demande.class);
                    intent.putExtra("message", c);
                    startActivity(intent);
                }
            } ) ;

            table.addView(vi);
            table.addView(r);
        }




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