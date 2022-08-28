package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class announcement extends AppCompatActivity {

    Button btnPost;
    EditText postMessage;
    DatabaseReference database;

    RecyclerView recyclerView;
    postAdapter postadapter;
    ArrayList<post> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        btnPost = findViewById(R.id.btnPost);
        postMessage = findViewById(R.id.postTxt);
        String coursename =getIntent().getStringExtra("course_name");


        btnPost.setOnClickListener(new View.OnClickListener() {//this is the button for pushing the specified post to the database
            @Override
            public void onClick(View view) {
                String inp = postMessage.getText().toString().trim();
                post obj = new post(inp);
                String coursecode=getIntent().getStringExtra("course_code");
                database = FirebaseDatabase.getInstance().getReference().child(coursename);
                database.child(coursecode).setValue(obj);
                Toast.makeText(announcement.this,"Announcement made",Toast.LENGTH_SHORT).show();
                postMessage.setText("");
            }
        });

        //The code below calls all posts on the database to be shown on the screen as previous posts

        recyclerView = findViewById(R.id.recAnnounce1);
        database = FirebaseDatabase.getInstance().getReference(coursename);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        postadapter = new postAdapter(this, list);
        recyclerView.setAdapter(postadapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    post posted = dataSnapshot.getValue(post.class);
                    list.add(posted);
                }
                postadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}