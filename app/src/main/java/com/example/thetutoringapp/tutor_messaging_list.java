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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class tutor_messaging_list extends AppCompatActivity {
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

        query = firebaseFirestore.collection("Requests").whereEqualTo("tutorEmail",mAuth.getCurrentUser().getEmail()).whereEqualTo("status","accepted");

        FirestoreRecyclerOptions<StudentModel> options = new FirestoreRecyclerOptions.Builder<StudentModel>()
                .setQuery(query, StudentModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<StudentModel, tutor_messaging_list.TutorViewHolders>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TutorViewHolders tutorViewHolders, int i, @NonNull StudentModel studentModel) {
                tutorViewHolders.list_first.setText(studentModel.getStudentEmail());

            }

            @NonNull
            @Override
            public tutor_messaging_list.TutorViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message_tutor_item_single, parent, false);
                return new tutor_messaging_list.TutorViewHolders(view);

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

                        // this is a tutor, so shared preference the god damn student info so you can enter a chat with them
                    //Toast.makeText(tutor_messaging_list.TutorViewHolders.super.itemView.getContext(), "The student is :" + list_first.getText(), Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("studentEmail", list_first.getText().toString());
                    myEdit.putString("tutorEmail", mAuth.getCurrentUser().getEmail());
                    myEdit.commit();



                    Intent i = new Intent(tutor_messaging_list.TutorViewHolders.super.itemView.getContext(), chat.class);
                        tutor_messaging_list.TutorViewHolders.super.itemView.getContext().startActivity(i);
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
