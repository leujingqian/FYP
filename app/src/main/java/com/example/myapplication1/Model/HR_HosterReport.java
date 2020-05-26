package com.example.myapplication1.Model;

import java.util.List;

import com.example.myapplication1.Model.HR_Question;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HR_HosterReport {

    @SerializedName("accuracy")
    @Expose
    private Integer accuracy;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("socket_id")
    @Expose
    private String socketId;
    @SerializedName("game_id")
    @Expose
    private String gameId;
    @SerializedName("hoster")
    @Expose
    private HR_Hoster hoster;
    @SerializedName("game_name")
    @Expose
    private String gameName;
    @SerializedName("questions")
    @Expose
    private List<HR_Question> questions = null;
    @SerializedName("hosted_date")
    @Expose
    private String hostedDate;
    @SerializedName("scoreboard")
    @Expose
    private List<Scoreboard> scoreboard = null;
    @SerializedName("player_results")
    @Expose
    private List<HR_PlayerResults> playerResults = null;
    @SerializedName("__v")
    @Expose
    private Integer v;

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

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public HR_Hoster getHoster() {
        return hoster;
    }

    public void setHoster(HR_Hoster hoster) {
        this.hoster = hoster;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<HR_Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<HR_Question> questions) {
        this.questions = questions;
    }

    public String getHostedDate() {
        return hostedDate;
    }

    public void setHostedDate(String hostedDate) {
        this.hostedDate = hostedDate;
    }

    public List<Scoreboard> getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(List<Scoreboard> scoreboard) {
        this.scoreboard = scoreboard;
    }

    public List<HR_PlayerResults> getPlayerResults() {
        return playerResults;
    }

    public void setPlayerResults(List<HR_PlayerResults> playerResults) {
        this.playerResults = playerResults;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}