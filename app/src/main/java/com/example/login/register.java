package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText eRegEmail;
    EditText eRegPassword;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setStatusBarColor(ContextCompat.getColor(register.this, R.color.teal_700));

        eRegEmail=findViewById(R.id.etRegEmail);
        eRegPassword=findViewById(R.id.etRegPass);
        tvLoginHere=findViewById(R.id.tvLoginHere);
        btnRegister=findViewById(R.id.btnRegister);

        mAuth=FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(register.this,login.class));
        });
    }

    private void createUser() {
        String email = eRegEmail.getText().toString();
        String password = eRegPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            eRegEmail.setError("Email cannot be empty");
            eRegEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            eRegPassword.setError("Password cannot be empty");
            eRegPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(register.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(register.this,login.class));
                            }else{
                                Toast.makeText(register.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                }
            });
        }
    }
}