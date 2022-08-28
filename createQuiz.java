package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

public class createQuiz extends AppCompatActivity {
    EditText addQuestion, addResponse, addOpt1, addOpt2, addOpt3, addOpt4, qNum;
   // public ArrayList<quizModel>  quizModelArrayList;
    Button addToList, closeCreateQuizActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        addQuestion = findViewById(R.id.createQuestion);
        addResponse = findViewById(R.id.createResponse);
        addOpt1 = findViewById(R.id.createOption1);
        addOpt2 = findViewById(R.id.createOption2);
        addOpt3 = findViewById(R.id.createOption3);
        addOpt4 = findViewById(R.id.createOption4);
        addToList = findViewById(R.id.addQuestionBtn);
        qNum = findViewById(R.id.qNum);
        closeCreateQuizActivity = findViewById(R.id.closeCreateCourseActivity);


       // quizModelArrayList = new ArrayList<>();


        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question =addQuestion.getText().toString();//The quiz question that will be asked in the quiz
                String response = addResponse.getText().toString();//the answer to the quiz questoin
                //System.out.println(response);
                //The 4 lines below are the quiz options that the user will see when the quiz runs
                String option1 = addOpt1.getText().toString();//getting the first option and storing it in a string so it can be saved into the database
                String option2 = addOpt2.getText().toString();//Adding the second option and ...
                String option3 = addOpt3.getText().toString();//Adding the third optionand ...
                String option4 = addOpt4.getText().toString();//Adding the fourth option and ...
                String qNumber = qNum.getText().toString();//the question number
                String coursecode=getIntent().getStringExtra("course_code");
               String quizName = getIntent().getStringExtra("quiz_name");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions").child(coursecode).child(quizName).child(qNumber);
                quizModel QuizQ = new quizModel(question, option1, option2, option3, option4, response);//Adding the quiz question to the database
                databaseReference.setValue(QuizQ);
                final String randomKey= UUID.randomUUID().toString();
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("QuizNames").child(coursecode)
                .child(randomKey);
                quizName quizName1 = new quizName(quizName);
                databaseReference1.setValue(quizName1);

                //the lines below are clearing the textview which were collecting the previous quiz data
                addOpt1.getText().clear();
                addOpt2.getText().clear();
                addOpt3.getText().clear();
                addOpt4.getText().clear();
                addQuestion.getText().clear();
                addResponse.getText().clear();
                qNum.getText().clear();
                Toast.makeText(createQuiz.this,"Question "+ qNumber +" Has been added",Toast.LENGTH_SHORT).show();//Questoin succesfully added


            }
        });

        closeCreateQuizActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createQuiz.this, TeacherCourse_content.class);
                startActivity(intent);
            }
        });


    }
}