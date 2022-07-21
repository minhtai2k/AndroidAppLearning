package com.example.itelishver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VocabularyLevelActivity extends AppCompatActivity {

    private TextView tvTitle;
    private CardView cvBeginner, cvIntermediate, cvAdvanced;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_level);

        Mapping();

//        Intent intent = getIntent();
//        String value1 = intent.getStringExtra("K1_Vocab_LT");
//        tvTitle.setText(value1);
//        int value2 = intent.getIntExtra("Key_2", 0);
//        boolean value3 = intent.getBooleanExtra("Key_3", false);
        getIntentVocabulary();
        cvBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTitle.getText().equals("Lý thuyết")){
                    Intent intent = new Intent(VocabularyLevelActivity.this, VocabularyLearnActivity.class);
                    intent.putExtra("K1_VocabLevel","Beginner");
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(VocabularyLevelActivity.this, VocabularyQuestionActivity.class);
                    intent.putExtra("K1_VocabLevel","Beginner");
                    startActivity(intent);
                    finish();
                }

            }
        });

        cvIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyLevelActivity.this, VocabularyQuestionActivity.class);
                intent.putExtra("K2_VocabLevel","Intermediate");
                startActivity(intent);
                finish();
            }
        });

        cvAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyLevelActivity.this, VocabularyQuestionActivity.class);
                intent.putExtra("K3_VocabLevel","Advanced");
                startActivity(intent);
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VocabularyLevelActivity.this, VocabularyActivity.class));
                finish();
            }
        });
    }

    private void Mapping(){
        tvTitle = (TextView) findViewById(R.id.tv_title_vocabulary_level_activity);
        cvBeginner = (CardView) findViewById(R.id.cv_lv1_vocabulary_level);
        cvIntermediate = (CardView) findViewById(R.id.cv_lv2_vocabulary_level);
        cvAdvanced = (CardView) findViewById(R.id.cv_lv3_vocabulary_level);
        imgBack = (ImageView) findViewById(R.id.imv_back_vocabulary_level_activity);
    }

    private void getIntentVocabulary(){
        Intent intent = getIntent();
        if(intent.getStringExtra("K1_Vocab_LT").equals("Lý thuyết")){
            tvTitle.setText("Lý thuyết");
        }else
            tvTitle.setText(intent.getStringExtra("K1_Vocab_LT"));
    }
}