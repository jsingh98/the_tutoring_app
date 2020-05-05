package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class reportTutor extends AppCompatActivity {

    EditText tutorEmail;
    EditText score;
    Button button;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_tutor);
        tutorEmail = findViewById(R.id.rateTutorBox);
        score = findViewById(R.id.scoreBox);
        button = findViewById(R.id.rateTutorButton);
        db = FirebaseFirestore.getInstance();


    }


    public void rateTutor(View view) {

        // when they hit rate a tutor button
//        Toast.makeText(this, "Tutor email is: " + tutorEmail.getText().toString()
//                        + " and the score is: " + score.getText().toString()
//                , Toast.LENGTH_SHORT).show();

        if (  !(score.getText().toString().equals("1"))
                && !(score.getText().toString().equals("2"))
                && !(score.getText().toString().equals("3"))
                && !(score.getText().toString().equals("4"))
                && !(score.getText().toString().equals("5"))
        ) {
            Toast.makeText(this, "Enter a valid number 1-5", Toast.LENGTH_SHORT).show();
        } else {

            db.collection("SyzygyTutors").whereEqualTo("email", tutorEmail.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //Log.d("MYDEBUG", document.getId() + " => " + document.getData());

                            int total = Integer.parseInt(document.getString("total")) + Integer.parseInt(score.getText().toString());

                            int num = Integer.parseInt(document.getString("num")) + 1;

                            String t = Integer.toString(total);
                            String n = Integer.toString(num);

                            Toast.makeText(getApplicationContext(), "The total score is " + total
                                            + " and the number of reviews is: " + num
                                    , Toast.LENGTH_SHORT).show();

                            db.collection("SyzygyTutors")
                                    .document(document.getId())
                                    .update("num", n);

                            db.collection("SyzygyTutors")
                                    .document(document.getId())
                                    .update("total", t);

                            Intent i = new Intent(getApplicationContext(), studentDashboard.class);
                            startActivity(i);


                        }

                    } else {
                        // Log.w("MYDEBUG", "Error getting documents.", task.getException());
                    }
                }
            });


        }

    }




}
