package com.example.myapplication1.Model;

import java.util.List;

import com.example.myapplication1.Model.Choice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HR_Question {

    @SerializedName("accuracy")
    @Expose
    private Integer accuracy;
    @SerializedName("numNoAnsPlayers")
    @Expose
    private Integer numNoAnsPlayers;
    @SerializedName("noAnsAccuracy")
    @Expose
    private Integer noAnsAccuracy;
    @SerializedName("choices")
    @Expose
    private List<HR_Choice> choices = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getNumNoAnsPlayers() {
        return numNoAnsPlayers;
    }

    public void setNumNoAnsPlayers(Integer numNoAnsPlayers) {
        this.numNoAnsPlayers = numNoAnsPlayers;
    }

    public Integer getNoAnsAccuracy() {
        return noAnsAccuracy;
    }

    public void setNoAnsAccuracy(Integer noAnsAccuracy) {
        this.noAnsAccuracy = noAnsAccuracy;
    }

    public List<HR_Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<HR_Choice> choices) {
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