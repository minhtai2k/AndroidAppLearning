package com.example.itelishver3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itelishver3.mapclass.QuestionList;
import com.example.itelishver3.mapclass.VocabularyList;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class VocabularyLearnActivity extends AppCompatActivity {

    private ArrayList<VocabularyList> vocabularyListsBeginner = new ArrayList<VocabularyList>();
    private ArrayList<VocabularyList> vocabularyListsIntermediate = new ArrayList<VocabularyList>();
    private ArrayList<VocabularyList> vocabularyListsAdvanced = new ArrayList<VocabularyList>();

    private DatabaseReference reference;
    private ImageView imgBack;
    private TextView tvTitle, tvVocab, tvPronounce, tvExample;
    private Button btnPrev, btnNext;
    private ProgressBar prbCount;
    private String urlPath;
    private ImageView imvPlay;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_learn);

        Mapping();
        GetIntentTitle();
        updateTVBeginner();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyLearnActivity.this, VocabularyLevelActivity.class);
                intent.putExtra("K1_Vocab_LT","Lý thuyết");
                startActivity(intent);
                finish();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrevVocab();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextVocab();
            }
        });

        imvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(view);
            }
        });
    }

    private void Mapping(){
        tvTitle = (TextView) findViewById(R.id.tv_title_vocabulary_learn_activity);
        tvVocab = (TextView) findViewById(R.id.tv_vocab_vocabulary_learn_activity);
        tvPronounce = (TextView) findViewById(R.id.tv_pronounce_vocabulary_learn_activity);
        tvExample = (TextView) findViewById(R.id.tv_example_vocabulary_learn_activity);

        imgBack = (ImageView) findViewById(R.id.imv_back_vocabulary_learn_activity);
        btnNext = (Button) findViewById(R.id.btn_next_vocabulary_learn_activity);
        btnPrev = (Button) findViewById(R.id.btn_prev_vocabulary_learn_activity);
        imvPlay = (ImageView) findViewById(R.id.imv_play_vocabulary_learn_activity);

        prbCount = (ProgressBar) findViewById(R.id.prb_number_vocab_vocabulary_learn_activity);
    }

    private void GetIntentTitle(){
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("K1_VocabLevel");
        tvTitle.setText(value1);
    }

    private void updateTVBeginner(){
        reference = FirebaseDatabase.getInstance().getReference().child("VocabularyList").child("TVCN").child("Beginner");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    vocabularyListsBeginner.add(snapshot.getValue(VocabularyList.class));
                    //audioUrl = dataSnapshot.getValue(String.class);
//                    Toast.makeText(VocabularyLearnActivity.this, ""+audioUrl.toString(), Toast.LENGTH_SHORT).show();

                }
                setAllData();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAllData(){
        tvVocab.setText(vocabularyListsBeginner.get(index).getVocab());
        tvPronounce.setText(vocabularyListsBeginner.get(index).getPronounce());
        tvExample.setText(vocabularyListsBeginner.get(index).getExample());
        urlPath = vocabularyListsBeginner.get(index).getAudioUrl();
    }

    private void NextVocab(){
        if(index < vocabularyListsBeginner.size()-1){
            index++;
            setAllData();
            prbCount.setProgress(prbCount.getProgress() + 100*1/vocabularyListsBeginner.size());
        }else {
            LearnSuccessForm();
        }
    }
    private void PrevVocab(){
        if(index < 0){
            Toast.makeText(VocabularyLearnActivity.this, "Đây là từ vựng đầu tiên", Toast.LENGTH_SHORT);
        }else {
            index--;
            setAllData();
            prbCount.setProgress(prbCount.getProgress() - 100*1/vocabularyListsBeginner.size());
        }
    }

    private void LearnSuccessForm(){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_success_learn);
        Intent intent = new Intent(VocabularyLearnActivity.this, VocabularyActivity.class);
        dialog.findViewById(R.id.btn_go_back_diaglog_success_learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("K1_Vocab_LT","Lý thuyết");
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.findViewById(R.id.btn_go_practice_diaglog_success_learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyLearnActivity.this, VocabularyQuestionActivity.class);
                intent.putExtra("K1_VocabLevel","Beginner");
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }

    public void playAudio(View view){
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try{
            mediaPlayer.setDataSource(vocabularyListsBeginner.get(index).getAudioUrl());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(VocabularyLearnActivity.this, "Play music", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(VocabularyLearnActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}