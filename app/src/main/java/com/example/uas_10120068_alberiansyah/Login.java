package com.example.uas_10120068_alberiansyah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    EditText emailText, passwordText;
    Button loginButton;
    ProgressBar progressBar;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        signUpText = findViewById(R.id.signUpText);

        loginButton.setOnClickListener((v)->loginUser());
        signUpText.setOnClickListener((v)->startActivity(new Intent(Login.this, SignUp.class)));
    }

    void loginUser(){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        boolean isValidated = validate(email, password);

        if(!isValidated){
            return;
        }

        loginAccountInFireBase(email, password);


    }

    void loginAccountInFireBase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        Utility.showToast(Login.this, "Selamat datang.");
                        startActivity(new Intent(getApplicationContext(), com.example.uas_10120068_alberiansyah.MainActivity.class));
                        finish();
                    }else{
                        Utility.showToast(Login.this, "Email belum terverifikasi, Mohon verifikasi email anda.");
                    }
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }

    boolean validate(String email, String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Email tidak sesuai");
            return false;
        }

        if(password.length() < 5){
            passwordText.setError("Panjang password tidak sesuai minimal 5 karakter");
            return false;
        }

        return true;
    }
}