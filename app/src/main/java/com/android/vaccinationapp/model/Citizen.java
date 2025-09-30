package com.android.vaccinationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Citizen implements Parcelable {

    public String id;
    public String full_name;
    public String email;
    public String password;
    public String phone_number;
    public String cin;
    public int age;
    public String address;

    public static final Creator<Citizen> CREATOR = new Creator<Citizen>() {
        @Override
        public Citizen createFromParcel(Parcel source) {
            return new Citizen(source);
        }

        @Override
        public Citizen[] newArray(int size) {
            return new Citizen[size];
        }
    };

    public Citizen(Parcel in) {
        id = in.readString();
        full_name = in.readString();
        email = in.readString();
        password = in.readString();
        phone_number = in.readString();
        cin = in.readString();
        age = in.readInt();
        address = in.readString();
    }

    public Citizen(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(full_name);
        out.writeString(email);
        out.writeString(password);
        out.writeString(phone_number);
        out.writeString(cin);
        out.writeInt(age);
        out.writeString(address);
    }
}