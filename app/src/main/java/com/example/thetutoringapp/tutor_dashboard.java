package com.example.thetutoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class tutor_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_dashboard);
    }

    public void goToSettings(View view) {
        Intent i = new Intent(this, t_settings.class);
        startActivity(i);
    }

    public void messages(View view) {
       Intent i = new Intent(this, tutor_messaging_list.class);
        startActivity(i);
    }

    public void viewRequests(View view) {
        Intent i = new Intent(this, view_requests.class);
        startActivity(i);
    }
}
