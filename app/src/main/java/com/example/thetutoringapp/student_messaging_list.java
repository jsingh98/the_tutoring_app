package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class student_messaging_list extends AppCompatActivity {

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
        setContentView(R.layout.activity_student_messaging_list);

        // subject = findViewById(R.id.editText10);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.message_list);

        // this is used to find the current email
        mAuth = FirebaseAuth.getInstance();




        // make query , then on complete, make the "query" variable

        query = firebaseFirestore.collection("Requests").whereEqualTo("studentEmail",mAuth.getCurrentUser().getEmail()).whereEqualTo("status","accepted");

        FirestoreRecyclerOptions<TutorMessageModel> options = new FirestoreRecyclerOptions.Builder<TutorMessageModel>()
                .setQuery(query, TutorMessageModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<TutorMessageModel, student_messaging_list.TutorViewHolders>(options) {
            @NonNull
            @Override
            public student_messaging_list.TutorViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message_tutor_item_single, parent, false);
                return new student_messaging_list.TutorViewHolders(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull student_messaging_list.TutorViewHolders tutorViewHolders, int i, @NonNull TutorMessageModel tutorMessageModel) {
                tutorViewHolders.list_first.setText(tutorMessageModel.getTutorEmail());

            }



        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);


    }



    public class TutorViewHolders extends RecyclerView.ViewHolder {

        public TextView list_first;
        public TextView list_last;
        public TextView list_subject;
       public Button list_button;
        private FirebaseAuth mAuth;
        FirebaseFirestore mFireStore;
        boolean sentRequest = false;

        public TutorViewHolders(@NonNull View itemView) {
            super(itemView);

            list_first = itemView.findViewById(R.id.list_tutor);
            list_button = itemView.findViewById(R.id.list_send_tutor_message);

            mAuth = FirebaseAuth.getInstance();
            mFireStore = FirebaseFirestore.getInstance();

            list_button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    // this is the students messaging, so make the TUTOR info go to the next screen
                   // Toast.makeText(student_messaging_list.TutorViewHolders.super.itemView.getContext(), "The tutor is :" + list_first.getText(), Toast.LENGTH_SHORT).show();

                     // Storing data into SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("tutorEmail", list_first.getText().toString());
                    myEdit.putString("studentEmail", mAuth.getCurrentUser().getEmail());
                    myEdit.commit();


                    Intent i = new Intent(student_messaging_list.TutorViewHolders.super.itemView.getContext(), chat.class);
                    student_messaging_list.TutorViewHolders.super.itemView.getContext().startActivity(i);
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
