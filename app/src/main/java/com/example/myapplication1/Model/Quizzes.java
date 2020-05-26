package com.example.myapplication1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Quizzes {
    @SerializedName("plays")
    @Expose
    private Integer plays;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("creator")
    @Expose
    private Creator creator;
    @SerializedName("questions")
    @Expose
    private List<QuizQuestion> questions = null;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Quizzes(String title, List<QuizQuestion> questions) {

        this.title = title;
        this.questions = questions;
    }

    public Integer getPlays() {
        return plays;
    }

    public void setPlays(Integer plays) {
        this.plays = plays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
