package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class StudentCourses extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    Student_cart_adapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.student_courses);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(StudentCourses.this,Student_Dashboard.class).putExtra("activity","courses"));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.student_courses:
                        return true;
                    case R.id.student_account:
                        startActivity(new Intent(StudentCourses.this,StudentAccount.class).putExtra("activity","courses"));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_student_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);

        FirebaseRecyclerOptions<module> options =
                new FirebaseRecyclerOptions.Builder<module>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Enrol").child(FirebaseAuth.getInstance().getCurrentUser().getUid()), module.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        mainAdapter = new Student_cart_adapter(options,getApplicationContext());
        recyclerView.setAdapter(mainAdapter);


    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(StudentCourses.this,login.class));
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public void onBackPressed()
    {
        /*
        Intent intent=getIntent();
        String inte=intent.getStringExtra("acivity");
        Toast.makeText(this, inte, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, getIntent().getStringExtra("acivity"), Toast.LENGTH_SHORT).show();
        if(inte.equals("dashboard")){
            startActivity(new Intent(this, Student_Dashboard.class));
            bottomNavigationView.setSelectedItemId(R.id.dashboard);
        }else if(inte.equals("account")){
            startActivity(new Intent(this, StudentAccount.class));
            bottomNavigationView.setSelectedItemId(R.id.student_account);
        }
        finish();*/
    }
}