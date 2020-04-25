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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class find_science_tutors extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private EditText subject;
    String search = "";
    Query query;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_science_tutors);


        // start here
        subject = findViewById(R.id.editText10);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.tutorList);

        // find button
        button = findViewById(R.id.button16);

        // find all the subject tutored

        query = firebaseFirestore.collection("ScienceTutors");

        FirestoreRecyclerOptions<TutorModel> options = new FirestoreRecyclerOptions.Builder<TutorModel>()
                .setQuery(query, TutorModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<TutorModel, find_tutor.TutorViewHolder>(options) {
            @NonNull
            @Override
            public find_tutor.TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new find_tutor.TutorViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull find_tutor.TutorViewHolder tutorViewHolder, int i, @NonNull TutorModel tutorModel) {
                tutorViewHolder.list_first.setText(tutorModel.getFirst());
                tutorViewHolder.list_last.setText(tutorModel.getEmail());
                tutorViewHolder.list_subject.setText(tutorModel.getSubject());
            }


        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);



    }

    public void findScienceTutors(View view) {
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


    private class TutorViewHolder extends RecyclerView.ViewHolder {

        private TextView list_first;
        private TextView list_last;
        public TextView list_subject;

        public TutorViewHolder(@NonNull View itemView) {
            super(itemView);

            list_first = itemView.findViewById(R.id.list_first);
            list_last = itemView.findViewById(R.id.list_last);
            list_subject = itemView.findViewById(R.id.list_subject);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }



}
