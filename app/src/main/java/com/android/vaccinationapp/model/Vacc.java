package com.android.vaccinationapp.model;

import com.google.firebase.firestore.DocumentId;

public class Vacc {
    @DocumentId
    private String document_id;
    private String first_dose_date;
    private String location;
    private String request;
    private String second_dose_date;
    private String vaccination_state;
    private String citizen;


    public String getFirst_dose_date() {
        return first_dose_date;
    }

    public void setFirst_dose_date(String first_dose_date) {
        this.first_dose_date = first_dose_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getSecond_dose_date() {
        return second_dose_date;
    }

    public void setSecond_dose_date(String second_dose_date) {
        this.second_dose_date = second_dose_date;
    }

    public String getVaccination_state() {
        return vaccination_state;
    }

    public void setVaccination_state(String vaccination_state) {
        this.vaccination_state = vaccination_state;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public Vacc() {}

    public Vacc(String first_dose_date, String location, String request, String second_dose_date, String vaccination_state) {
        this.first_dose_date = first_dose_date;
        this.location = location;
        this.request = request;
        this.second_dose_date = second_dose_date;
        this.vaccination_state = vaccination_state;
    }

}
