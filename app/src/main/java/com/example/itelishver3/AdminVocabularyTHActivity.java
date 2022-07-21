package com.example.itelishver3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.itelishver3.mapclass.QuestionList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminVocabularyTHActivity extends AppCompatActivity {

    private RecyclerView rcvVocabularyTH;
    private Button btnAdd, btnBack;
    private ArrayList<QuestionList> questionLists = new ArrayList<>();
    private DatabaseReference databaseReference;
    private MyAdapterTH myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vocabulary_thactivity);

        Mapping();
        UploadRecyclerView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminVocabularyTHActivity.this, AdminActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void Mapping(){
        rcvVocabularyTH = (RecyclerView) findViewById(R.id.rcv_vocabulary_th_admin_th_activity);
        btnAdd = (Button) findViewById(R.id.btn_add_vocabulary_th_admin);
        btnBack = (Button) findViewById(R.id.btn_back_vocabulary_th_admin);
    }

    private void UploadRecyclerView(){
        databaseReference = FirebaseDatabase.getInstance().getReference("QuestionList").child("TVCN").child("Beginner");
        rcvVocabularyTH.setHasFixedSize(true);
        rcvVocabularyTH.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapterTH(this, questionLists);
        rcvVocabularyTH.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    QuestionList questionList = dataSnapshot.getValue(QuestionList.class);
                    questionLists.add(questionList);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DialogFixVocabulary(){
        Dialog dialog = new Dialog(AdminVocabularyTHActivity.this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_upload_vocabulary_admin);
        dialog.show();
        dialog.findViewById(R.id.btn_update_dialog_edit_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }

    private void DialogAddConversation(){
        Dialog dialog = new Dialog(AdminVocabularyTHActivity.this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_upload_vocabulary_admin);
        dialog.show();
        dialog.findViewById(R.id.btn_update_dialog_edit_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }
}