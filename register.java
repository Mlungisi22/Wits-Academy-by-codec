package com.example.login;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.ContextCompat;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Patterns;
import android.view.View;
import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.textfield.TextInputEditText;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    //Declare all views including Firebase

    private EditText eRegEmail;
    private EditText eRegPassword;
    private Button btnRegister;
    private TextView tvLoginHere;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setStatusBarColor(ContextCompat.getColor(register.this, R.color.teal_700));  //set status bar color

        //intilise all view and get instance of Firebase
        radioGroup = findViewById(R.id.radioGroup);
        eRegEmail=findViewById(R.id.etRegEmail);
        eRegPassword=findViewById(R.id.etRegPass);
        tvLoginHere=findViewById(R.id.tvLoginHere);
        btnRegister=findViewById(R.id.btnRegister);
        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.loading).setVisibility(View.INVISIBLE);
        //Implement the onClickListerner for the register button
        btnRegister.setOnClickListener(view ->{
            createUser();                                                                               //call function on Register button click
        });

        //Implement the onClickListerner for the re
        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(register.this,login.class));
        });
    }

    /*Create user function*/
    private void createUser() {
        findViewById(R.id.loading).setVisibility(View.VISIBLE);//Showing the loadin button to show user that the system ins loading while they trying to create user in database
        //Get checked occupation, teacher or student to create account for
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        String occupation = radioButton.getText().toString();

        //Get email and password from user
        String email = eRegEmail.getText().toString().trim();
        String password = eRegPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            eRegEmail.setError("Email cannot be empty");
            eRegEmail.requestFocus();                                                               //If email is empty notify user and request input by requesting focus
        }else if(TextUtils.isEmpty(password)){
            eRegPassword.setError("Password cannot be empty");
            eRegPassword.requestFocus();                                                            //Do the same as above for password
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {       //If they not empty create user with email and passoword which is not stored on database
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(password,email);                                                                       //Instantiate a new user object

                        FirebaseDatabase.getInstance().getReference(occupation)
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())                                        //Add user to table in Firebase based on their occupation including their ID
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {                                 //Can be on Success Listener
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    if(occupation.equals("Teacher")){
                                        Toast.makeText(register.this,"Teacher's account registered successfully",Toast.LENGTH_SHORT).show();            //If user added successfully then make toast for Teacher or student
                                    }else{
                                        Toast.makeText(register.this,"Student's account registered successfully",Toast.LENGTH_SHORT).show();
                                    }

                                    startActivity(new Intent(register.this,login.class));                               //Move user to log in page
                                }else{
                                    Toast.makeText(register.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();        //If unsuccessful registering
                                }
                            }
                        });


                    }else{
                        Toast.makeText(register.this,"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();                //Could'nt create user

                    }
                }
            });
        }
    }
}