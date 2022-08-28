package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentCourse_content extends AppCompatActivity {
    TextView coursename,coursedesc,courseinst,coursecode;
    Button subscribe,gotocourses, btn_take_quiz, btn_ann,btn_forum;
    Boolean subscribed=false;
    int ratingNum;
    String coursename1,courseinstructor,coursecode1,courseid;
    RecyclerView recyclerView;
    All_pdf_adapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_content);
        getWindow().setStatusBarColor(ContextCompat.getColor(StudentCourse_content.this, R.color.teal_700));

        courseinst=findViewById(R.id.instructor_name);
        coursename=findViewById(R.id.course_name);
        coursedesc=findViewById(R.id.course_description);
        subscribe=findViewById(R.id.subscribe);
        gotocourses=findViewById(R.id.enrolled_courses);
        btn_take_quiz = findViewById(R.id.btn_take_quiz);
        btn_ann = findViewById(R.id.btn_announcements);
        btn_forum=findViewById(R.id.enrolled_courses_forum);


        coursename1=getIntent().getStringExtra("course_name");
        courseinstructor=getIntent().getStringExtra("course_teacher");
        coursecode1=getIntent().getStringExtra("course_code");
        courseid=getIntent().getStringExtra("course_id");

        courseinst.setText(courseinstructor);
        coursename.setText(coursecode1);

        recyclerView = (RecyclerView)findViewById(R.id.show_pdfs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);

        retrievepdf();


        FirebaseDatabase.getInstance().getReference("Course Description").child(coursename1)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        coursedesc.setText(snapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(StudentCourse_content.this, "Couldnt load course description", Toast.LENGTH_SHORT).show();
                    }
                });


        if(getIntent().getStringExtra("dashboard_or_mycourses").equals("subscribed")){
            subscribe.setEnabled(false);
            subscribe.setText("Subscribed");
        }
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!subscribed){

                    module module=new module(coursename1,coursecode1,courseinstructor,ratingNum);
                    FirebaseDatabase.getInstance().getReference("Enrol")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(coursecode1)
                            .setValue(module)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(StudentCourse_content.this,"Successfully subscribed to the course",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    subscribe.setText("Subscribed");
                    subscribed=true;
                }else{
                    Toast.makeText(StudentCourse_content.this,"Already subscribed to the course",Toast.LENGTH_SHORT).show();
                }
            }
        });

        gotocourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentCourse_content.this,StudentCourses.class));
            }
        });

        btn_take_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentCourse_content.this, StudentPlayQuiz.class);
                intent.putExtra("course_code",coursename1) ;
            startActivity(intent);
            }
        });

        btn_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whichactivity=getIntent().getStringExtra("dashboard_or_mycourses");
                Intent intent=new Intent(StudentCourse_content.this,Forum.class);
                intent.putExtra("course_code",courseid);
                intent.putExtra("course_code",coursecode1);
                intent.putExtra("course_name",coursename1);
                intent.putExtra("course_teacher",courseinstructor);
                intent.putExtra("identifier","student");
                intent.putExtra("dashboard_or_mycourses",whichactivity);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        finish();
    }

    private void retrievepdf() {
        FirebaseRecyclerOptions<uploadpdf> options =
                new FirebaseRecyclerOptions.Builder<uploadpdf>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Course Material").child(coursename1), uploadpdf.class)//.orderByChild("modName").equalTo("APHY8010")
                        .build();
        mainAdapter = new All_pdf_adapter(options,getApplicationContext(), false);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(StudentCourse_content.this,login.class));
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}