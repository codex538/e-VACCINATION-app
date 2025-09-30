package com.android.vaccinationapp.firestore;

public class UsersFirestoreDbContract {
    // Root collection name
    public static final String COLLECTION_NAME = "users";

    // Document ID
    public static final String DOCUMENT_ID = "document_id";

    // Document field names
    public static final String FIELD_FULL_NAME = "full_name";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHONE_NUMBER = "phone_number";
    public static final String FIELD_CIN = "cin";
    public static final String FIELD_AGE = "age";
    public static final String FIELD_ADDRESS = "address";

    private UsersFirestoreDbContract() {}
}
