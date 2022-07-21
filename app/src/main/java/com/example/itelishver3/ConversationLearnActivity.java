package com.example.itelishver3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itelishver3.mapclass.ConversationList;
import com.example.itelishver3.mapclass.QuestionList;
import com.example.itelishver3.mapclass.VocabularyList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversationLearnActivity extends AppCompatActivity {

    private TextView tvTitle, tvEmployer, tvInterns;
    private Button btnPrev, btnNext;
    private ImageView imvBack;
    private DatabaseReference reference;
    private ArrayList<ConversationList> conversationListsBeginner = new ArrayList<>();
    private int index = 0;
    private int countDouble = 0;
    private String question, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_learn);

        Mapping();
        GetIntentConversation();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConversationLearnActivity.this, ConversationActivity.class));
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NextConversation();
                NextConversationFIX();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrevConversation();
            }
        });
    }

    private void Mapping(){
        tvTitle = (TextView) findViewById(R.id.tv_title_conversation_learn_activity);
        tvInterns = (TextView) findViewById(R.id.tv_interns_conversation_learn_activity);
        tvEmployer = (TextView) findViewById(R.id.tv_employer_conversation_learn_activity);

        imvBack = (ImageView) findViewById(R.id.imv_back_conversation_learn_activity);
        btnNext = (Button) findViewById(R.id.btn_next_conversation_learn_activity);
        btnPrev = (Button) findViewById(R.id.btn_prev_conversation_learn_activity);

    }

    private void GetIntentConversation(){
        Intent intent = getIntent();
        if(intent.getStringExtra("K1_HT_Activity").equals("Hội thoại chuyên ngành")){
            tvTitle.setText("Hội thoại chuyên ngành");
            updateHTCNBeginner();
        }else{
            tvTitle.setText(intent.getStringExtra("K1_HT_Activity"));
            updateHTPVBeginner();
        }

    }

    private void updateHTPVBeginner(){
        reference = FirebaseDatabase.getInstance().getReference().child("ConversationList").child("HTPV").child("CNPM");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    conversationListsBeginner.add(snapshot.getValue(ConversationList.class));
                }
                //setAllData();
                //tvEmployer.setText(conversationListsBeginner.get(index).getQuestion());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateHTCNBeginner(){
        reference = FirebaseDatabase.getInstance().getReference().child("ConversationList").child("HTCN").child("CNPM");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    conversationListsBeginner.add(snapshot.getValue(ConversationList.class));
                }
                //setAllData();
                //tvEmployer.setText(conversationListsBeginner.get(index).getQuestion());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAllData(){
        tvEmployer.setText(conversationListsBeginner.get(index).getQuestion());
        tvInterns.setText(conversationListsBeginner.get(index).getAnswer());
    }

    private void SetData(){
        question = conversationListsBeginner.get(index).getQuestion();
        answer = conversationListsBeginner.get(index).getAnswer();
    }

    private void NextConversation(){
        if(index < conversationListsBeginner.size()-1){
            index++;
            setAllData();
        }else {
            LearnSuccessForm();
        }
    }

    private void NextConversationFIX(){
        if(index <= conversationListsBeginner.size()-1 && countDouble==0){
            tvEmployer.setText(conversationListsBeginner.get(index).getQuestion());
            tvInterns.setText("");
            countDouble++;
        }else if(index <= conversationListsBeginner.size()-1 && countDouble==1) {
            tvInterns.setText(conversationListsBeginner.get(index).getAnswer());
            index++;
            //setAllData();
            countDouble = 0;
        }else if(index <= conversationListsBeginner.size()-1 && countDouble==2){
            index++;
            tvEmployer.setText(conversationListsBeginner.get(index).getQuestion());
            tvInterns.setText("");
            countDouble = 1;
        }
        else if(index > conversationListsBeginner.size()-1){
            LearnSuccessForm();
        }
    }

    private void PrevConversation(){
        if(index <= 0){
           Toast.makeText(ConversationLearnActivity.this, "Cannot Prev",Toast.LENGTH_SHORT).show();
        }else if(index <= conversationListsBeginner.size()-1){
            index--;
            tvInterns.setText("");
            countDouble = 2;
            setAllData();
        }else if(countDouble==0){
            index--;
            tvInterns.setText("");
            countDouble = 2;
            setAllData();
        }
    }

    private void LearnSuccessForm(){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_success_coversation_learn);
        Intent intent = new Intent(ConversationLearnActivity.this, ConversationActivity.class);
        dialog.findViewById(R.id.btn_go_back_diaglog_conversation_success_learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("K1_Vocab_LT","Lý thuyết");
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.findViewById(R.id.btn_go_practice_diaglog_conversation_success_learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("K1_VocabLevel","Beginner");
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

}