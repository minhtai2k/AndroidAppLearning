package com.example.itelishver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class VocabularyResultActivity extends AppCompatActivity {

    private int correct, wrong, number;
    private CircularProgressBar circularProgressBar;
    private CardView cvSuccess, cvShare;
    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_result);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);
        number = getIntent().getIntExtra("number", 0);

        Mapping();
        circularProgressBar.setProgress(correct);
        tvResult.setText(correct+"/"+number);

        cvSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VocabularyResultActivity.this, VocabularyActivity.class));
                finish();
            }
        });

        cvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ITELish");
                    String shareMessage = "\n I got "+correct+" score";
                    //shareMessage = shareMessage + "https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose one"));
                }catch (Exception e){
                    Log.d("DATA", e.toString());
                }
            }
        });
    }

    private void Mapping(){
        circularProgressBar = (CircularProgressBar) findViewById(R.id.circularProgressBarResult);
        cvSuccess = (CardView) findViewById(R.id.cv_success_vocabulary_result_activity);
        cvShare = (CardView) findViewById(R.id.cv_share_vocabulary_result_activity);
        tvResult = (TextView) findViewById(R.id.tv_result_vocabulary_result_activity);
    }
}