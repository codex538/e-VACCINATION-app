package com.android.vaccinationapp.model;

import com.google.firebase.firestore.DocumentId;

public class User {
        @DocumentId
        private String document_id;
        private String full_name;
        private String email;
        private String phone_number;
        private String cin;
        private int age;
        private String address;

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

        public User() {}

        public User(String full_name, String email, String phone_number, String cin, int age, String address) {
            this.full_name = full_name;
            this.email = email;
            this.phone_number = phone_number;
            this.cin = cin;
            this.age = age;
            this.address = address;
        }


}
