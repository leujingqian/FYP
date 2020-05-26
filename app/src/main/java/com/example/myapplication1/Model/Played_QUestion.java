package com.example.myapplication1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Played_QUestion {
    @SerializedName("choices")
    @Expose
    private List<Played_Choice> choices = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;

    public List<Played_Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Played_Choice> choices) {
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
