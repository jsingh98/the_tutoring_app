package com.example.thetutoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class s_settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_settings);
    }

    public void report(View view) {
        Intent i = new Intent(this, reportTutor.class);
        startActivity(i);
    }

    public void changeName(View view) {
        Intent i = new Intent(this, changeName.class);
        startActivity(i);
    }

    public void deleteAccount(View view) {
        Intent i = new Intent(this, deleteStudent.class);
        startActivity(i);
    }

    public void chooseColor(View view) {
        Intent i = new Intent(this, changeColor.class);
        startActivity(i);
    }
}
