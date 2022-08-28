package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherCourses extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    Adapter_View_AllCourses mainAdapter;
    //TODO mess with the icon thing for the recourses over here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_courses);
        getWindow().setStatusBarColor(ContextCompat.getColor(TeacherCourses.this, R.color.teal_700));

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.teacher_courses);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.teacher_courses:
                    return true;
                case R.id.add_module:
                    startActivity(new Intent(TeacherCourses.this,Teacher_New_Module.class).putExtra("activity","courses"));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.teacher_account:
                    startActivity(new Intent(TeacherCourses.this,TeacherAccount.class).putExtra("activity","courses"));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });


        recyclerView = (RecyclerView)findViewById(R.id.recylerview_teacher_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<module> options =
                new FirebaseRecyclerOptions.Builder<module>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Group by teachers")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()), module.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        mainAdapter = new Adapter_View_AllCourses(options,getApplicationContext(),"edit_course");
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(TeacherCourses.this,login.class));
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