package com.example.myapplication1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HR_PlayerResults {
    @SerializedName("correct")
    @Expose
    private Integer correct;
    @SerializedName("incorrect")
    @Expose
    private Integer incorrect;
    @SerializedName("unattempted")
    @Expose
    private Integer unattempted;
    @SerializedName("accuracy")
    @Expose
    private Integer accuracy;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(Integer incorrect) {
        this.incorrect = incorrect;
    }

    public Integer getUnattempted() {
        return unattempted;
    }

    public void setUnattempted(Integer unattempted) {
        this.unattempted = unattempted;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
