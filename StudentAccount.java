package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentAccount extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextInputEditText student_email, student_pass;
    String initialemail,initialpass;
    Button signout,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.student_account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(StudentAccount.this,Student_Dashboard.class).putExtra("activity","account"));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.student_courses:
                        startActivity(new Intent(StudentAccount.this,StudentCourses.class).putExtra("activity","account"));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.student_account:
                        return true;
                }
                return false;
            }
        });

        FirebaseDatabase.getInstance().getReference()
                .child("Student")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User teacher=snapshot.getValue(User.class);

                        initialemail=teacher.getEmail();
                        initialpass=teacher.getPassword();

                        student_email.setText(initialemail);
                        student_pass.setText(initialpass);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(StudentAccount.this,"Couldnt retrieve user information.",Toast.LENGTH_SHORT).show();
                    }
                });

        student_email =findViewById(R.id.update_student_email);
        student_pass =findViewById(R.id.update_student_password);
        signout=findViewById(R.id.logout_student);
        update=findViewById(R.id.update_student_details);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentAccount.this,login.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedetails();
            }
        });

    }

    private void updatedetails() {
        if(student_email.getText().toString().isEmpty()){
            student_email.setError("Email cannot be empty");
            student_email.requestFocus();
        }
        else if(student_pass.getText().toString().isEmpty()){
            student_pass.setError("Password cannot be empty");
            student_pass.requestFocus();
        }else{
            if(!initialemail.equals(student_email.getText().toString().trim()) || !initialpass.equals(student_pass.getText().toString().trim()) ){
                User user=new User(student_pass.getText().toString().trim(), student_email.getText().toString().trim());

                FirebaseAuth.getInstance().getCurrentUser().updateEmail(student_email.getText().toString().trim());
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(student_pass.getText().toString().trim());
                FirebaseDatabase.getInstance().getReference()
                        .child("Student")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(StudentAccount.this,"Successfully updated details",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(StudentAccount.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                ;
            }else{
                Toast.makeText(StudentAccount.this,"Successfully updated details",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(StudentAccount.this,login.class));
        }
    }


    @Override
    public void onBackPressed()
    {
        /*
        Intent intent=getIntent();
        String inte=intent.getStringExtra("acivity");
        Toast.makeText(this, inte, Toast.LENGTH_SHORT).show();
        if(inte.equals("dashboard")){
            startActivity(new Intent(this, Student_Dashboard.class));
            bottomNavigationView.setSelectedItemId(R.id.dashboard);
        }else if(inte.equals("courses")){
            startActivity(new Intent(this, StudentAccount.class));
            bottomNavigationView.setSelectedItemId(R.id.student_account);
        }
        finish();*/
    }

}