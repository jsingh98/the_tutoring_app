package com.example.thetutoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class studentDashboard extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        mAuth = FirebaseAuth.getInstance();

//        Toast.makeText(studentDashboard.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();


    }

    public void takePicture(View view) {


        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "You have been signed out", Toast.LENGTH_SHORT).show();


    }

    public void displayList(View view) {
        Intent i = new Intent(this, find_tutor.class);
        startActivity(i);
    }

    public void goToSettings(View view) {
        Intent i = new Intent(this, s_settings.class);
        startActivity(i);
    }

    public void messages(View view) {
        Intent i = new Intent(this, student_messaging_list.class);
        startActivity(i);
    }
}
