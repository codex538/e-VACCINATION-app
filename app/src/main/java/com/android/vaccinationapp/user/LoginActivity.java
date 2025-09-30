package com.android.vaccinationapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vaccinationapp.R;
import com.android.vaccinationapp.admin.AccueilAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth fAuth;

    private EditText Email;
    private EditText Password;
    private ProgressBar progressBar;
    private Button btnlogin;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.inputEmail);
        Password = findViewById(R.id.inputPassword);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();

        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (password.length() < 6) {
                    Password.setError("Le mot de passe doit être >= 6 characters");
                    return;
                }

                if (!(email.isEmpty() || password.isEmpty())) {
                    signIn(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Veuillez saisir des identifiants valides!", Toast.LENGTH_SHORT)
                            .show();
                }

                progressBar.setVisibility(View.VISIBLE);

            }
        });

        signup = findViewById(R.id.txtViewSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    private void signIn(String email, String password) {
        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = fAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }


    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Vous vous êtes connecté avec succès", Toast.LENGTH_SHORT).show();

            if (user.getEmail().equals("admin@e.com"))
                startActivity(new Intent(this, AccueilAdmin.class));
            else
                startActivity(new Intent(this, DashboardActivity.class).putExtra("currUser", user));

        } /*else {
            Toast.makeText(this, "Il n'existe pas de compte avec ces données", Toast.LENGTH_LONG).show();
        }*/


    }
}