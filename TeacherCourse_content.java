package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class TeacherCourse_content extends AppCompatActivity {
    TextView coursename,courseinst,refreshpage;
    EditText coursedesc, file_name;
    String coursename1,courseinstructor,coursecode1,courseid;

    Button uploadpdf,choosepdf,announcement;
    Button updatedesc,goback, btn_add_file, btn_add_quiz, btn_upload, btn_choose_file, listQuizzes, btn_go_to_forum;

    RecyclerView recyclerView;
    All_pdf_adapter mainAdapter;
    ConstraintLayout cl_content;
    LayoutInflater inflater;
    View popupView;
    PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course_content);

        courseinst=findViewById(R.id.instructor_name);
        coursename=findViewById(R.id.course_name);
        coursedesc=findViewById(R.id.edit_course_desc);
        btn_add_file = findViewById(R.id.btn_add_file);
        btn_add_quiz = findViewById(R.id.btn_add_quiz);
        listQuizzes= findViewById(R.id.viewQuizzes);
//        uploadpdf=findViewById(R.id.btn_add_quiz);
        updatedesc=findViewById(R.id.update_description);
        goback=findViewById(R.id.go_back);
        announcement = findViewById(R.id.btnAnnouncement);
        btn_go_to_forum=findViewById(R.id.btn_go_to_forum);


        coursename1=getIntent().getStringExtra("course_name");
        courseinstructor=getIntent().getStringExtra("course_teacher");
        coursecode1=getIntent().getStringExtra("course_code");


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherCourse_content.this,TeacherCourses.class));
            }
        });


        coursename1=getIntent().getStringExtra("course_name");//one of the teachers courses
        courseinstructor=getIntent().getStringExtra("course_teacher");//the name of the imnstructor thats tecahing the courses
        coursecode1=getIntent().getStringExtra("course_code");//course code set by instructor

        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TeacherCourse_content.this, announcement.class);
                intent.putExtra("course_code",coursecode1);
                intent.putExtra("course_name",coursename1);
                startActivity(intent);
            }
        });


        btn_add_quiz.setOnClickListener(new View.OnClickListener() {
            //Creating a quiz for the course
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TeacherCourse_content.this, addQuizWithName.class);
                intent.putExtra("course_code",coursename1);
                startActivity(intent);
            }
        });

        listQuizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherCourse_content.this, courseQuizzes.class);
                intent.putExtra("course_code",coursename1);
                startActivity(intent);
               // startActivity(new Intent(TeacherCourse_content.this,TeacherCourses.class));
            }
        });

        btn_go_to_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TeacherCourse_content.this,Forum.class);
                intent.putExtra("course_code",coursecode1);
                intent.putExtra("course_name",coursename1);
                intent.putExtra("course_teacher",courseinstructor);
                intent.putExtra("identifier","teacher");
                startActivity(intent);
            }
        });



        courseinst.setText(courseinstructor);
        coursename.setText(coursecode1);

        FirebaseDatabase.getInstance().getReference("Course Description").child(coursename1)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        coursedesc.setText(snapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TeacherCourse_content.this, "Couldnt retrieve course description", Toast.LENGTH_SHORT).show();
                    }
                });

        updatedesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDescription();
            }
        });

        cl_content = findViewById(R.id.cl_content);

        inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_upload_content, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setHeight(1000);
        popupWindow.setWidth(800);

        btn_upload = popupView.findViewById(R.id.btn_upload);
        btn_choose_file = popupView.findViewById(R.id.btn_choose);
        file_name = popupView.findViewById(R.id.et_file_name);

        btn_upload.setEnabled(false);
        btn_add_file.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                addFile();
            }
        });



        recyclerView = (RecyclerView)findViewById(R.id.course_pdfs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        retrieveFile();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void updateDescription() {
        if(coursedesc.getText().toString()!=null){
            FirebaseDatabase.getInstance().getReference("Course Description").child(coursename1).setValue(coursedesc.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(TeacherCourse_content.this, "Successfully updated course", Toast.LENGTH_SHORT).show();//message diplayed whem successully changed discription
                        }
                    });
        }else{
            FirebaseDatabase.getInstance().getReference("Course Description").child(coursename1).setValue("None")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(TeacherCourse_content.this, "Successfully updated course", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(TeacherCourse_content.this,TeacherCourses.class));
    }

    private void retrieveFile() {
        recyclerView.setItemAnimator(null);
        FirebaseRecyclerOptions<uploadpdf> options =
                new FirebaseRecyclerOptions.Builder<uploadpdf>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Course Material").child(coursename1), uploadpdf.class)
                        .build();
        mainAdapter = new All_pdf_adapter(options,getApplicationContext(), this.getIntent(),true);
        recyclerView.setAdapter(mainAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addFile() {

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(cl_content, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });


        Button btn_choose_file = popupView.findViewById(R.id.btn_choose);
        Button btn_upload = popupView.findViewById(R.id.btn_upload);

        btn_upload.setEnabled(false);
        btn_choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Button btn_upload = popupView.findViewById(R.id.btn_upload);
            btn_upload.setEnabled(true);
            EditText file_name = popupView.findViewById(R.id.et_file_name);
            file_name.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            btn_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    uploadFileFirebase(data.getData());
                    Intent intent=new Intent(TeacherCourse_content.this,TeacherCourse_content.class);
                    intent.putExtra("course_name",coursename1);
                    intent.putExtra("course_teacher",courseinstructor);
                    intent.putExtra("course_code",coursecode1);
                    startActivity(intent);
//                    finish();

                }
            });
        }else{
            Toast.makeText(TeacherCourse_content.this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectFile() {
        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECt"),12);

    }
    private void uploadFileFirebase(Uri data){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("File is loading.....");
        progressDialog.show();

        StorageTask<UploadTask.TaskSnapshot> reference= FirebaseStorage.getInstance().getReference("Lecture_Notes").child(coursecode1)
                .putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri=uriTask.getResult();

                        uploadpdf uploadpdf=new uploadpdf(file_name.getText().toString(),uri.toString());
                        FirebaseDatabase.getInstance().getReference("Course Material")
                                .child(coursename1)
                                .child(uploadpdf.getPdfname())
                                .setValue(uploadpdf);
                        file_name.setText("");
                        Toast.makeText(TeacherCourse_content.this, "File Uploaded", Toast.LENGTH_SHORT).show();



                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploading..."+(int)progress+"%");
                    }
                });

        retrieveFile();


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