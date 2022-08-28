package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class studentStartQuiz extends AppCompatActivity {
    Button btn_start_quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_start_quiz);
//        btn_start_quiz = findViewById(R.id.startQuiz);
//        btn_start_quiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(studentStartQuiz.this, StudentPlayQuiz.class));
//            }
//        });
    }

}