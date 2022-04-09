package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnLogOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.teal_700));


        btnLogOut= findViewById(R.id.btnLogout);
        mAuth= FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this,login.class));
        });
    }
    @Override    

    //I added this comment
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(MainActivity.this,login.class));
        }
    }
}