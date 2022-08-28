package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Student_Dashboard extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    Adapter_View_AllCourses mainAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        getWindow().setStatusBarColor(ContextCompat.getColor(Student_Dashboard.this, R.color.teal_700));

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.dashboard:
                    return true;
                case R.id.student_courses:
                    startActivity(new Intent(Student_Dashboard.this,StudentCourses.class).putExtra("activity","dashboard"));//leads to the students courses that they they subscribeed for
                    overridePendingTransition(0,0);
                    return true;
                case R.id.student_account:
                    startActivity(new Intent(Student_Dashboard.this,StudentAccount.class).putExtra("activity","dashboard"));//Student details like login an password
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        recyclerView = (RecyclerView)findViewById(R.id.recylerview_all_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        retriveallcourses();

        searchView=findViewById(R.id.student_search_course);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filteredCourses(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filteredCourses(s);
                return true;
            }
        });
    }

    private void retriveallcourses() {
        //Retrieving courses from the database
        FirebaseRecyclerOptions<module> options =
                new FirebaseRecyclerOptions.Builder<module>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("All Courses"), module.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new Adapter_View_AllCourses(options,getApplicationContext(),"");
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

    private void filteredCourses(String s) {
        if(s.equals("")){
            retriveallcourses();
        }else{
            FirebaseRecyclerOptions<module> options =
                    new FirebaseRecyclerOptions.Builder<module>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("All Courses").orderByChild("modName").startAt(s).endAt(s+"~"), module.class)//.orderByChild("modName").equalTo("APHY8010")
                            .build();
            recyclerView.getRecycledViewPool().clear();
            mainAdapter = new Adapter_View_AllCourses(options,getApplicationContext(),"");
            mainAdapter.startListening();
            recyclerView.setAdapter(mainAdapter);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(Student_Dashboard.this,login.class));
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

    }
}