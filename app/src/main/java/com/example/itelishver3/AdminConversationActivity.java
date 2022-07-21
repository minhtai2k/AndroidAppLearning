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
import android.widget.EditText;
import android.widget.Toast;

import com.example.itelishver3.mapclass.ConversationList;
import com.example.itelishver3.mapclass.VocabularyList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminConversationActivity extends AppCompatActivity {

    private RecyclerView rcvConversation;
    private Button btnAdd, btnBack;
    private ArrayList<ConversationList> conversationLists = new ArrayList<ConversationList>();
    private DatabaseReference databaseReference;
    private MyAdapterConversation myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_conversation);

        Mapping();
        UploadRecyclerViewHTPV();
        Intent intent = getIntent();
        if(intent.getStringExtra("SL_1").equals("HTCN_Beginner")){
            UploadRecyclerViewHTCN();
        }else {
            UploadRecyclerViewHTPV();
        }



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminConversationActivity.this, AdminActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddConversation();
            }
        });
    }

    private void Mapping(){
        rcvConversation = (RecyclerView) findViewById(R.id.rcv_conversation_admin_activity);
        btnAdd = (Button) findViewById(R.id.btn_add_conversation_admin);
        btnBack = (Button) findViewById(R.id.btn_back_conversation_admin);
    }

    private void UploadRecyclerViewHTPV(){
        databaseReference = FirebaseDatabase.getInstance().getReference("ConversationList").child("HTPV").child("CNPM");
        rcvConversation.setHasFixedSize(true);
        rcvConversation.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapterConversation(this, conversationLists, new MyAdapterConversation.ItemClickListener() {
            @Override
            public void onItemClick(ConversationList conversationList) {
                Dialog dialog = new Dialog(AdminConversationActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.dialog_upload_conversation_admin);

                EditText edtQuestion = dialog.findViewById(R.id.edt_question_upload_conversation_admin_dialog);
                edtQuestion.setText(conversationList.getQuestion());
                EditText edtQuestionVS = dialog.findViewById(R.id.edt_questionVS_upload_conversation_admin_dialog);
                edtQuestionVS.setText(conversationList.getQuestionVS());
                EditText edtAnswer = dialog.findViewById(R.id.edt_answer_upload_conversation_admin_dialog);
                edtAnswer.setText(conversationList.getAnswer());
                EditText edtAnswerVS = dialog.findViewById(R.id.edt_answerVS_upload_conversation_admin_dialog);
                edtAnswerVS.setText(conversationList.getAnswerVS());

                dialog.show();
                dialog.findViewById(R.id.btn_update_dialog_edit_conversation_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference(ConversationList.class.getSimpleName());

                        //Upload Firestore TVCN Beginner
                        DatabaseReference beginnerRef = databaseReference.child("HTPV").child("CNPM");
                        ConversationList conversationList = new ConversationList();

                        HashMap VocabUpdate = new HashMap();
                        VocabUpdate.put("answer", conversationList.getAnswer());
                        VocabUpdate.put("answerVS", conversationList.getAnswerVS());
                        VocabUpdate.put("question", conversationList.getQuestion());
                        VocabUpdate.put("questionVS", conversationList.getQuestionVS());

                        beginnerRef.child(conversationList.getId()).updateChildren(VocabUpdate).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Toast.makeText(AdminConversationActivity.this, "Upload success", Toast.LENGTH_SHORT).show();
                                myAdapter.notifyDataSetChanged();
                                AdminConversationActivity.this.recreate();
                            }
                        });
                    }
                });
                dialog.findViewById(R.id.btn_back_dialog_upload_conversation_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        rcvConversation.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ConversationList conversationList = dataSnapshot.getValue(ConversationList.class);
                    conversationLists.add(conversationList);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void DialogFixVocabulary(VocabularyList vocabularyList){
//        Dialog dialog = new Dialog(AdminConversationActivity.this);
//        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//        dialog.setContentView(R.layout.dialog_upload_vocabulary_admin);
//        EditText edtVocab = dialog.findViewById(R.id.edt_vocab_upload_vocabulary_admin_dialog);
//        edtVocab.setText(vocabularyList.getVocab());
//        EditText edtPronounce = dialog.findViewById(R.id.edt_pronounce_upload_vocabulary_admin_dialog);
//        edtPronounce.setText(vocabularyList.getPronounce());
//        EditText edtMean = dialog.findViewById(R.id.edt_mean_upload_vocabulary_admin_dialog);
//        edtMean.setText(vocabularyList.getExample());
//        dialog.show();
//        dialog.show();
//        dialog.findViewById(R.id.btn_update_dialog_edit_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.hide();
//            }
//        });
//    }

    private void DialogAddConversation(){
        Dialog dialog = new Dialog(AdminConversationActivity.this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_upload_conversation_admin);
        dialog.show();
        dialog.findViewById(R.id.btn_back_dialog_upload_conversation_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        dialog.findViewById(R.id.btn_update_dialog_edit_conversation_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void UploadRecyclerViewHTCN(){
        databaseReference = FirebaseDatabase.getInstance().getReference("ConversationList").child("HTCN").child("CNPM");
        rcvConversation.setHasFixedSize(true);
        rcvConversation.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapterConversation(this, conversationLists, new MyAdapterConversation.ItemClickListener() {
            @Override
            public void onItemClick(ConversationList conversationList) {

            }
        });
        rcvConversation.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ConversationList conversationList = dataSnapshot.getValue(ConversationList.class);
                    conversationLists.add(conversationList);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}