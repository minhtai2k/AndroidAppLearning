package com.example.itelishver3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itelishver3.mapclass.QuestionList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class VocabularyQuestionActivity extends AppCompatActivity {

    private TextView tvTitle;
    private CountDownTimer countDownTimer;
    private Timer quizTimer;
    private int timerValue = 0;
    private ImageView imgBack;
    private Button btnNext;
    private ProgressBar prbTimer;
    private DatabaseReference reference;
    private CardView cvQuestion1, cvQuestion2, cvQuestion3, cvQuestion4;
    private TextView tvQuestion1, tvQuestion2, tvQuestion3, tvQuestion4, tvQuestion, tvNumberQuestion;
    private ArrayList<QuestionList> questionListsBeginner = new ArrayList<QuestionList>();
    private ArrayList<QuestionList> questionListsIntermediate;
    private ArrayList<QuestionList> questionListsAdvanced;
    private QuestionList questionList = new QuestionList();

    private int index = 0;
    private int correctCount = 0;
    private int wrongCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_question);

        Mapping();
        timerQuestion();
        updateTVCNBeginner();
        Collections.shuffle(questionListsBeginner);
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("K1_VocabLevel");
        tvTitle.setText(value1);

        btnNext.setClickable(false);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                Intent intent = new Intent(VocabularyQuestionActivity.this, VocabularyLevelActivity.class);
                intent.putExtra("K1_Vocab_LT","Thực hành");
                startActivity(intent);
                finish();
            }
        });

        cvQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionAClick(view);
            }
        });

        cvQuestion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionBClick(view);
            }
        });

        cvQuestion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionCClick(view);
            }
        });

        cvQuestion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionDClick(view);
            }
        });

    }

    private void Mapping(){
        tvTitle = (TextView) findViewById(R.id.tv_title_vocabulary_question_activity);
        prbTimer = (ProgressBar) findViewById(R.id.prb_timer_vocabulary_question_activity);
        imgBack = (ImageView) findViewById(R.id.imv_back_vocabulary_question_activity);
        btnNext = (Button) findViewById(R.id.btn_next_vocabulary_question_activity);

        tvQuestion1 = (TextView) findViewById(R.id.tv_questionA_vocabulary_question_activity);
        tvQuestion2 = (TextView) findViewById(R.id.tv_questionB_vocabulary_question_activity);
        tvQuestion3 = (TextView) findViewById(R.id.tv_questionC_vocabulary_question_activity);
        tvQuestion4 = (TextView) findViewById(R.id.tv_questionD_vocabulary_question_activity);
        tvQuestion = (TextView) findViewById(R.id.tv_question_vocabulary_question_activity);
        tvNumberQuestion = (TextView) findViewById(R.id.tv_number_question_vocabulary_question_tvcn);

        cvQuestion1 = (CardView) findViewById(R.id.cv_A_vocabulary_question_activity);
        cvQuestion2 = (CardView) findViewById(R.id.cv_B_vocabulary_question_activity);
        cvQuestion3 = (CardView) findViewById(R.id.cv_C_vocabulary_question_activity);
        cvQuestion4 = (CardView) findViewById(R.id.cv_D_vocabulary_question_activity);

        btnNext = (Button) findViewById(R.id.btn_next_vocabulary_question_activity);
    }

    private void timerQuestion(){
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                prbTimer.setProgress(prbTimer.getProgress()+5);
            }

            @Override
            public void onFinish() {
//                Dialog dialog = new Dialog(VocabularyQuestionActivity.this);
//                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//                dialog.setContentView(R.layout.dialog_time_out_question);
//                dialog.show();
//                dialog.findViewById(R.id.btn_try_again_diaglog_time_out).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.hide();
//                        prbTimer.setProgress(0);
//                        countDownTimer.start();
//                    }
//                });
                  CreateAlertDialog();
            }
        }.start();
    }

    private void updateTVCNBeginner(){

        reference = FirebaseDatabase.getInstance().getReference().child("QuestionList").child("TVCN").child("Beginner");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    questionList = snapshot.getValue(QuestionList.class);
                    questionListsBeginner.add(questionList);
                }
                questionList = questionListsBeginner.get(index);
                setAllData();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAllData(){
        tvQuestion.setText(questionList.getQuestion());

        tvQuestion1.setText(questionList.getOptions1());
        tvQuestion2.setText(questionList.getOptions2());
        tvQuestion3.setText(questionList.getOptions3());
        tvQuestion4.setText(questionList.getOptions4());

        tvNumberQuestion.setText(index+"/"+questionListsBeginner.size());
    }

    private void Correct(CardView cardView){
        cardView.setBackgroundColor(getResources().getColor(R.color.green));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index<questionListsBeginner.size()-1){
                    correctCount++;
                    index++;
                    questionList = questionListsBeginner.get(index);
                    resetColor();
                    enableButton();
                    setAllData();
                    prbTimer.setProgress(0);
                    countDownTimer.start();
                }else {
                    correctCount++;
                    countDownTimer.cancel();
                    Intent intent = new Intent(VocabularyQuestionActivity.this, VocabularyResultActivity.class);
                    intent.putExtra("correct", correctCount);
                    intent.putExtra("wrong", wrongCount);
                    intent.putExtra("number", questionListsBeginner.size());
                    startActivity(intent);
                }

            }
        });
    }

