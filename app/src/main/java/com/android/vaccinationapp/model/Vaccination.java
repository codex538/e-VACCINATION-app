package com.android.vaccinationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vaccination extends CitizenRequest implements Parcelable {

    public String id_vaccination;
    public String date_vaccination;
    public String centre;
    public String vaccination_state;

    public static final Creator<Vaccination> CREATOR = new Creator<Vaccination>() {
        @Override
        public Vaccination createFromParcel(Parcel source) {
            return new Vaccination(source);
        }

        @Override
        public Vaccination[] newArray(int size) {
            return new Vaccination[size];
        }
    };

    public Vaccination(Parcel in) {
        super(in);
        id_vaccination = in.readString();
        date_vaccination = in.readString();
        centre = in.readString();
        vaccination_state = in.readString();
    }

    public Vaccination() {

    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(id_vaccination);
        out.writeString(date_vaccination);
        out.writeString(centre);
        out.writeString(vaccination_state);
    }


}