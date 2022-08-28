package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Forum extends AppCompatActivity {
    Button askquestion;
    RecyclerView recyclerView;
    each_question_adapter mainAdapter;
    Button go_back_coursecontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        askquestion=findViewById(R.id.ask_question);

        recyclerView = (RecyclerView)findViewById(R.id.recycle_question);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        go_back_coursecontent=findViewById(R.id.go_back_teacher_course_content);

        String coursecode=getIntent().getStringExtra("course_code");
        String coursename=getIntent().getStringExtra("course_name");
        String teacher=getIntent().getStringExtra("course_teacher");
        //String courseid=getIntent().getStringExtra("course_id");
        String identifier=getIntent().getStringExtra("identifier");

        //String coursecode="COMSTRYING1018";

        go_back_coursecontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(identifier.equals("teacher")){
                    Intent intent=new Intent(Forum.this,TeacherCourse_content.class);
                    intent.putExtra("course_name",coursename);
                    intent.putExtra("course_teacher",teacher);
                    intent.putExtra("course_code",coursecode);
                    intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent.putExtra("identifier",identifier);
                    startActivity(intent);
                }
                else{
                    String whichactivity=getIntent().getStringExtra("dashboard_or_mycourses");
                    if(whichactivity.equals("subscribe")){
                        Intent intent=new Intent(Forum.this,Student_Dashboard.class);
                        intent.putExtra("course_name",coursename);
                        intent.putExtra("course_teacher",teacher);
                        intent.putExtra("course_code",coursecode);
                        intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        intent.putExtra("identifier",identifier);
                        intent.putExtra("dashboard_or_mycourses",whichactivity);
                        startActivity(intent);
                    }else{
                        Intent intent=new Intent(Forum.this,StudentCourse_content.class);
                        intent.putExtra("course_name",coursename);
                        intent.putExtra("course_teacher",teacher);
                        intent.putExtra("course_code",coursecode);
                        intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        intent.putExtra("identifier",identifier);
                        intent.putExtra("dashboard_or_mycourses",whichactivity);
                        startActivity(intent);
                    }


                }

            }
        });

        String whichactivity1=null;
        if(getIntent().getStringExtra("dashboard_or_mycourses")!=null){
            whichactivity1=getIntent().getStringExtra("dashboard_or_mycourses");
        }
        FirebaseRecyclerOptions<model_posting_question> options =
                new FirebaseRecyclerOptions.Builder<model_posting_question>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Forum").child(coursecode), model_posting_question.class)
                        .build();
        mainAdapter = new each_question_adapter(options,getApplicationContext(),coursecode,coursename,identifier,teacher,whichactivity1);
        recyclerView.setAdapter(mainAdapter);

        askquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whichactivity=null;
                if(getIntent().getStringExtra("dashboard_or_mycourses")!=null){
                    whichactivity=getIntent().getStringExtra("dashboard_or_mycourses");
                }
                Intent intent=new Intent(Forum.this,type_question.class);
                intent.putExtra("course_code",coursecode);
                intent.putExtra("course_name",coursename);
                intent.putExtra("course_teacher",teacher);
                intent.putExtra("course_code",coursecode);
                intent.putExtra("course_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                intent.putExtra("identifier",identifier);
                intent.putExtra("dashboard_or_mycourses",whichactivity);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}