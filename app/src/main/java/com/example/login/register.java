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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText eRegEmail;
    EditText eRegPassword;
    TextView tvLoginHere;

    Button btnRegister;

    RadioGroup radioGroup;
    RadioButton radioButton;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setStatusBarColor(ContextCompat.getColor(register.this, R.color.teal_700));

        radioGroup = findViewById(R.id.radioGroup);


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

        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

        String occupation = radioButton.getText().toString();

        String email = eRegEmail.getText().toString().trim();
        String password = eRegPassword.getText().toString().trim();

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
                                User user = new User(occupation,password,email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            if(occupation.equals("Teacher")){
                                                Toast.makeText(register.this,"Teacher's account registered successfully",Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(register.this,"Student's account registered successfully",Toast.LENGTH_SHORT).show();
                                            }

                                            startActivity(new Intent(register.this,login.class));
                                        }else{
                                            Toast.makeText(register.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }else{
                                Toast.makeText(register.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                }
            });
        }
    }
}