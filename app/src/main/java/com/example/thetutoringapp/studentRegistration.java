package com.example.thetutoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class studentRegistration extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        mAuth = FirebaseAuth.getInstance();
    }


    public void goToHome(View view) {







        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
