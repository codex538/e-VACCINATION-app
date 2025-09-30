package com.android.vaccinationapp.firestore;

public class RequestsFirestoreDbContract {
    // Root collection name
    public static final String COLLECTION_NAME = "requests";

    // Document ID
    public static final String DOCUMENT_ID = "document_id";

    // Document field names
    public static final String FIELD_CITIZEN = "citizen";
    public static final String FIELD_REQUEST_DATE = "request_date";
    public static final String FIELD_REQUEST_STATE = "request_state";

    private RequestsFirestoreDbContract() {}
}
