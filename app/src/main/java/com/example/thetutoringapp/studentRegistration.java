package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class studentRegistration extends AppCompatActivity {



    private static final String KEY_FIRST = "first";
    private static final String KEY_LAST = "last";
    private static final String KEY_EDUCTATION = "education";
    private static final String KEY_PASS = "pass";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_PHONE = "phone";
    EditText first, last, education, pass, email, phone;
    FirebaseAuth mAuth;


    FirebaseFirestore mFireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        first = findViewById(R.id.editText2);
        last = findViewById(R.id.editText3);
        education = findViewById(R.id.editText6);
        pass = findViewById(R.id.editText7);
        email = findViewById(R.id.editText8);
        phone = findViewById(R.id.editText9);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

    }


    public void goToHome(View view) {

        String f, l, e, p, em, ph;
        f = first.getText().toString();
        l = last.getText().toString();
        e = education.getText().toString();
        p = pass.getText().toString();
        em = email.getText().toString();
        ph = phone.getText().toString();


        // following TVAC tutorial
        Map<String,String> userMap = new HashMap<>();

        userMap.put("first", f);
        userMap.put("last", l);
        userMap.put("education", e);
        userMap.put("password", p);
        userMap.put("email", em);
       userMap.put("phone", ph);
        userMap.put("role", "student");



        mFireStore.collection("SyzygyStudents").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
            Toast.makeText(studentRegistration.this, "Fields are empty", Toast.LENGTH_SHORT).show();

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
                                        && phone.length() >= 3 && phone.length() < 30
                                        && education.length() >= 3 && education.length() < 30) {

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                                } else {

                                    Toast.makeText(studentRegistration.this, "Fields are invalid",
                                            Toast.LENGTH_SHORT).show();

                                }


                            }

                            else {
                                Toast.makeText(studentRegistration.this, "User already exists",
                                        Toast.LENGTH_SHORT).show();
                            }


                        }
                    });




        }
    }
}
