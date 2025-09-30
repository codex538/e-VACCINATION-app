package com.android.vaccinationapp.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.vaccinationapp.model.CitizenRequest;
import com.android.vaccinationapp.model.Vaccination;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAO {

    private static FirebaseFirestore db;
    private boolean erreur;

    public DAO() {
        erreur = false;
    }

    public void connect(){
        db = FirebaseFirestore.getInstance();
    }

    public ArrayList<CitizenRequest> listeDemande(boolean b) {

        ArrayList<CitizenRequest> l = new ArrayList<CitizenRequest>();

        Task<QuerySnapshot> t2 = null;
        if (b)
            t2 = db.collection("requests").whereEqualTo("request_state", "").get().addOnFailureListener(this.onFailureListener());
        else
            t2 = db.collection("requests").get().addOnFailureListener(this.onFailureListener());

        while (!erreur) {
            if (t2.isSuccessful()) {

                for (QueryDocumentSnapshot document2 : t2.getResult()) {

                    CitizenRequest c = new CitizenRequest();

                    c.id_request = document2.getId();
                    c.request_date = document2.getString("request_date");
                    c.request_state = document2.getString("request_state");

                    //Task<DocumentSnapshot> t1 = document2.getDocumentReference("citizen").get();
                    Task<DocumentSnapshot> t1 = db.document("/users/" + document2.getString("citizen")).get();

                    while (!erreur) {
                        if (t1.isSuccessful()) {

                            DocumentSnapshot document = t1.getResult();

                            Log.d("Message", document.getId() + " => " + document.getData());

                            c.id = document.getId();
                            c.full_name = document.getString("full_name");
                            c.email = document.getString("email");
                            c.phone_number = document.getString("phone_number");
                            c.cin = document.getString("cin");
                            c.age = document.getLong("age").intValue();
                            c.address = document.getString("address");

                            l.add(c);

                            break;

                        }
                    }


                }
                break;
            }
        }


        return l;


    }



    public ArrayList<Vaccination> listeCitoyenVaccin() {

        ArrayList<Vaccination> l = new ArrayList<Vaccination>();

        Task<QuerySnapshot> t3 = db.collection("vaccinations").whereEqualTo("vaccination_state", "").get().addOnFailureListener(this.onFailureListener());

        while (!erreur) {
            if (t3.isSuccessful()) {

                for (QueryDocumentSnapshot document : t3.getResult()) {

                    Vaccination v = new Vaccination();

                    v.id_vaccination = document.getId();
                    v.centre = document.getString("location");
                    v.date_vaccination = document.getString("first_dose_date");

                    Log.d("Message", document.getId() + " => " + document.getData());

                    //Task<DocumentSnapshot> t2 = document.getDocumentReference("request").get();
                    Task<DocumentSnapshot> t2 = db.document("/requests/" + document.getString("request")).get();


                    while (!erreur) {
                        if (t2.isSuccessful()) {

                            DocumentSnapshot document2 = t2.getResult();

                            Log.d("Message", document2.getId() + " => " + document2.getData());
                            v.id_request = document2.getId();
                            v.request_date = document2.getString("request_date");
                            v.request_state = document2.getString("request_state");

                            //Task<DocumentSnapshot> t1 = document2.getDocumentReference("citizen").get();
                            Task<DocumentSnapshot> t1 = db.document("/users/" + document2.getString("citizen")).get();

                            while (!erreur) {
                                if (t1.isSuccessful()) {
                                    DocumentSnapshot document1 = t1.getResult();



                                    v.id = document1.getId();
                                    v.full_name = document1.getString("full_name");
                                    v.email = document1.getString("email");
                                    v.phone_number = document1.getString("phone_number");
                                    v.cin = document1.getString("cin");
                                    v.age = document1.getLong("age").intValue();
                                    v.address = document1.getString("address");


                                    l.add(v);


                                    break;





                                }
                            }


                            break;


                        }
                    }




                }
                break;
            }
        }


        return l;
    }



    public ArrayList<Vaccination> listeDemandeVaccin() {

        ArrayList<Vaccination> l = new ArrayList<Vaccination>();

        Task<QuerySnapshot> t3 = db.collection("vaccinations").get().addOnFailureListener(this.onFailureListener());

        while (!erreur) {
            if (t3.isSuccessful()) {

                for (QueryDocumentSnapshot document : t3.getResult()) {

                    Vaccination v = new Vaccination();

                    v.id_vaccination = document.getId();
                    v.centre = document.getString("location");
                    v.date_vaccination = document.getString("first_dose_date");

                    l.add(v);
                }

                break;
            }
        }


        Log.d("message", "La taille : " + String.valueOf(l.size()));
        return l;
    }



    public void ajouterVaccination(String r, boolean b, String centre, String d) {

        if (b) {

            HashMap<String, Object> m = new HashMap<>();
            m.put("location", centre);
            m.put("first_dose_date", d);
            //m.put("request", db.document("/requests/" + r));
            m.put("request", r);
            m.put("vaccination_state", "");


            Task <DocumentReference> t = db.collection("vaccinations").add(m).addOnFailureListener(this.onFailureListener());

            while(!(t.isComplete() || erreur)){
                ;
            }


            Task t1 = db.collection("requests").document(r).update("request_state","v").addOnFailureListener(this.onFailureListener());

            while(!(t1.isComplete() || erreur)){
                ;
            }




        }
        else {
            Task t1 = db.collection("requests").document(r).update("request_state","n").addOnFailureListener(this.onFailureListener());

            while(!(t1.isComplete() || erreur)){
                ;
            }
        }


    }







    public void validerVaccination(String v, boolean b) {

        if (b) {

            Task t1 = db.collection("vaccinations").document(v).update("vaccination_state","v").addOnFailureListener(this.onFailureListener());

            while(!(t1.isComplete() || erreur)){
                ;
            }

        }
        else {
            Task t1 = db.collection("vaccinations").document(v).update("vaccination_state","n").addOnFailureListener(this.onFailureListener());

            while(!(t1.isComplete() || erreur)){
                ;
            }
        }


    }













    private OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                erreur=true;
                Log.w("Message", "Error getting documents."+ e.getStackTrace());
            }
        };
    }


}