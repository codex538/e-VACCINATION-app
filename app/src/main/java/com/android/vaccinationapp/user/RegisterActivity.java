package com.android.vaccinationapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vaccinationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = null;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore ;

    private EditText Fullname, Email ,Password, Cin, Age, Tel, Ville;
    private Button register;
    private TextView login;
    private ProgressBar progressBar;

    private String userID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progressBar2);

        Fullname = findViewById(R.id.inputName);
        Email = findViewById(R.id.inputEmail);
        Password = findViewById(R.id.inputPasswd);
        Cin = findViewById(R.id.inputCin);
        Age = findViewById(R.id.inputA);
        Tel = findViewById(R.id.inputNum);
        Ville = findViewById(R.id.inputVille);

        register = findViewById(R.id.btnRegister);
        login = findViewById(R.id.alreadyHaveAnAccount);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String fullname = Fullname.getText().toString().trim();
                String cin = Cin.getText().toString().trim();
                String age = Age.getText().toString().trim();
                String tel = Tel.getText().toString().trim();
                String ville = Ville.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    Email.setError("Veuillez saisir un email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Password.setError("Veuillez saisir un mot de passe");
                    return;
                }

                if(password.length() < 6){
                    Password.setError("Mot de passe très court");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID) ;
                            Map<String , Object> user = new HashMap<>();
                            user.put("address", ville);
                            user.put("age", Integer.valueOf(age));
                            user.put("cin", cin);
                            user.put("email", email);
                            user.put("full_name", fullname);
                            user.put("phone_number", tel);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG , "User profile created for" + userID) ;
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }


}