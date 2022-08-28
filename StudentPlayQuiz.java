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

public class StudentPlayQuiz extends AppCompatActivity {
    RecyclerView recyclerView;
    QuizAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_play_quiz);

        recyclerView = (RecyclerView)findViewById(R.id.quizRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);

        String coursecode=getIntent().getStringExtra("course_code");

        FirebaseRecyclerOptions<quizModel> options =
                new FirebaseRecyclerOptions.Builder<quizModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Questions").child(coursecode).child("QuizOne"), quizModel.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new QuizAdapter(options,getApplicationContext(),"");
        mainAdapter.startListening();
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