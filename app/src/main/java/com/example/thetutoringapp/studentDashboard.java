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


//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        String shareBody = "Send a picture of your problem";
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
            Intent i = new Intent(this, picture.class);
            startActivity(i);

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
        Intent i = new Intent(this, student_messages.class);
        startActivity(i);
    }
}
