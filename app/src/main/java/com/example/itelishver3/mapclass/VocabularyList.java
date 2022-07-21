package com.example.itelishver3.mapclass;

public class VocabularyList {
    private String vocab;
    private String pronounce;
    private String example;
    private String audioUrl;

    public VocabularyList(String vocab, String pronounce, String example, String audioUrl) {
        this.vocab = vocab;
        this.pronounce = pronounce;
        this.example = example;
        this.audioUrl = audioUrl;
    }

    public VocabularyList(){}

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String urlAudio) {
        this.audioUrl = urlAudio;
    }

    @Override
    public String toString() {
        return "VocabularyList{" +
                "vocab='" + vocab + '\'' +
                ", pronounce='" + pronounce + '\'' +
                ", example='" + example + '\'' +
                ", urlAudio='" + audioUrl + '\'' +
                '}';
    }
}
