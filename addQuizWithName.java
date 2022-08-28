package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addQuizWithName extends AppCompatActivity {
    private EditText quizName;
    private Button addQuestions;
    String courseCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz_with_name);

        quizName=findViewById(R.id.quizId);
        addQuestions=findViewById(R.id.addQuestions);
        courseCode=getIntent().getStringExtra("course_code");


        addQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quizName1 = quizName.getText().toString();
                Intent intent=new Intent(addQuizWithName.this, createQuiz.class);
                intent.putExtra("quiz_name", quizName1);
                intent.putExtra("course_code", courseCode);
                startActivity(intent);
            }
        });
    }
}