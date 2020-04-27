package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class view_requests extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    private EditText subject;
    String search = "";
    Query query;
    String tutor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

       // subject = findViewById(R.id.editText10);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.studentList);

        // this is used to find the current email
        mAuth = FirebaseAuth.getInstance();

        tutor = mAuth.getCurrentUser().getEmail();
        //Toast.makeText(this, tutor, Toast.LENGTH_SHORT).show();


        // make query , then on complete, make the "query" variable




        // find all the requests for a specific tutor
        query = firebaseFirestore.collection("Requests").whereEqualTo("tutorEmail",mAuth.getCurrentUser().getEmail()).whereEqualTo("status","pending");

        FirestoreRecyclerOptions<StudentModel> options = new FirestoreRecyclerOptions.Builder<StudentModel>()
                .setQuery(query, StudentModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<StudentModel, view_requests.StudentViewHolder>(options) {
            @NonNull
            @Override
            public view_requests.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_student_item_single, parent, false);
                return new view_requests.StudentViewHolder(view);


            }

            @Override
            protected void onBindViewHolder(@NonNull view_requests.StudentViewHolder studentViewHolder, int i, @NonNull StudentModel studentModel) {
                studentViewHolder.list_first.setText(studentModel.getStudentEmail());

            }



        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);



    }




    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView list_first;
        public Button list_button;
        public Button list_decline;

        private FirebaseAuth mAuth;
        FirebaseFirestore mFireStore;
        boolean sentRequest = false;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            list_first = itemView.findViewById(R.id.list_first);
            list_button = itemView.findViewById(R.id.list_accept);
            list_decline = itemView.findViewById(R.id.list_decline);

            mAuth = FirebaseAuth.getInstance();
            mFireStore = FirebaseFirestore.getInstance();

            // what happens if you click on ACCEPT STUDENTS button
            list_button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    mFireStore.collection("Requests").whereEqualTo("tutorEmail",mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String role = document.getString("status");

                            // might need to use equals to function
                            if(role.equals("pending") || role.equals("declined")){
                                Toast.makeText(view_requests.StudentViewHolder.super.itemView.getContext(), "The student is :" + list_first.getText(), Toast.LENGTH_SHORT).show();
                                mFireStore.collection("Requests")
                                        .document(document.getId())
                                        .update("status", "accepted");
                            }
                        }
                    } else {
                        // Log.w("MYDEBUG", "Error getting documents.", task.getException());
                    }
                }
            });
        } // end of on click, dont put any code past this point
    });



            list_decline.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    mFireStore.collection("Requests").whereEqualTo("tutorEmail",mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String role = document.getString("status");

                                    // might need to use equals to function
                                    if(role.equals("pending") || role.equals("accepted")){
                                        Toast.makeText(view_requests.StudentViewHolder.super.itemView.getContext(), "The student is :" + list_first.getText(), Toast.LENGTH_SHORT).show();
                                        mFireStore.collection( "Requests")
                                                .document(document.getId())
                                                .update("status", "declined");
                                    }
                                }
                            } else {
                                // Log.w("MYDEBUG", "Error getting documents.", task.getException());
                            }
                        }
                    });
                } // end of on click, dont put any code past this point
            });




        }


    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
