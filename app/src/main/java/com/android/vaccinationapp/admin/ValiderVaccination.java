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
import com.android.vaccinationapp.model.Vaccination;
import com.android.vaccinationapp.user.WelcomeActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ValiderVaccination extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valider_vaccination);

        menu();

        Intent i = getIntent();
        Vaccination c = i.getParcelableExtra("message");

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
        t = (TextView) this.findViewById(R.id.date);
        t.setText(c.date_vaccination);
        t = (TextView) this.findViewById(R.id.centre);
        t.setText(c.centre);

        Button confirm = (Button) this.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ValiderVaccination.this);
                AlertDialog dialog;

                // Add the buttons
                builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        new DAO().validerVaccination(c.id_vaccination, true);

                        Toast.makeText(ValiderVaccination.this, "Le citoyen que vous avez selectionné est bien considéré comme vacciné dans notre système.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ValiderVaccination.this, ListeVaccin.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                // Set other dialog properties
                builder.setMessage("Confirmez-vous que ce citoyen a été bien vacciné dans le centre mentionné avant ?")
                        .setTitle("Confirmation de l'opération souhaité");




                // Create the AlertDialog
                dialog = builder.create();
                dialog.show();

            }
        } ) ;




        Button annuler = (Button) this.findViewById(R.id.annuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ValiderVaccination.this);
                AlertDialog dialog;

                // Add the buttons
                builder.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        new DAO().validerVaccination(c.id_vaccination, false);

                        Toast.makeText(ValiderVaccination.this, "Le citoyen que vous avez selectionné a été bien considéré comme non vacciné dans notre système.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ValiderVaccination.this, ListeVaccin.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                // Set other dialog properties
                builder.setMessage("Etes-vous sur que ce citoyen n'a pas assité au rendez-vous de vaccination ?")
                        .setTitle("Confirmation de l'opération souhaité");




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