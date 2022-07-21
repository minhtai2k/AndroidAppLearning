package com.example.itelishver3.mapclass;

public class ConversationList {
    private String question;
    private String answer;
    private String answerVS, questionVS, id, audioAnswer, audioQuestion;

    public ConversationList(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public ConversationList(String question, String answer, String answerVS, String questionVS, String id, String audioAnswer, String audioQuestion) {
        this.question = question;
        this.answer = answer;
        this.answerVS = answerVS;
        this.questionVS = questionVS;
        this.id = id;
        this.audioAnswer = audioAnswer;
        this.audioQuestion = audioQuestion;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerVS() {
        return answerVS;
    }

    public void setAnswerVS(String answerVS) {
        this.answerVS = answerVS;
    }

    public String getQuestionVS() {
        return questionVS;
    }

    public void setQuestionVS(String questionVS) {
        this.questionVS = questionVS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudioAnswer() {
        return audioAnswer;
    }

    public void setAudioAnswer(String audioAnswer) {
        this.audioAnswer = audioAnswer;
    }

    public String getAudioQuestion() {
        return audioQuestion;
    }

    public void setAudioQuestion(String audioQuestion) {
        this.audioQuestion = audioQuestion;
    }

    public ConversationList() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswerInterns(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ConversationList{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
