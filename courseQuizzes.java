package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class courseQuizzes extends AppCompatActivity {
    RecyclerView recyclerView;
    quizListAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_quizzes);

        recyclerView = (RecyclerView)findViewById(R.id.quizList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        String coursecode=getIntent().getStringExtra("course_code");
        final String randomKey = UUID.randomUUID().toString();
        FirebaseRecyclerOptions<quizName> options =
                new FirebaseRecyclerOptions.Builder<quizName>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("QuizNames").child(coursecode), quizName.class)
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new quizListAdapter(options,getApplicationContext(),"", coursecode);

        recyclerView.setAdapter(mainAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}