package com.example.thetutoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

public class find_tutor extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private  FirestoreRecyclerAdapter adapter;
    private EditText subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor);

        subject = findViewById(R.id.editText10);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.tutorList);

        // find all the subject tutored


        // This is the recycler view stuff below
        // query
        Query query = firebaseFirestore.collection("SyzygyTutors");

        // Recycler options

        FirestoreRecyclerOptions<TutorModel> options = new FirestoreRecyclerOptions.Builder<TutorModel>()
                .setQuery(query, TutorModel.class)
                .build();

        adapter= new FirestoreRecyclerAdapter<TutorModel, TutorViewHolder>(options) {
            @NonNull
            @Override
            public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent,false);
                return  new TutorViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull TutorViewHolder tutorViewHolder, int i, @NonNull TutorModel tutorModel) {
                tutorViewHolder.list_first.setText(tutorModel.getFirst());
                tutorViewHolder.list_last.setText(tutorModel.getLast());
            }
        };

            mFirestoreList.setHasFixedSize(true);
            mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
            mFirestoreList.setAdapter(adapter);
        // view holder
    }

    private class TutorViewHolder extends RecyclerView.ViewHolder {

       private TextView list_first;
       private TextView list_last;


        public TutorViewHolder(@NonNull View itemView) {
            super(itemView);

            list_first = itemView.findViewById(R.id.list_first);
            list_last = itemView.findViewById(R.id.list_last);

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
