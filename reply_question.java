package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.UUID;

public class reply_question extends AppCompatActivity {
    Button submit_reply,submit_reply_back;
    EditText reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_question);

        reply=findViewById(R.id.reply_question);
        submit_reply=findViewById(R.id.reply_submit);
        submit_reply_back=findViewById(R.id.reply_go_back);

        String questioncontent=getIntent().getStringExtra("question");
        String headingcontent=getIntent().getStringExtra("heading");
        String emailcontent=getIntent().getStringExtra("email");
        String coursecode=getIntent().getStringExtra("course_code");
        String coursename=getIntent().getStringExtra("course_name");
        String teacher=getIntent().getStringExtra("course_teacher");
        String identifier=getIntent().getStringExtra("identifier");

        submit_reply_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whichactivity=null;
                if(getIntent().getStringExtra("dashboard_or_mycourses")!=null){
                    whichactivity=getIntent().getStringExtra("dashboard_or_mycourses");
                }
                Intent intent=new Intent(reply_question.this,inside_question.class);
                intent.putExtra("question",questioncontent);
                intent.putExtra("heading",headingcontent);
                intent.putExtra("email",emailcontent);
                intent.putExtra("course_code",coursecode);
                intent.putExtra("coursename",coursename);
                intent.putExtra("course_teacher",teacher);
                intent.putExtra("identifier",identifier);
                intent.putExtra("dashboard_or_mycourses",whichactivity);
                startActivity(intent);
            }
        });



       submit_reply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String reply_content=reply.getText().toString();
               if(!reply_content.isEmpty()){

                   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ForumAnswers").child(coursecode).child(questioncontent);
                   model_reply_question Module = new model_reply_question(emailcontent,reply_content);
                   final String randomkey= UUID.randomUUID().toString();
                   databaseReference.child(randomkey).setValue(Module);
                   Toast.makeText(reply_question.this, "replied", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(reply_question.this, "Cannot submit empty reply", Toast.LENGTH_SHORT).show();
               }
               reply.setText("");
           }
       });
    }
}