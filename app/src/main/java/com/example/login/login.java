package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    TextInputEditText eLoginEmail;
    TextInputEditText eLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eLoginEmail=findViewById(R.id.etLoginEmail);
        eLoginPassword=findViewById(R.id.etLoginPass);
        tvRegisterHere=findViewById(R.id.tvRegisterHere);
        btnLogin=findViewById(R.id.btnLogin);

        mAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view ->{
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            startActivity(new Intent(login.this,register.class));
        });
    }

    private void loginUser() {
        String email=eLoginEmail.getText().toString();
        String password=eLoginPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            eLoginEmail.setError("Email cannot be empty");
            eLoginEmail.requestFocus();
        }else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eLoginEmail.setError("Enter correct email");
            eLoginEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            eLoginPassword.setError("Password cannot be empty");
            eLoginPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(login.this,"User logged in successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this,MainActivity.class));
                    }else{
                        Toast.makeText(login.this,"Log in error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}