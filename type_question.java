package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.UUID;

public class type_question extends AppCompatActivity {
    EditText question,heading;
    Button submit_question,go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_question);

        question=findViewById(R.id.type_question);
        heading=findViewById(R.id.type_question_heading);
        submit_question=findViewById(R.id.type_submit_question);
        go_back=findViewById(R.id.question_go_back);

        String coursecode=getIntent().getStringExtra("course_code");
        String coursename=getIntent().getStringExtra("course_name");
        String teacher=getIntent().getStringExtra("course_teacher");
        String courseid=getIntent().getStringExtra("course_id");
        String identifier=getIntent().getStringExtra("identifier");
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        submit_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question_content=question.getText().toString();
                String heading_content=heading.getText().toString();

                if(!question_content.isEmpty()){
                    //String coursecode="COMSTRYING1018";
                    //String coursecode=getIntent().getStringExtra("course_code");
                    //String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    //String email="random@email";
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Forum").child(coursecode);
                    model_posting_question Module = new model_posting_question(email,heading_content,question_content);
                    final String randomkey= UUID.randomUUID().toString();
                    databaseReference.child(randomkey).setValue(Module);
                    Toast.makeText(type_question.this, "Question submitted", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(type_question.this, "Question cannot be empty", Toast.LENGTH_SHORT).show();
                }
                question.setText("");
                heading.setText("");
            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whichactivity=null;
                if(getIntent().getStringExtra("dashboard_or_mycourses")!=null){
                    whichactivity=getIntent().getStringExtra("dashboard_or_mycourses");
                }
                Intent intent=new Intent(type_question.this,Forum.class);
                intent.putExtra("course_code",coursecode);
                intent.putExtra("course_name",coursename);
                intent.putExtra("course_teacher",teacher);
                intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                intent.putExtra("identifier",identifier);
                intent.putExtra("dashboard_or_mycourses",whichactivity);
                startActivity(intent);
            }
        });

    }
}