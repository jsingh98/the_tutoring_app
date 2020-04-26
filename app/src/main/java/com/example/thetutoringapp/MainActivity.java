package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    private EditText email, pass;
    private Button login, register;
    Dialog d;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance();

        db.collection("app").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("MYDEBUG",document.getId()+" => "+document.getData());
                    }
                } else {
                    Log.w("MYDEBUG","Error getting documents.",task.getException());
                }
            }
        });





        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText4);
        pass = findViewById(R.id.editText5);
        login = findViewById(R.id.button);
        register = findViewById(R.id.button2);




    }

    public void log(View view) {
        // variables for login, log is the email and p is the password
        final String log, p;
        log = email.getText().toString();
        p = pass.getText().toString();

        // if the email or the password are empty, display fields are empty
    if(log.isEmpty() || p.isEmpty() ){
        Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();

    }
else {
    // try signing in with firestore using the log and p variables
        mAuth.signInWithEmailAndPassword(log, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if the email and password exist in firebase, do the following
                if (task.isSuccessful()) {

                    // in order to get the email of the current user, use this
                    //mAuth.getCurrentUser().getEmail();

                   // Toast.makeText(MainActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();


                    // read in email, and search for role
                    // depending on role, bring to respective dashboard

                    db.collection("SyzygyStudents").whereEqualTo("email", log).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("MYDEBUG", document.getId() + " => " + document.getData());
                                    String role = document.getString("role");

                                    // might need to use equals to function
                                    if(role.equals("student")){
                                        Intent i = new Intent(getApplicationContext(), studentDashboard.class);
                                        startActivity(i);
                                    }
                                    // technically, don't need this check because it is checking the student collection, delete later after testing
                                    if(role.equals("tutor")){
                                        Intent i = new Intent(getApplicationContext(), tutor_dashboard.class);
                                        startActivity(i);
                                    }

                                }

                            } else {
                                Log.w("MYDEBUG", "Error getting documents.", task.getException());
                            }
                        }
                    });

                    db.collection("SyzygyTutors").whereEqualTo("email", log).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("MYDEBUG", document.getId() + " => " + document.getData());
                                    String role = document.getString("role");

                                    // might need to use equals to function
                                    if(role.equals("student")){
                                        Intent i = new Intent(getApplicationContext(), studentDashboard.class);
                                        startActivity(i);
                                    }

                                    if(role.equals("tutor")){
                                        Intent i = new Intent(getApplicationContext(), tutor_dashboard.class);
                                        startActivity(i);
                                    }

                                }

                            } else {
                                Log.w("MYDEBUG", "Error getting documents.", task.getException());
                            }
                        }
                    });


                    Toast.makeText(MainActivity.this, "Login with firebase successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login with firebase failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
        // use the next two lines to skip auth, for testing purposes only
//        Intent i = new Intent(getApplicationContext(), studentDashboard.class);
//        startActivity(i);
    }







    public void register(View view) {

        d = new Dialog(this);
        d.setContentView(R.layout.student_or_tutor_dialog);
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
