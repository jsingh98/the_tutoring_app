package com.example.thetutoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class studentDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
    }

    public void takePicture(View view) {

//        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("image/jpg");
//        final File photoFile = new File(getFilesDir(), "foo.jpg");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
//        startActivity(Intent.createChooser(shareIntent, "Share image using"));


    }
}
