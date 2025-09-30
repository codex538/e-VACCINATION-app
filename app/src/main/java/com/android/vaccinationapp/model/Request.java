package com.android.vaccinationapp.model;

import com.google.firebase.firestore.DocumentId;

public class Request {
    @DocumentId
    private String document_id;
    private String citizen;
    private String request_date;
    private String request_state;

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getRequest_state() {
        return request_state;
    }

    public void setRequest_state(String request_state) {
        this.request_state = request_state;
    }

    public Request() {}

    public Request(String citizen, String request_date, String request_state) {
        this.citizen = citizen;
        this.request_date = request_date;
        this.request_state = request_state;
    }

}
