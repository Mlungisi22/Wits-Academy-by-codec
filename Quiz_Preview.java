package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Quiz_Preview extends AppCompatActivity {
    RecyclerView recyclerView;
    teacherPreviewAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_preview);


        recyclerView = (RecyclerView)findViewById(R.id.quiz_preview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        String quizname =getIntent().getStringExtra("quiz_name");
        String coursename=getIntent().getStringExtra("course_code");


        FirebaseRecyclerOptions<quizModel> options =
                new FirebaseRecyclerOptions.Builder<quizModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Questions").child(coursename).child(quizname), quizModel.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        recyclerView.getRecycledViewPool().clear();
        mainAdapter = new teacherPreviewAdapter(options,getApplicationContext(),"");
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