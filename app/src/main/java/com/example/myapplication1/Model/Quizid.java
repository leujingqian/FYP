package com.example.myapplication1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quizid {
    @SerializedName("quiz")
    @Expose
    private Quizzes quiz;
    @SerializedName("isFavorited")
    @Expose
    private Boolean isFavorited;

    public Quizzes getQuiz() {
        return quiz;
    }

    public void setQuiz(Quizzes quiz) {
        this.quiz = quiz;
    }

    public Boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

}

