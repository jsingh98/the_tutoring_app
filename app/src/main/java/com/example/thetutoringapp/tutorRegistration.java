package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class tutorRegistration extends AppCompatActivity {

    EditText first;
    EditText last;
    EditText education;
    EditText pass;
    EditText email;
    EditText phone;
    EditText subject;

    FirebaseFirestore mFireStore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_registration);

        first = findViewById(R.id.editText2);
        last = findViewById(R.id.editText3);
        education = findViewById(R.id.editText6);
        pass = findViewById(R.id.editText7);
        email = findViewById(R.id.editText8);
        phone = findViewById(R.id.editText9);
        subject = findViewById(R.id.editText);

        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


    }


    public void goToHome(View view) {

        String f, l, e, p, em, ph, sub;
        f = first.getText().toString();
        l = last.getText().toString();
        e = education.getText().toString();
        ph = phone.getText().toString();
        em = email.getText().toString();
        p = pass.getText().toString();
        sub = subject.getText().toString();


        // following TVAC tutorial
        Map<String,String> userMap = new HashMap<>();

        userMap.put("first", f);
        userMap.put("last", l);
        userMap.put("education", e);
        userMap.put("password", p);
        userMap.put("email", em);
        userMap.put("price", ph);
        userMap.put("subject", sub);
        userMap.put("role", "tutor");
        userMap.put("num", "0");
        userMap.put("total", "0");

        if(sub.equals("Math") || sub.equals("math")){
            mFireStore.collection("MathTutors").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // Toast.makeText(studentRegistration.this, "Username added to Firestore", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(studentRegistration.this, "Username NOT ADDED", Toast.LENGTH_SHORT).show();

                }
            });
        } // end of if statement for Math tutors
        if(sub.equals("English") || sub.equals("english")){
            mFireStore.collection("EnglishTutors").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // Toast.makeText(studentRegistration.this, "Username added to Firestore", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(studentRegistration.this, "Username NOT ADDED", Toast.LENGTH_SHORT).show();

                }
            });
        }
        if(sub.equals("Science") || sub.equals("science")){
            mFireStore.collection("ScienceTutors").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // Toast.makeText(studentRegistration.this, "Username added to Firestore", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(studentRegistration.this, "Username NOT ADDED", Toast.LENGTH_SHORT).show();

                }
            });
        }
        if(sub.equals("Social Studies") || sub.equals("social studies")){
            mFireStore.collection("SocialStudiesTutors").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // Toast.makeText(studentRegistration.this, "Username added to Firestore", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(studentRegistration.this, "Username NOT ADDED", Toast.LENGTH_SHORT).show();

                }
            });
        }

    // if the person doesn't fall under a specific subject

            mFireStore.collection("SyzygyTutors").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // Toast.makeText(studentRegistration.this, "Username added to Firestore", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Toast.makeText(studentRegistration.this, "Username NOT ADDED", Toast.LENGTH_SHORT).show();

                }
            });




        if (em.isEmpty() || p.isEmpty()) {
            Toast.makeText(tutorRegistration.this, "Fields are empty", Toast.LENGTH_SHORT).show();

        } else {
            mAuth.createUserWithEmailAndPassword(em, p)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (first.length() >= 2 && first.length() < 30 && last.length() >= 2 && last.length() < 30
                                        && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()
                                        && pass.length() >= 3 && pass.length() < 30
                                        && education.length() >= 3 && education.length() < 30
                                        && subject.length() >= 3 && subject.length() < 30) {

                                    Intent i = new Intent(tutorRegistration.this, MainActivity.class);
                                    startActivity(i);

                                } else {

                                    Toast.makeText(tutorRegistration.this, "Fields are invalid",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }

                            else {
                                Toast.makeText(tutorRegistration.this, "User already exists",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



        }
    }

}
