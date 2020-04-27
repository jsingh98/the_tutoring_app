package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class chat extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;


    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private EditText message;
    String search = "";
    Query query;
    String tutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mFirestoreList = findViewById(R.id.chat_list);
        message = findViewById(R.id.messageBox);

        // get the time
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());


        // shared preferences stuff
        SharedPreferences sh = getSharedPreferences("MySharedPref", 0);
        final String s1 = sh.getString("tutorEmail", "");
        final String s2 = sh.getString("studentEmail", "");
//
       Toast.makeText(chat.this, "Student is: " + s2 + " and the Tutor is:" + s1, Toast.LENGTH_SHORT).show();

        //query = firebaseFirestore.collection("Messages");
        query = firebaseFirestore.collection("Messages").whereEqualTo("studentEmail", s2).whereEqualTo("tutorEmail",s1).orderBy("date");

        FirestoreRecyclerOptions<MessageModel> options = new FirestoreRecyclerOptions.Builder<MessageModel>()
                .setQuery(query, MessageModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<MessageModel, MessageViewHolder>(options) {
            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_single, parent, false);
                return new MessageViewHolder(view);


            }

            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i, @NonNull MessageModel messageModel) {
                messageViewHolder.list_from.setText(messageModel.getFrom());
                messageViewHolder.list_message.setText(messageModel.getMessage());
                messageViewHolder.list_date.setText(messageModel.getDate());

            }



        };
//
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    public void send(View view) {

        SharedPreferences sh = getSharedPreferences("MySharedPref", 0);
        final String s1 = sh.getString("tutorEmail", "");
        final String s2 = sh.getString("studentEmail", "");
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        String s = message.getText().toString();

        //Toast.makeText(chat.this, s + " " + currentDateTimeString, Toast.LENGTH_SHORT).show();


        // following TVAC tutorial
        Map<String,String> userMap = new HashMap<>();

        userMap.put("tutorEmail", s1);
        userMap.put("studentEmail", s2);
        userMap.put("date", currentDateTimeString);
        userMap.put("message", s);
        userMap.put("from", mAuth.getCurrentUser().getEmail());



        firebaseFirestore.collection("Messages").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent i = new Intent(getApplicationContext(), chat.class);
                startActivity(i);
                // Toast.makeText(studentRegistration.this, "Username added to Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(studentRegistration.this, "Username NOT ADDED", Toast.LENGTH_SHORT).show();

            }
        });



    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView list_from;
        public TextView list_message;
        public TextView list_date;


        private FirebaseAuth mAuth;
        FirebaseFirestore mFireStore;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

           list_from = itemView.findViewById(R.id.list_from);
           list_message = itemView.findViewById(R.id.list_message);
            list_date = itemView.findViewById(R.id.list_date);


            mAuth = FirebaseAuth.getInstance();
            mFireStore = FirebaseFirestore.getInstance();


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
