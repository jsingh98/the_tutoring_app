package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class find_tutor extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private  FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    private EditText subject;
    String search = "";
    Query query;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor);

        subject = findViewById(R.id.editText10);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.tutorList);

        // this is used to find the current email
        mAuth = FirebaseAuth.getInstance();

        // find button
        button = findViewById(R.id.list_accept);

        // find all the subject tutored

        query = firebaseFirestore.collection("SyzygyTutors");

        FirestoreRecyclerOptions<TutorModel> options = new FirestoreRecyclerOptions.Builder<TutorModel>()
                .setQuery(query, TutorModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<TutorModel, TutorViewHolder>(options) {
            @NonNull
            @Override
            public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new TutorViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull TutorViewHolder tutorViewHolder, int i, @NonNull TutorModel tutorModel) {
                tutorViewHolder.list_first.setText(tutorModel.getFirst());
                tutorViewHolder.list_last.setText(tutorModel.getEmail());
                tutorViewHolder.list_subject.setText(tutorModel.getSubject());
            }



        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);


        // This is the recycler view stuff below
        // query


        // view holder
    }


    public void find(View view) {
       //query =  firebaseFirestore.collection("SyzygyTutors");

        if(subject.getText().toString().equals("math") || subject.getText().toString().equals("Math") || subject.getText().toString().equals("MTH") || subject.getText().toString().equals("mth")) {
            Intent i = new Intent(this, find_math_tutors.class);
            startActivity(i);
        }

        else if(subject.getText().toString().equals("english") || subject.getText().toString().equals("English") || subject.getText().toString().equals("EGL") || subject.getText().toString().equals("egl")) {
            Intent i = new Intent(this, find_english_tutors.class);
            startActivity(i);
        }
        else if(subject.getText().toString().equals("social studies") || subject.getText().toString().equals("Social Studies") || subject.getText().toString().equals("HIS") || subject.getText().toString().equals("his")
                || subject.getText().toString().equals("ss") || subject.getText().toString().equals("SS") || subject.getText().toString().equals("socialstudies") || subject.getText().toString().equals("social")
                || subject.getText().toString().equals("history") || subject.getText().toString().equals("History") ) {
            Intent i = new Intent(this, find_social_studies_tutors.class);
            startActivity(i);
        }
        else if(subject.getText().toString().equals("Science") || subject.getText().toString().equals("science") || subject.getText().toString().equals("BCS") || subject.getText().toString().equals("bcs")) {
            Intent i = new Intent(this, find_science_tutors.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(this, find_tutor.class);
            startActivity(i);
        }




    }

        public static class TutorViewHolder extends RecyclerView.ViewHolder {

            public TextView list_first;
            public TextView list_last;
            public TextView list_subject;
            public Button list_button;
            private FirebaseAuth mAuth;
            FirebaseFirestore mFireStore;
            boolean sentRequest = false;

            public TutorViewHolder(@NonNull View itemView) {
                super(itemView);

                list_first = itemView.findViewById(R.id.list_first);
                list_last = itemView.findViewById(R.id.list_last);
                list_subject = itemView.findViewById(R.id.list_subject);
                list_button = itemView.findViewById(R.id.list_accept);
                mAuth = FirebaseAuth.getInstance();
                mFireStore = FirebaseFirestore.getInstance();

                // what happens if you click on ADD TUTOR button
                list_button.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {

                        int position = getAdapterPosition();

                        Map<String,String> userMap = new HashMap<>();



                        userMap.put("status", "pending");
                        userMap.put("studentEmail",mAuth.getCurrentUser().getEmail());
                        userMap.put("tutorEmail",list_last.getText().toString());
                       // Toast.makeText(TutorViewHolder.super.itemView.getContext(), mAuth.getCurrentUser().getEmail() + " sent a request to: " + list_last.getText().toString(), Toast.LENGTH_SHORT ).show();

                        if(sentRequest == false) {
                            mFireStore.collection("Requests").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(TutorViewHolder.super.itemView.getContext(), mAuth.getCurrentUser().getEmail() + " sent a request to: " + list_last.getText().toString(), Toast.LENGTH_SHORT).show();
                                    sentRequest = true;

                                } // end of success
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                  //  Toast.makeText(TutorViewHolder.super.itemView.getContext(), "Cannot send request", Toast.LENGTH_SHORT).show();

                                }
                            }); // end of on failur
                        } // end of send request == false
                    } // end of on click
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
