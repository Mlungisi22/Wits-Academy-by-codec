package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Teacher_New_Module extends AppCompatActivity {
    EditText modName;
    EditText modCode;
    EditText modTeach,imagename;
    Button createMod,chooseim,uploadim;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference,databaseReference1;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_new_module);
        getWindow().setStatusBarColor(ContextCompat.getColor(Teacher_New_Module.this, R.color.teal_700));

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add_module);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.teacher_courses:
                    startActivity(new Intent(Teacher_New_Module.this,TeacherCourses.class).putExtra("activity","new_module"));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.add_module:
                    return true;
                case R.id.teacher_account:
                    startActivity(new Intent(Teacher_New_Module.this,TeacherAccount.class).putExtra("activity","new_module"));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        modName = findViewById(R.id.moduleName);
        modCode = findViewById(R.id.moduleCode);
        modTeach = findViewById(R.id.moduleTName);
        createMod = findViewById(R.id.createCourse);

        mAuth = FirebaseAuth.getInstance();

        createMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createModule();
                modName.setText("");
                modCode.setText("");
                modTeach.setText("");
            }
        });

        
    }
    @Override
    public void onBackPressed()
    {
        /*
        if(getIntent().getStringExtra("activity")=="courses"){
            startActivity(new Intent(this, TeacherCourses.class));
        }else{
            startActivity(new Intent(this, TeacherAccount.class));
        }
        finish();
         */
    }

    public void createModule(){
        String mName = modName.getText().toString().trim();
        String mCode = modCode.getText().toString().trim();
        String mTeach = modTeach.getText().toString().trim();

        if(TextUtils.isEmpty(mName)){
            modName.setError("Course name cannot be empty");
            modName.requestFocus();
        }else if(TextUtils.isEmpty(mCode)){
            modCode.setError("Course code cannot be empty");
            modCode.requestFocus();
        }else if(TextUtils.isEmpty(mTeach)){
            modTeach.setError("Instructors name cannot be empty");
            modTeach.requestFocus();
        }else{
            databaseReference = FirebaseDatabase.getInstance().getReference().child("All Courses");
            module Module = new module(mName, mCode, mTeach, 0);
            databaseReference.child(mCode).setValue(Module);
            Toast.makeText(Teacher_New_Module.this, "Course created", Toast.LENGTH_SHORT).show();

            databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Group by teachers");
            module Module1 = new module(mName, mCode, mTeach, 0);
            databaseReference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(mCode).setValue(Module1);

        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(Teacher_New_Module.this,login.class));
        }
    }
}