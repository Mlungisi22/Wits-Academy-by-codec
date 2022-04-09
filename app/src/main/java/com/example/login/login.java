package com.example.login;
// this is thapelo's first comment
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    EditText eLoginEmail;
    EditText eLoginPassword;
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
        }
        else if(TextUtils.isEmpty(password)){
            eLoginPassword.setError("Password cannot be empty");
            eLoginPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseDatabase.getInstance().getReference()
                                .child("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("occupation").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String occupation;
                                try {
                                    occupation = snapshot.getValue(String.class).toString().trim();
                                }
                                catch(Exception e) {
                                    occupation="";
                                }

                                Toast.makeText(login.this,occupation,Toast.LENGTH_SHORT).show();
                                if(occupation.equals("Teacher")){
                                    startActivity(new Intent(login.this,MainActivity.class));//Go to the teachers home page
                                }else{
                                    startActivity(new Intent(login.this,Student_Homepage.class));//Go the student homepage
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        /*Toast.makeText(login.this,"User logged in successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this,MainActivity.class));*/
                    }else{
                        Toast.makeText(login.this,"Log in error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}