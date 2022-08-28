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

public class TeacherAccount extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextInputEditText teacher_email,teacher_pass;
    String initialemail,initialpass;
    Button signout,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_account);

        //Create navigation bar
        createnavigationbar();

        //Get initial email and password
        getinitialEmailPass();

        teacher_email=findViewById(R.id.update_teacher_email);
        teacher_pass=findViewById(R.id.update_teacher_password);
        signout=findViewById(R.id.logout_teacher);
        update=findViewById(R.id.update_teacher_details);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherAccount.this,login.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedetails();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(TeacherAccount.this,login.class));
        }
    }

    private void getinitialEmailPass(){
        FirebaseDatabase.getInstance().getReference()
                .child("Teacher")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User teacher=snapshot.getValue(User.class);

                        initialemail=teacher.getEmail();
                        initialpass=teacher.getPassword();

                        teacher_email.setText(initialemail);
                        teacher_pass.setText(initialpass);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TeacherAccount.this,"Couldnt retrieve user information.",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createnavigationbar(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.teacher_account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.teacher_courses:
                        startActivity(new Intent(TeacherAccount.this,TeacherCourses.class).putExtra("activity","account"));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.add_module:
                        startActivity(new Intent(TeacherAccount.this,Teacher_New_Module.class).putExtra("activity","account"));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.teacher_account:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        /*
        if(getIntent().getStringExtra("activity")=="courses"){
            startActivity(new Intent(this, TeacherCourses.class));
        }else{
            startActivity(new Intent(this, Teacher_New_Module.class));
        }
        finish();
         */
    }

    private void updatedetails() {
        if(teacher_email.getText().toString().isEmpty()){
            teacher_email.setError("Email cannot be empty");
            teacher_email.requestFocus();
        }
        else if(teacher_pass.getText().toString().isEmpty()){
            teacher_pass.setError("Password cannot be empty");
            teacher_pass.requestFocus();
        }else{
            if(!initialemail.equals(teacher_email.getText().toString().trim()) || !initialpass.equals(teacher_pass.getText().toString().trim()) ){
                User user=new User(teacher_pass.getText().toString().trim(),teacher_email.getText().toString().trim());

                FirebaseAuth.getInstance().getCurrentUser().updateEmail(teacher_email.getText().toString().trim());
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(teacher_pass.getText().toString().trim());
                FirebaseDatabase.getInstance().getReference()
                        .child("Teacher")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(TeacherAccount.this,"Successfully updated details",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TeacherAccount.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                ;
            }else{
                Toast.makeText(TeacherAccount.this,"Successfully updated details",Toast.LENGTH_SHORT).show();
            }
        }
    }

}