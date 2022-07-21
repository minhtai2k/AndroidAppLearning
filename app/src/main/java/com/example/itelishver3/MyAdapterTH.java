package com.example.itelishver3;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itelishver3.mapclass.QuestionList;
import com.example.itelishver3.mapclass.VocabularyList;
import com.google.firebase.firestore.auth.User;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapterTH extends RecyclerView.Adapter<MyAdapterTH.MyViewHolder> {

    Context context;
    String urlPath;

    ArrayList<QuestionList> questionLists = new ArrayList<QuestionList>();

    public MyAdapterTH(Context context, ArrayList<QuestionList> questionLists) {
        this.context = context;
        this.questionLists = questionLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_vocabulary_th_rcv, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        QuestionList questionList = questionLists.get(position);
        holder.textViewQuestion.setText(questionList.getQuestion());
        holder.textViewAnswer.setText(questionList.getAnswers());
        holder.textViewOption1.setText(questionList.getOptions1());
        holder.textViewOption2.setText(questionList.getOptions2());
        holder.textViewOption3.setText(questionList.getOptions3());
        holder.textViewOption4.setText(questionList.getOptions4());
//        holder.imgAudio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                urlPath = vocabularyList.getUrlAudio();
//                playAudio();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return questionLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewQuestion, textViewOption1, textViewOption2, textViewOption3, textViewOption4, textViewAnswer;
        ImageView imgAudio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.item_question_admin_th_activity);
            textViewAnswer = itemView.findViewById(R.id.item_answer_admin_th_activity);
            textViewOption1 = itemView.findViewById(R.id.item_optionA_admin_th_activity);
            textViewOption2 = itemView.findViewById(R.id.item_optionB_admin_th_activity);
            textViewOption3 = itemView.findViewById(R.id.item_optionC_admin_th_activity);
            textViewOption4 = itemView.findViewById(R.id.item_optionD_admin_th_activity);
            //imgAudio = itemView.findViewById(R.id.imv_speaker_vocab_admin);
        }
    }

    public void playAudio(){
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try{
            mediaPlayer.setDataSource(urlPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException e){
            Toast.makeText(context.getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
