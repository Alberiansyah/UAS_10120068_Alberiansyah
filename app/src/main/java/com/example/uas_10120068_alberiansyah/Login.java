package com.example.uas_10120068_alberiansyah;

/**
 * NIM      : 10120068
 * Nama     : Alberiansyah
 * Kelas    : IF-2
 */

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
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
//            Utility.showToast(Login.this, "Email/Password tidak valid, pastikan anda sudah konfirmasi email.");
            return;
        }

        loginAccountInFireBase(email, password);
        // Menghilangkan keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(loginButton.getWindowToken(), 0);
    }

    void loginAccountInFireBase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser != null && currentUser.isEmailVerified()) {
                        Utility.showToast(Login.this, "Selamat datang.");
                        startActivity(new Intent(getApplicationContext(), com.example.uas_10120068_alberiansyah.MainActivity.class));
                        finish();
                    } else {
                        Utility.showToast(Login.this, "Email belum terverifikasi, silahkan cek email anda.");
                    }
                } else {
                    Utility.showToast(Login.this, "Email/Password tidak valid.");
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