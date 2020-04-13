package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        userMap.put("phone", ph);
        userMap.put("subject", sub);
        userMap.put("role", "tutor");

        mFireStore.collection("app").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(tutorRegistration.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //  updateUI(null);
                            }

                            // ...
                        }
                    });


            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

}
