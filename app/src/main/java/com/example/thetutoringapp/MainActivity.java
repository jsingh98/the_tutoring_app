package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email, pass;
    private Button login, register;
    Dialog d;

    private FirebaseAuth fuego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fuego = FirebaseAuth.getInstance();

        email = findViewById(R.id.editText4);
        pass = findViewById(R.id.editText5);
        login = findViewById(R.id.button);
        register = findViewById(R.id.button2);



    }

    public void log(View view) {
        String log, p;

        log = email.getText().toString();

        p = pass.getText().toString();


    if(log.isEmpty() || p.isEmpty() ){
        Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();

    }
else {
        fuego.signInWithEmailAndPassword(log, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // make intent to go to tutor or student page

                    Toast.makeText(MainActivity.this, "Login with firebase successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login with firebase failed", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    }

    public void register(View view) {

        d = new Dialog(this);
        // grade1 = (EditText) findViewById()
        d.setContentView(R.layout.student_or_tutor_dialog);
       // data = d.findViewById(R.id.textView2);
        // showing the dialog

        d.show();
    }

    public void goToStudentRegistration(View view) {
        Intent i= new Intent(this,studentRegistration.class);
        startActivity(i);

    }

    public void goToTutorRegistration(View view) {
        Intent i= new Intent(this,tutorRegistration.class);
        startActivity(i);
    }
}