//    private void Wrong(CardView cardView){
//        //cardView.setBackgroundColor(getResources().getColor(R.color.red));
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(index<questionListsBeginner.size()-1){
//                    wrongCount++;
//                    index++;
//                    questionList = questionListsBeginner.get(index);
//                    resetColor();
//                    enableButton();
//                    setAllData();
//                    prbTimer.setProgress(0);
//                    countDownTimer.start();
//                }else {
//                    wrongCount++;
//                    countDownTimer.cancel();
//                    Intent intent = new Intent(VocabularyQuestionActivity.this, VocabularyResultActivity.class);
//                    intent.putExtra("correct", correctCount);
//                    intent.putExtra("wrong", wrongCount);
//                    intent.putExtra("number", questionListsBeginner.size());
//                    startActivity(intent);
//                }
//
//            }
//        });
//    }

    private void WrongShowResult(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index<questionListsBeginner.size()-1){
                    wrongCount++;
                    index++;
                    questionList = questionListsBeginner.get(index);
                    resetColor();
                    enableButton();
                    setAllData();
                    prbTimer.setProgress(0);
                    countDownTimer.start();
                }else {
                    wrongCount++;
                    countDownTimer.cancel();
                    Intent intent = new Intent(VocabularyQuestionActivity.this, VocabularyResultActivity.class);
                    intent.putExtra("correct", correctCount);
                    intent.putExtra("wrong", wrongCount);
                    intent.putExtra("number", questionListsBeginner.size());
                    startActivity(intent);
                }

            }
        });
    }


    private void enableButton(){
        cvQuestion1.setClickable(true);
        cvQuestion2.setClickable(true);
        cvQuestion3.setClickable(true);
        cvQuestion4.setClickable(true);
    }

    private void disableButton(){
        cvQuestion1.setClickable(false);
        cvQuestion2.setClickable(false);
        cvQuestion3.setClickable(false);
        cvQuestion4.setClickable(false);
    }

    private void resetColor(){
        cvQuestion1.setBackgroundColor(getResources().getColor(R.color.white));
        cvQuestion2.setBackgroundColor(getResources().getColor(R.color.white));
        cvQuestion3.setBackgroundColor(getResources().getColor(R.color.white));
        cvQuestion4.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void OptionAClick(View view){
        disableButton();
        if(questionList.getOptions1().equals(questionList.getAnswers())){
            countDownTimer.cancel();
            btnNext.setClickable(true);
            Correct(cvQuestion1);
        }else {
            countDownTimer.cancel();
            ShowAnswerQuestion();
            cvQuestion1.setBackgroundColor(getResources().getColor(R.color.red));
            //Wrong(cvQuestion1);
            WrongShowResult();
        }

    }

    private void OptionBClick(View view){
        disableButton();
        if(questionList.getOptions2().equals(questionList.getAnswers())){
            countDownTimer.cancel();
            btnNext.setClickable(true);
            Toast.makeText(VocabularyQuestionActivity.this, "True"+index+questionList.getOptions2(), Toast.LENGTH_SHORT).show();
            Correct(cvQuestion2);
        }else {
            countDownTimer.cancel();
            ShowAnswerQuestion();
            cvQuestion2.setBackgroundColor(getResources().getColor(R.color.red));
            //Wrong(cvQuestion2);
            WrongShowResult();
        }

    }

    private void OptionCClick(View view){
        disableButton();
        if(questionList.getOptions3().equals(questionList.getAnswers())){
            btnNext.setClickable(true);
            countDownTimer.cancel();
            Correct(cvQuestion3);

        }else {
            countDownTimer.cancel();
            ShowAnswerQuestion();
            cvQuestion3.setBackgroundColor(getResources().getColor(R.color.red));
            //Wrong(cvQuestion3);
            WrongShowResult();
        }
    }

    private void OptionDClick(View view){
        disableButton();
        if(questionList.getOptions4().equals(questionList.getAnswers())){
            countDownTimer.cancel();
            btnNext.setClickable(true);
            Correct(cvQuestion4);
        }else {
            countDownTimer.cancel();
            ShowAnswerQuestion();
            cvQuestion4.setBackgroundColor(getResources().getColor(R.color.red));
            //Wrong(cvQuestion4);
            WrongShowResult();
        }

    }


    private void CreateAlertDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có muốn hiển thị kết quả không?");
// Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                ShowAnswerQuestion();
                WrongShowResult();
            }
        });

        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                prbTimer.setProgress(0);
                countDownTimer.cancel();
            }
        });
//Tạo dialog
        AlertDialog al = b.create();

//Hiển thị diaglog
        al.show();
        al.setCancelable(false);
    }


    private void ShowAnswerQuestion(){
        //Nếu option1 == Answers
        if(questionListsBeginner.get(index).getOptions1().equals(questionListsBeginner.get(index).getAnswers())){
            //Chỉnh background thành màu xanh
            cvQuestion1.setBackgroundColor(getResources().getColor(R.color.green));
        }else if(questionListsBeginner.get(index).getOptions2().equals(questionListsBeginner.get(index).getAnswers())){
            cvQuestion2.setCardBackgroundColor(getResources().getColor(R.color.green));
        }else if(questionListsBeginner.get(index).getOptions3().equals(questionListsBeginner.get(index).getAnswers())){
            cvQuestion3.setCardBackgroundColor(getResources().getColor(R.color.green));
        }else{
            cvQuestion4.setCardBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    private void ShowResultFinal(){
        Dialog dialogResult = new Dialog(this);
    }

}