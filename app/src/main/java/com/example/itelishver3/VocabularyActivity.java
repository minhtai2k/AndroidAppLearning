package com.example.itelishver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class VocabularyActivity extends AppCompatActivity {

    private CardView cardViewCNPM;
    private CardView cardViewMMT;
    private CardView cardViewHQTCSDL;
    private CardView cardViewT4;
    private TextView tvTitle;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        Mapping();

        cardViewCNPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(VocabularyActivity.this, VocabularyLevelActivity.class);
//                intent.putExtra("K1_Vocab","Công nghệ phần mềm");
//                startActivity(intent);
//                finish();
                DialogChooseForm();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VocabularyActivity.this, MainActivity.class));
                finish();
            }
        });


//        Intent intent = getIntent();
//        String value1 = intent.getStringExtra("K1_Main_Vocab");
//        tvTitle.setText(value1);
    }

    private void Mapping(){
        cardViewCNPM = (CardView) findViewById(R.id.cv_cnpm_vocabulary_activity);
        cardViewMMT = (CardView) findViewById(R.id.cv_mmt_vocabulary_activity);
        cardViewHQTCSDL = (CardView) findViewById(R.id.cv_hcsdl_vocabulary_activity);
        cardViewT4 = (CardView) findViewById(R.id.cv_nt4_vocabulary_activity);
        imgBack = (ImageView) findViewById(R.id.imv_back_vocabulary_activity);
        tvTitle = (TextView) findViewById(R.id.tv_title_vocabulary_activity);
    }

    private void DialogChooseForm(){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_choose_study_form);
        Intent intent = new Intent(VocabularyActivity.this, VocabularyLevelActivity.class);
        dialog.findViewById(R.id.btn_lt_vocabulary_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("K1_Vocab_LT","Lý thuyết");
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.findViewById(R.id.btn_tn_vocabulary_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(VocabularyActivity.this, VocabularyLevelActivity.class);
                intent.putExtra("K1_Vocab_LT","Thực hành");
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

}