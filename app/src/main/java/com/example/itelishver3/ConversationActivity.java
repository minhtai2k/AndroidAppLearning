package com.example.itelishver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ConversationActivity extends AppCompatActivity {

    private ImageView imvBack;
    private CardView cvHTCN, cvHTPV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Mapping();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConversationActivity.this, MainActivity.class));
                finish();
            }
        });

        cvHTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConversationActivity.this, ConversationLearnActivity.class);
                intent.putExtra("K1_HT_Activity", "Hội thoại chuyên ngành");
                startActivity(intent);
                finish();

            }
        });

        cvHTPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConversationActivity.this, ConversationLearnActivity.class);
                intent.putExtra("K1_HT_Activity", "Hội thoại phỏng vấn");
                startActivity(intent);
                finish();
            }
        });
    }

    private void Mapping(){
        imvBack = (ImageView) findViewById(R.id.imv_back_convarsation_activity);
        cvHTCN = (CardView) findViewById(R.id.cv_htcn_conversation_activity);
        cvHTPV = (CardView) findViewById(R.id.cv_htpv_conversasion_activity);
    }

}