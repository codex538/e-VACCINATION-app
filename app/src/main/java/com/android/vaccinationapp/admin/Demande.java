package com.android.vaccinationapp.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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

public class Demande extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);

        menu();

        Intent i = getIntent();
        CitizenRequest c = i.getParcelableExtra("message");

        TextView t = (TextView) this.findViewById(R.id.nom);
        t.setText(c.full_name);
        t = (TextView) this.findViewById(R.id.email);
        t.setText(c.email);
        t = (TextView) this.findViewById(R.id.tel);
        t.setText(c.phone_number);
        t = (TextView) this.findViewById(R.id.cin);
        t.setText(c.cin);
        t = (TextView) this.findViewById(R.id.age);
        t.setText(String.valueOf(c.age));
        t = (TextView) this.findViewById(R.id.adresse);
        t.setText(c.address);
        t = (TextView) this.findViewById(R.id.request_date);
        t.setText(c.request_date);

        Button confirm = (Button) this.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /*AlertDialog.Builder builder = new AlertDialog.Builder(Demande.this);
                AlertDialog dialog;

                int id = View.generateViewId();

                // Add the buttons
                builder.setNeutralButton("Confirmer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                // Set other dialog properties
                builder.setMessage("Etes-vous sur de vouloir valider cette demande de vaccination ? Si oui, veuillez spécifier la date de vaccination.")
                        .setTitle("Validation de la demande");

                DatePicker d = new DatePicker(Demande.this);
                d.setMinDate(new Date().getTime());

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    d.setMaxDate(format.parse("31-12-2022").getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                d.setId(id);
                d.setPadding(20, 40, 20, 20);

                builder.setView(d);




                // Create the AlertDialog
                dialog = builder.create();
                dialog.show();*/

                Intent intent = new Intent(Demande.this, ConfirmerValidation.class);
                intent.putExtra("message", c);
                startActivity(intent);


            }
        } ) ;




        Button annuler = (Button) this.findViewById(R.id.annuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Demande.this);
                AlertDialog dialog;

                // Add the buttons
                builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        new DAO().ajouterVaccination(c.id_request, false, null, null);

                        Toast.makeText(Demande.this, "La demande de vaccination a été bien rejetée.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Demande.this, ListeDemande.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                // Set other dialog properties
                builder.setMessage("Etes-vous sur de vouloir rejeter cette demande de vaccination ?")
                        .setTitle("Confirmation de la demande de rejet");




                // Create the AlertDialog
                dialog = builder.create();
                dialog.show();


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