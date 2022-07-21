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

import com.example.itelishver3.mapclass.ConversationList;

import com.google.firebase.firestore.auth.User;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapterConversation extends RecyclerView.Adapter<MyAdapterConversation.MyViewHolder> {

    Context context;
    String urlPath;

    ArrayList<ConversationList> conversationLists  = new ArrayList<ConversationList>();
    private ItemClickListener itemClickListener;

    public MyAdapterConversation(Context context, ArrayList<ConversationList> conversationLists, ItemClickListener itemClickListener) {
        this.context = context;
        this.conversationLists = conversationLists;
        this.itemClickListener = itemClickListener;
    }


//    public MyAdapterConversation(AdminConversationActivity context, ArrayList<ConversationList> conversationLists, ItemClickListener itemClickListener) {
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_conversation_rcv, parent, false);
        return new MyViewHolder(v);
    }

    public interface ItemClickListener{
        void onItemClick(ConversationList conversationList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ConversationList conversationList = conversationLists.get(position);
        holder.textViewQuestion.setText(conversationList.getQuestion());
        holder.textViewAnswer.setText(conversationList.getAnswer());
        holder.textViewAnswerVS.setText(conversationList.getAnswerVS());
        holder.textViewQuestionVS.setText(conversationList.getQuestionVS());
        int index = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(conversationLists.get(index));
            }
        });

        holder.imgAudioQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlPath = conversationList.getAudioQuestion();
                playAudio();
            }
        });

        holder.imgAudioAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlPath = conversationList.getAudioAnswer();
                playAudio();
            }
        });

    }

    @Override
    public int getItemCount() {
        return conversationLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewQuestion, textViewAnswer, textViewQuestionVS, textViewAnswerVS;
        ImageView imgAudioQuestion, imgAudioAnswer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.item_question_conversation_activity);
            textViewQuestionVS = itemView.findViewById(R.id.item_questionVS_conversation_activity);
            textViewAnswer = itemView.findViewById(R.id.item_answer_conversation_activity);
            textViewAnswerVS = itemView.findViewById(R.id.item_answerVS_conversation_activity);
            imgAudioQuestion = itemView.findViewById(R.id.imv_speaker_question_conversation_admin);
            imgAudioAnswer = itemView.findViewById(R.id.imv_speaker_answer_conversation_admin);
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
