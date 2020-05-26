package com.example.myapplication1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Played_Choice {
    @SerializedName("accuracy")
    @Expose
    private Integer accuracy;
    @SerializedName("response_time")
    @Expose
    private Integer responseTime;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("choice")
    @Expose
    private String choice;
    @SerializedName("is_correct")
    @Expose
    private Boolean isCorrect;
    @SerializedName("is_answer")
    @Expose
    private Boolean isAnswer;

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

}
