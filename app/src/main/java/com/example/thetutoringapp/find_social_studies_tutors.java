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

public class find_social_studies_tutors extends AppCompatActivity {
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
        setContentView(R.layout.activity_find_social_studies_tutors);

        // start here
        subject = findViewById(R.id.editText10);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.tutorList);

        // find button
        button = findViewById(R.id.button16);

        // find all the subject tutored

        query = firebaseFirestore.collection("SocialStudiesTutors");

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
                tutorViewHolder.list_price.setText("Price: $" + (tutorModel.getPrice()) + " per hour");
                if( Integer.parseInt(tutorModel.getTotal()) == 0){
                    //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                    tutorViewHolder.list_rating.setText("Rating: " + tutorModel.getTotal());

                    // Toast.makeText(TutorViewHolder.super.itemView.getContext(), mAuth.getCurrentUser().getEmail() + " sent a request to: " + list_last.getText().toString(), Toast.LENGTH_SHORT).show();

                }
                else {
                    int average = Integer.parseInt(tutorModel.getTotal()) / Integer.parseInt(tutorModel.getNum());
                    tutorViewHolder.list_rating.setText("Rating:" + average);
                }
                tutorViewHolder.list_num.setText("Number of Ratings: " + (tutorModel.getNum()));

            }


        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);




    }


    public void findSocialStudiesTutors(View view) {
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
        public TextView list_price;

        public TextView list_num;
        public TextView list_rating;

        private FirebaseAuth mAuth;
        FirebaseFirestore mFireStore;
        boolean sentRequest = false;
        public Button list_button;

        public TutorViewHolder(@NonNull View itemView) {
            super(itemView);

            list_first = itemView.findViewById(R.id.list_first);
            list_last = itemView.findViewById(R.id.list_last);
            list_subject = itemView.findViewById(R.id.list_subject);
            list_price = itemView.findViewById(R.id.list_pay);

            list_rating = itemView.findViewById(R.id.list_rating);
            list_num = itemView.findViewById(R.id.list_num);

            list_button = itemView.findViewById(R.id.list_accept);
            mAuth = FirebaseAuth.getInstance();
            mFireStore = FirebaseFirestore.getInstance();
            list_button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    Map<String,String> userMap = new HashMap<>();



                    userMap.put("status", "pending");
                    userMap.put("studentEmail",mAuth.getCurrentUser().getEmail());
                    userMap.put("tutorEmail",list_last.getText().toString());
                    userMap.put("subject", list_subject.getText().toString());
                    // Toast.makeText(TutorViewHolder.super.itemView.getContext(), mAuth.getCurrentUser().getEmail() + " sent a request to: " + list_last.getText().toString(), Toast.LENGTH_SHORT ).show();

                    if(sentRequest == false) {
                        mFireStore.collection("Requests").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(find_social_studies_tutors.TutorViewHolder.super.itemView.getContext(), mAuth.getCurrentUser().getEmail() + " sent a request to: " + list_last.getText().toString(), Toast.LENGTH_SHORT).show();
                                sentRequest = true;

                            } // end of success
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(find_social_studies_tutors.TutorViewHolder.super.itemView.getContext(), "Cannot send request", Toast.LENGTH_SHORT).show();

                            }
                        }); // end of on failur
                    } // end of send request == false
                } // end of on click
            });



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
