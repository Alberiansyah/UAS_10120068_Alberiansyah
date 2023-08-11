package com.example.uas_10120068_alberiansyah;

/**
 * NIM      : 10120068
 * Nama     : Alberiansyah
 * Kelas    : IF-2
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUp extends AppCompatActivity {

    EditText emailText, passwordText, passwordKonfirmasiText;
    Button daftarButton;
    ProgressBar progressBar;
    TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        passwordKonfirmasiText = findViewById(R.id.passwordKonfirmasi);
        daftarButton = findViewById(R.id.daftarButton);
        progressBar = findViewById(R.id.progressBar);
        loginButton = findViewById(R.id.loginText);

        daftarButton.setOnClickListener(v-> buatAkun());
        loginButton.setOnClickListener(v-> finish());


    }

    void buatAkun(){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordKonfirmasi = passwordKonfirmasiText.getText().toString();

        boolean isValidated = validate(email, password, passwordKonfirmasi);

        if(!isValidated){
            return;
        }

        buatAkunFirebase(email, password);
    }

    void buatAkunFirebase(String email, String password){
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInProgress(false);
                        if(task.isSuccessful()){
                            Utility.showToast(SignUp.this, "Berhasil membuat akun, silahkan konfirmasi email anda.");
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        }else{
                            Utility.showToast(SignUp.this, task.getException().getLocalizedMessage());
                        }
                    }
                }
        );

    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            daftarButton.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            daftarButton.setVisibility(View.VISIBLE);
        }
    }

    boolean validate(String email, String password, String passworkKonfirmasi){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Email tidak sesuai");
            return false;
        }

        if(password.length() < 5){
            passwordText.setError("Panjang password tidak sesuai minimal 5 karakter");
            return false;
        }

        if(!password.equals(passworkKonfirmasi)){
            passwordKonfirmasiText.setError("Password tidak sesuai");
            return false;
        }

        return true;
    }
}