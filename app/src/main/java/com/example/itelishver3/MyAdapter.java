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

import com.example.itelishver3.mapclass.VocabularyList;
import com.google.firebase.firestore.auth.User;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    String urlPath;

    ArrayList<VocabularyList> vocabularyLists = new ArrayList<VocabularyList>();
    private ItemClickListener itemClickListener;

    public MyAdapter(Context context, ArrayList<VocabularyList> vocabularyLists, ItemClickListener itemClickListener) {
        this.context = context;
        this.vocabularyLists = vocabularyLists;
        this.itemClickListener = itemClickListener;
    }

    public MyAdapter(Context context, ArrayList<VocabularyList> vocabularyLists) {
        this.context = context;
        this.vocabularyLists = vocabularyLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_vocabulary_rcv, parent, false);
        return new MyViewHolder(v);
    }

    public interface ItemClickListener{
        void onItemClick(VocabularyList vocabularyList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VocabularyList vocabularyList = vocabularyLists.get(position);
        holder.textViewVocab.setText(vocabularyList.getVocab());
        holder.textViewPronounce.setText(vocabularyList.getPronounce());
        holder.textViewMean.setText(vocabularyList.getExample());
        int index = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(vocabularyLists.get(index));
            }
        });
        holder.imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlPath = vocabularyList.getAudioUrl();
                playAudio();
            }
        });

    }

    @Override
    public int getItemCount() {
        return vocabularyLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewVocab, textViewPronounce, textViewMean;
        ImageView imgAudio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewVocab = itemView.findViewById(R.id.item_vocab_admin_activity);
            textViewPronounce = itemView.findViewById(R.id.item_pronounce_admin_activity);
            textViewMean = itemView.findViewById(R.id.item_mean_admin_activity);
            imgAudio = itemView.findViewById(R.id.imv_speaker_vocab_admin);
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
