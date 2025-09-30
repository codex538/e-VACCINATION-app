package com.android.vaccinationapp.admin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.vaccinationapp.MainActivity;
import com.android.vaccinationapp.R;
import com.android.vaccinationapp.firestore.DAO;
import com.android.vaccinationapp.model.CitizenRequest;
import com.android.vaccinationapp.model.Vaccination;
import com.android.vaccinationapp.user.WelcomeActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Stat extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        menu();



        DAO d = new DAO();

        int dn = 0;
        int db = 0;
        int dbf = 0;

        Date act = new Date();
        for (CitizenRequest x : d.listeDemande(false)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Date date = null;

            try {
                date = format.parse(x.request_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if ((date.getMonth() == act.getMonth()) && (date.getYear() == act.getYear()))
                dn = dn + 1;

            if ((date.getMonth() == act.getMonth() - 1) && (date.getYear() == act.getYear()))
                db = db + 1;

            if ((date.getMonth() == act.getMonth() - 2) && (date.getYear() == act.getYear()))
                dbf = dbf + 1;

        }


        SimpleDateFormat fr = new SimpleDateFormat("MMM yyyy");

        BarChart barchart = findViewById(R.id.chart);
        barchart.animateY(1000);

        ArrayList<String> label = new ArrayList<String>();
        label.add(fr.format(act));

        try {

            Date actddd = new Date();

            ArrayList<BarEntry> data = new ArrayList<BarEntry>();

            actddd.setMonth(act.getMonth() - 2);
            data.add(new BarEntry(1, dbf));

            Date actdd = new Date();
            actdd.setMonth(act.getMonth() - 1);

            data.add(new BarEntry(2, db));
            data.add(new BarEntry(3, dn));

            ArrayList<String> labels = new ArrayList<String>();
            labels.add("");
            labels.add(fr.format(actddd));
            labels.add(fr.format(actdd));
            labels.add(fr.format(act));

            barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

            barchart.getAxisRight().setEnabled(false);
            barchart.getLegend().setEnabled(false);
            barchart.getAxisRight().setDrawAxisLine(false);
            barchart.getXAxis().setDrawGridLines(false);
            barchart.getAxisLeft().setDrawGridLinesBehindData(false);
            barchart.getDescription().setEnabled(false);
            barchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            barchart.getAxisLeft().setAxisMinimum(0);
            barchart.getAxisLeft().setGranularity(1.0f);
            barchart.getAxisLeft().setGranularityEnabled(true);
            barchart.getXAxis().setGranularity(1f);
            barchart.getAxisLeft().setDrawGridLines(false);


            BarDataSet dataset = new BarDataSet(data, "test");
            dataset.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.valueOf((int)value);
                }
            });

            dataset.setColors(ColorTemplate.COLORFUL_COLORS);
            BarData bardata = new BarData(dataset);


            barchart.setData(bardata);
            barchart.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }


















        PieChart piechart = findViewById(R.id.chart2);

        dn = 0;
        db = 0;
        dbf = 0;
        int dbff = 0;

        for (Vaccination x : d.listeDemandeVaccin()) {

            if (x.centre.equals("Centre Ibn Baitar Fès"))
                dn = dn + 1;

            if (x.centre.equals("Centre de conférence Nahda Rabat"))
                db = db + 1;

            if (x.centre.equals("Complexe Idéal Bourgogne Casablanca"))
                dbf = dbf + 1;

            if (x.centre.equals("Centre Zertouni Marrakech"))
                dbff = dbff + 1;

        }

        ArrayList<PieEntry> data = new ArrayList<PieEntry>();
        data.add(new PieEntry(dn, "Centre Ibn Baitar Fès"));
        data.add(new PieEntry(db, "Centre de conférence Nahda Rabat"));
        data.add(new PieEntry(dbf,"Complexe Idéal Bourgogne Casablanca"));
        data.add(new PieEntry(dbff,"Centre Zertouni Marrakech"));

        PieDataSet dset = new PieDataSet(data, "");
        dset.setColors(ColorTemplate.COLORFUL_COLORS);
        dset.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int)value);
            }
        });

        piechart.setData(new PieData(dset));
        piechart.animateXY(1000, 1000);

        piechart.getDescription().setEnabled(false);
        piechart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        piechart.getLegend().setDrawInside(false);
        piechart.getLegend().setYOffset(10f);
        piechart.setEntryLabelColor(Color.BLACK);



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