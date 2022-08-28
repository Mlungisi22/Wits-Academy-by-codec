package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private EditText eLoginEmail;
    private EditText eLoginPassword;
    private Button btnLogin;
    private TextView tvRegisterHere;
    private FirebaseAuth mAuth;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        radioGroup = findViewById(R.id.radioGroup1);//binds all input information to allow spave for image view

        eLoginEmail=findViewById(R.id.etLoginEmail);//
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

        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed()
    {
    }

    private void loginUser() {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        int radioID = radioGroup.getCheckedRadioButtonId();//see if user is student or teacher
        radioButton = findViewById(radioID);//user type found

        String occupation = radioButton.getText().toString();//Subject to change

        String email=eLoginEmail.getText().toString();
        String password=eLoginPassword.getText().toString();

        //checking email and input
        if(TextUtils.isEmpty(email)){
            //the elseif caters for when there no email information
            eLoginEmail.setError("Email cannot be empty");
            eLoginEmail.requestFocus();//focuses on the email interface waiting for input from user
        }//the elseif caters for when there no password information
        else if(TextUtils.isEmpty(password)){
            eLoginPassword.setError("Password cannot be empty");
            eLoginPassword.requestFocus();//focuses on the password state waiting for user to provide such of password
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference()
                                .child(occupation)
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("email").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String confirmemail;
                                try {
                                    confirmemail = snapshot.getValue(String.class).toString().trim();
                                }
                                catch(Exception e) {
                                    confirmemail="";
                                }

                                if(email.equals(confirmemail)){
                                    if(occupation.equals("Teacher")){
                                        startActivity(new Intent(login.this,TeacherCourses.class).putExtra("activity","login"));//Go to the teachers home page once approved
                                    }else if(occupation.equals("Student")){
                                        startActivity(new Intent(login.this,Student_Dashboard.class));//Go the student homepage once validated
                                    }
                                }else{
                                    Toast.makeText(login.this,"No "+occupation+"'s account with those details",Toast.LENGTH_SHORT).show();

                                }

                            }
//my comment on 20220512
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        Toast.makeText(login.this,"Log in error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();//it is too show errors and error types should they arise
                    }
                }
            });
        }
    }
}