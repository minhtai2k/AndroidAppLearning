package com.example.itelishver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class AdminActivity extends AppCompatActivity {

    private ImageView imgBack;
    private CardView cvTv, cvHt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Mapping();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                finish();
            }
        });

        cvTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TvChooseForm();
            }
        });

        cvHt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HtChooseForm();
            }
        });
    }

    private void Mapping(){
        imgBack = (ImageView) findViewById(R.id.imv_back_admin_activity);
        cvTv = (CardView) findViewById(R.id.cv_tvcn_admin_activity);
        cvHt = (CardView) findViewById(R.id.cv_ht_admin_activity);
    }

    private void TvChooseForm() {
        Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_choose_study_form);
        dialog.show();
        dialog.findViewById(R.id.btn_lt_vocabulary_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogSpecialized = new Dialog(AdminActivity.this);
                dialogSpecialized.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialogSpecialized.setContentView(R.layout.dialog_choose_specialized_vocabulary_admin);
                dialog.dismiss();
                dialogSpecialized.show();
                dialogSpecialized.findViewById(R.id.btn_cnpm_dialog_choose_specialized_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialogLevel = new Dialog(AdminActivity.this);
                        dialogLevel.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        dialogLevel.setContentView(R.layout.dialog_choose_level_vocabulary_admin);
                        dialogSpecialized.dismiss();
                        dialogLevel.show();
                        dialogLevel.findViewById(R.id.btn_beginner_admin_vocabulary_dialog).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminActivity.this, AdminVocabularyActivity.class);
                                intent.putExtra("SL_1", "CNPM_Beginner");
                                startActivity(intent);
                                dialogLevel.dismiss();
                                finish();
                            }
                        });

                    }
                });
            }
        });
        dialog.findViewById(R.id.btn_tn_vocabulary_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogSpecialized = new Dialog(AdminActivity.this);
                dialogSpecialized.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialogSpecialized.setContentView(R.layout.dialog_choose_specialized_vocabulary_admin);
                dialog.dismiss();
                dialogSpecialized.show();
                dialogSpecialized.findViewById(R.id.btn_cnpm_dialog_choose_specialized_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialogLevel = new Dialog(AdminActivity.this);
                        dialogLevel.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        dialogLevel.setContentView(R.layout.dialog_choose_level_vocabulary_admin);
                        dialogSpecialized.dismiss();
                        dialogLevel.show();
                        dialogLevel.findViewById(R.id.btn_beginner_admin_vocabulary_dialog).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminActivity.this, AdminVocabularyTHActivity.class);
                                intent.putExtra("SL_1", "CNPM_Beginner");
                                startActivity(intent);
                                dialogLevel.dismiss();
                                finish();
                            }
                        });

                    }
                });
            }
        });
        dialog.show();
    }
    private void HtChooseForm(){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setContentView(R.layout.dialog_choose_conversation);
        dialog.findViewById(R.id.btn_htpv_admin_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogSpecialized = new Dialog(AdminActivity.this);
                dialogSpecialized.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialogSpecialized.setContentView(R.layout.dialog_choose_specialized_vocabulary_admin);
                dialog.dismiss();
                dialogSpecialized.show();
                dialogSpecialized.findViewById(R.id.btn_cnpm_dialog_choose_specialized_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialogLevel = new Dialog(AdminActivity.this);
                        dialogLevel.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        dialogLevel.setContentView(R.layout.dialog_choose_level_vocabulary_admin);
                        dialogSpecialized.dismiss();
                        dialogLevel.show();
                        dialogLevel.findViewById(R.id.btn_beginner_admin_vocabulary_dialog).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminActivity.this, AdminConversationActivity.class);
                                intent.putExtra("SL_1", "HTPV_Beginner");
                                startActivity(intent);
                                dialogLevel.dismiss();
                                finish();
                            }
                        });

                    }
                });
            }
        });
        dialog.findViewById(R.id.btn_htcn_admin_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogSpecialized = new Dialog(AdminActivity.this);
                dialogSpecialized.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialogSpecialized.setContentView(R.layout.dialog_choose_specialized_vocabulary_admin);
                dialog.dismiss();
                dialogSpecialized.show();
                dialogSpecialized.findViewById(R.id.btn_cnpm_dialog_choose_specialized_vocabulary_admin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialogLevel = new Dialog(AdminActivity.this);
                        dialogLevel.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        dialogLevel.setContentView(R.layout.dialog_choose_level_vocabulary_admin);
                        dialogSpecialized.dismiss();
                        dialogLevel.show();
                        dialogLevel.findViewById(R.id.btn_beginner_admin_vocabulary_dialog).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminActivity.this, AdminConversationActivity.class);
                                intent.putExtra("SL_1", "HTCN_Beginner");
                                startActivity(intent);
                                dialogLevel.dismiss();
                                finish();
                            }
                        });

                    }
                });
//                Intent intent = new Intent(AdminActivity.this, VocabularyQuestionActivity.class);
//                intent.putExtra("K1_Conversa_CN","Hội thoại chuyên ngành");
//                startActivity(intent);
//                finish();
            }
        });
        dialog.show();
    }
}